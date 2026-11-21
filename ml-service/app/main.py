"""API de scoring du risque des sinistres, appelée par le workflow Camunda."""

import os
from functools import lru_cache
from pathlib import Path
from typing import Literal

import joblib
import pandas as pd
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

from app.features import FEATURES, TYPES_SINISTRE, TYPES_VEHICULE, ratio_montant_valeur

MODEL_PATH = Path(os.getenv("MODEL_PATH", Path(__file__).resolve().parent.parent / "models" / "risk_model.joblib"))

app = FastAPI(title="Service de scoring du risque des sinistres", version="1.0.0")


class SinistreFeatures(BaseModel):
    type_vehicule: Literal[tuple(TYPES_VEHICULE)]  # type: ignore[valid-type]
    type_sinistre: Literal[tuple(TYPES_SINISTRE)]  # type: ignore[valid-type]
    puissance_cv: int = Field(ge=1, le=60)
    valeur_vehicule: float = Field(gt=0, description="Valeur du véhicule en milliers de dinars")
    nb_places: int = Field(ge=1, le=60)
    classe_bonus_malus: int = Field(ge=1, le=20)
    montant_declare: float = Field(ge=0, description="Montant déclaré en dinars")
    delai_declaration_jours: int = Field(ge=0)
    nb_sinistres_anterieurs: int = Field(ge=0)
    accident_nuit: bool
    presence_temoins: bool
    constat_amiable: bool


class Prediction(BaseModel):
    score_risque: float
    niveau_risque: Literal["FAIBLE", "MOYEN", "ELEVE"]
    version_modele: str


@lru_cache(maxsize=1)
def load_model() -> dict:
    if not MODEL_PATH.exists():
        raise FileNotFoundError(f"Modèle introuvable : {MODEL_PATH}. Lancer train.py d'abord.")
    return joblib.load(MODEL_PATH)


def niveau(score: float, seuils: dict) -> str:
    if score >= seuils["eleve"]:
        return "ELEVE"
    if score >= seuils["moyen"]:
        return "MOYEN"
    return "FAIBLE"


@app.get("/health")
def health() -> dict:
    try:
        meta = load_model()["metadata"]
    except FileNotFoundError as exc:
        raise HTTPException(status_code=503, detail=str(exc))
    return {"statut": "ok", "version_modele": meta["version"]}


@app.get("/model-info")
def model_info() -> dict:
    return load_model()["metadata"]


@app.post("/predict", response_model=Prediction)
def predict(sinistre: SinistreFeatures) -> Prediction:
    bundle = load_model()
    meta = bundle["metadata"]
    row = sinistre.model_dump()
    row["ratio_montant_valeur"] = ratio_montant_valeur(row["montant_declare"], row["valeur_vehicule"])
    for col in ("accident_nuit", "presence_temoins", "constat_amiable"):
        row[col] = int(row[col])
    X = pd.DataFrame([row])[FEATURES]
    score = float(bundle["model"].predict_proba(X)[0, 1])
    return Prediction(
        score_risque=round(score, 4),
        niveau_risque=niveau(score, meta["seuils"]),
        version_modele=meta["version"],
    )
