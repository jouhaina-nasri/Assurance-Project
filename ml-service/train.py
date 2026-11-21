"""Entraîne et évalue le modèle de prédiction du risque des sinistres.

Compare une régression logistique (référence) et un gradient boosting par
validation croisée, retient le meilleur selon l'AUC-ROC, calibre ses
probabilités puis l'évalue sur un jeu de test tenu à l'écart.

Usage : python train.py --data data/sinistres_synthetiques.csv --out models/
"""

import argparse
import json
from datetime import datetime, timezone
from pathlib import Path

import joblib
import pandas as pd
from sklearn.calibration import CalibratedClassifierCV
from sklearn.compose import ColumnTransformer
from sklearn.ensemble import HistGradientBoostingClassifier
from sklearn.inspection import permutation_importance
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import (
    average_precision_score,
    brier_score_loss,
    f1_score,
    precision_score,
    recall_score,
    roc_auc_score,
)
from sklearn.model_selection import StratifiedKFold, cross_val_score, train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import OneHotEncoder, StandardScaler

from app.features import (
    BOOLEAN_FEATURES,
    CATEGORICAL_FEATURES,
    FEATURES,
    NUMERIC_FEATURES,
    TARGET,
)

# Seuils de niveau de risque appliqués à la probabilité calibrée.
SEUIL_MOYEN = 0.25
SEUIL_ELEVE = 0.55


def build_preprocessor() -> ColumnTransformer:
    return ColumnTransformer(
        [
            ("cat", OneHotEncoder(handle_unknown="ignore"), CATEGORICAL_FEATURES),
            ("num", StandardScaler(), NUMERIC_FEATURES),
            ("bool", "passthrough", BOOLEAN_FEATURES),
        ]
    )


def candidates() -> dict:
    return {
        "regression_logistique": LogisticRegression(max_iter=1000, class_weight="balanced"),
        "gradient_boosting": HistGradientBoostingClassifier(
            max_iter=300, learning_rate=0.05, max_leaf_nodes=31, l2_regularization=1.0, random_state=42
        ),
    }


def load(path: Path) -> pd.DataFrame:
    df = pd.read_csv(path)
    missing = set(FEATURES + [TARGET]) - set(df.columns)
    if missing:
        raise ValueError(f"Colonnes manquantes dans {path} : {sorted(missing)}")
    for col in BOOLEAN_FEATURES:
        df[col] = df[col].astype(int)
    return df


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--data", type=Path, default=Path("data/sinistres_synthetiques.csv"))
    parser.add_argument("--out", type=Path, default=Path("models"))
    args = parser.parse_args()

    df = load(args.data)
    X, y = df[FEATURES], df[TARGET]
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, stratify=y, random_state=42)

    cv = StratifiedKFold(n_splits=5, shuffle=True, random_state=42)
    cv_scores = {}
    for name, clf in candidates().items():
        pipe = Pipeline([("prep", build_preprocessor()), ("clf", clf)])
        scores = cross_val_score(pipe, X_train, y_train, cv=cv, scoring="roc_auc")
        cv_scores[name] = {"auc_moyenne": round(scores.mean(), 4), "auc_ecart_type": round(scores.std(), 4)}
        print(f"{name:24s} AUC CV = {scores.mean():.4f} ± {scores.std():.4f}")

    best_name = max(cv_scores, key=lambda k: cv_scores[k]["auc_moyenne"])
    best = Pipeline([("prep", build_preprocessor()), ("clf", candidates()[best_name])])
    model = CalibratedClassifierCV(best, method="isotonic", cv=3)
    model.fit(X_train, y_train)

    proba = model.predict_proba(X_test)[:, 1]
    pred = (proba >= 0.5).astype(int)
    test_metrics = {
        "auc_roc": round(roc_auc_score(y_test, proba), 4),
        "average_precision": round(average_precision_score(y_test, proba), 4),
        "brier": round(brier_score_loss(y_test, proba), 4),
        "precision@0.5": round(precision_score(y_test, pred, zero_division=0), 4),
        "rappel@0.5": round(recall_score(y_test, pred), 4),
        "f1@0.5": round(f1_score(y_test, pred), 4),
        "taux_positifs_test": round(float(y_test.mean()), 4),
    }
    niveaux = pd.cut(proba, [-0.01, SEUIL_MOYEN, SEUIL_ELEVE, 1.01], labels=["FAIBLE", "MOYEN", "ELEVE"])
    repartition = (
        pd.DataFrame({"niveau": niveaux, "y": y_test.values})
        .groupby("niveau", observed=False)["y"]
        .agg(["count", "mean"])
        .round(4)
        .rename(columns={"count": "nb_sinistres", "mean": "taux_reel_a_risque"})
        .to_dict(orient="index")
    )

    imp = permutation_importance(model, X_test, y_test, scoring="roc_auc", n_repeats=5, random_state=42)
    importances = dict(
        sorted(
            ((f, round(float(v), 4)) for f, v in zip(FEATURES, imp.importances_mean)),
            key=lambda kv: kv[1],
            reverse=True,
        )
    )

    version = datetime.now(timezone.utc).strftime("risque-sinistre-%Y%m%d-%H%M")
    metadata = {
        "version": version,
        "algorithme": best_name,
        "calibration": "isotonic",
        "features": FEATURES,
        "seuils": {"moyen": SEUIL_MOYEN, "eleve": SEUIL_ELEVE},
        "donnees": str(args.data),
        "nb_entrainement": int(len(X_train)),
        "nb_test": int(len(X_test)),
        "validation_croisee": cv_scores,
        "test": test_metrics,
        "niveaux_test": repartition,
        "importance_permutation": importances,
    }

    args.out.mkdir(parents=True, exist_ok=True)
    joblib.dump({"model": model, "metadata": metadata}, args.out / "risk_model.joblib")
    (args.out / "metrics.json").write_text(json.dumps(metadata, indent=2, ensure_ascii=False), encoding="utf-8")
    print(f"Modèle retenu : {best_name} — test : {test_metrics}")
    print(f"Écrit dans {args.out}/risk_model.joblib et metrics.json")


if __name__ == "__main__":
    main()
