from fastapi.testclient import TestClient

from app.main import app

client = TestClient(app)

SINISTRE_SIMPLE = {
    "type_vehicule": "privee",
    "type_sinistre": "bris_de_glace",
    "puissance_cv": 5,
    "valeur_vehicule": 35.0,
    "nb_places": 5,
    "classe_bonus_malus": 2,
    "montant_declare": 400,
    "delai_declaration_jours": 1,
    "nb_sinistres_anterieurs": 0,
    "accident_nuit": False,
    "presence_temoins": True,
    "constat_amiable": False,
}

SINISTRE_SUSPECT = {
    **SINISTRE_SIMPLE,
    "type_vehicule": "taxi",
    "type_sinistre": "incendie",
    "valeur_vehicule": 20.0,
    "classe_bonus_malus": 10,
    "montant_declare": 24000,
    "delai_declaration_jours": 60,
    "nb_sinistres_anterieurs": 4,
    "accident_nuit": True,
    "presence_temoins": False,
}


def test_health():
    r = client.get("/health")
    assert r.status_code == 200
    assert r.json()["statut"] == "ok"


def test_predict_schema():
    r = client.post("/predict", json=SINISTRE_SIMPLE)
    assert r.status_code == 200
    body = r.json()
    assert 0.0 <= body["score_risque"] <= 1.0
    assert body["niveau_risque"] in {"FAIBLE", "MOYEN", "ELEVE"}
    assert body["version_modele"].startswith("risque-sinistre-")


def test_sinistre_suspect_plus_risque_que_simple():
    simple = client.post("/predict", json=SINISTRE_SIMPLE).json()
    suspect = client.post("/predict", json=SINISTRE_SUSPECT).json()
    assert simple["niveau_risque"] == "FAIBLE"
    assert suspect["niveau_risque"] == "ELEVE"
    assert suspect["score_risque"] > simple["score_risque"]


def test_entree_invalide_rejetee():
    r = client.post("/predict", json={**SINISTRE_SIMPLE, "type_sinistre": "inconnu"})
    assert r.status_code == 422
