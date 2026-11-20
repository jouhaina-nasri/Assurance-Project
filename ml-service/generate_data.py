"""Génère un jeu de données synthétique de sinistres automobiles.

Aucune donnée réelle de sinistres n'est disponible dans le projet. Ce script
produit des sinistres plausibles dont l'étiquette `sinistre_a_risque`
(suspicion de fraude ou sinistre atypique nécessitant une expertise) suit des
règles métier bruitées : déclaration tardive, montant élevé par rapport à la
valeur du véhicule, historique chargé, accident de nuit sans témoin, etc.

Pour entraîner sur des données réelles, fournir un CSV avec les mêmes colonnes
(voir app/features.py) à train.py via --data.

Usage : python generate_data.py --n 20000 --out data/sinistres_synthetiques.csv
"""

import argparse
from pathlib import Path

import numpy as np
import pandas as pd

from app.features import TYPES_SINISTRE, TYPES_VEHICULE, ratio_montant_valeur


def generate(n: int, seed: int = 42) -> pd.DataFrame:
    rng = np.random.default_rng(seed)

    type_vehicule = rng.choice(TYPES_VEHICULE, size=n, p=[0.70, 0.18, 0.12])
    type_sinistre = rng.choice(TYPES_SINISTRE, size=n, p=[0.62, 0.12, 0.06, 0.20])

    puissance_cv = rng.integers(2, 16, size=n)
    # valeur en milliers de dinars, corrélée à la puissance
    valeur_vehicule = np.round(rng.lognormal(mean=3.0 + 0.06 * puissance_cv, sigma=0.35), 1)
    nb_places = np.where(type_vehicule == "commerciale", rng.integers(2, 4, size=n), 5)
    classe_bonus_malus = rng.integers(1, 12, size=n)

    base_montant = {
        "collision": 2500,
        "vol": 0.0,  # dépend de la valeur du véhicule
        "incendie": 0.0,
        "bris_de_glace": 450,
    }
    montant = np.empty(n)
    for i in range(n):
        ts = type_sinistre[i]
        if ts in ("vol", "incendie"):
            montant[i] = valeur_vehicule[i] * 1000 * rng.uniform(0.4, 1.1)
        else:
            montant[i] = rng.lognormal(np.log(base_montant[ts]), 0.7)
    montant_declare = np.round(montant, 0)

    delai_declaration_jours = rng.poisson(4, size=n) + (rng.random(n) < 0.08) * rng.integers(15, 90, size=n)
    nb_sinistres_anterieurs = rng.poisson(0.6 + 0.08 * classe_bonus_malus)
    accident_nuit = rng.random(n) < 0.22
    presence_temoins = rng.random(n) < 0.45
    constat_amiable = (type_sinistre == "collision") & (rng.random(n) < 0.6)

    ratio = np.array([ratio_montant_valeur(m, v) for m, v in zip(montant_declare, valeur_vehicule)])

    logit = (
        -3.4
        + 2.2 * np.clip(ratio - 0.35, 0, None)
        + 0.045 * np.clip(delai_declaration_jours - 7, 0, None)
        + 0.45 * nb_sinistres_anterieurs
        + 0.10 * (classe_bonus_malus - 6)
        + 0.9 * (accident_nuit & ~presence_temoins)
        - 0.6 * presence_temoins
        - 0.9 * constat_amiable
        + np.select(
            [type_sinistre == "vol", type_sinistre == "incendie", type_sinistre == "bris_de_glace"],
            [1.1, 1.3, -0.8],
            default=0.0,
        )
        + 0.25 * (type_vehicule == "taxi")
        + rng.normal(0, 0.6, size=n)
    )
    proba = 1 / (1 + np.exp(-logit))
    target = (rng.random(n) < proba).astype(int)

    return pd.DataFrame(
        {
            "type_vehicule": type_vehicule,
            "type_sinistre": type_sinistre,
            "puissance_cv": puissance_cv,
            "valeur_vehicule": valeur_vehicule,
            "nb_places": nb_places,
            "classe_bonus_malus": classe_bonus_malus,
            "montant_declare": montant_declare,
            "ratio_montant_valeur": np.round(ratio, 4),
            "delai_declaration_jours": delai_declaration_jours,
            "nb_sinistres_anterieurs": nb_sinistres_anterieurs,
            "accident_nuit": accident_nuit,
            "presence_temoins": presence_temoins,
            "constat_amiable": constat_amiable,
            "sinistre_a_risque": target,
        }
    )


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--n", type=int, default=20000)
    parser.add_argument("--seed", type=int, default=42)
    parser.add_argument("--out", type=Path, default=Path("data/sinistres_synthetiques.csv"))
    args = parser.parse_args()

    df = generate(args.n, args.seed)
    args.out.parent.mkdir(parents=True, exist_ok=True)
    df.to_csv(args.out, index=False)
    print(f"{len(df)} sinistres écrits dans {args.out} (taux à risque : {df['sinistre_a_risque'].mean():.1%})")


if __name__ == "__main__":
    main()
