"""Schéma des variables utilisées par le modèle de risque des sinistres.

Les variables proviennent des entités existantes du backend
(Voiture, Client) et de la nouvelle entité Sinistre.
"""

CATEGORICAL_FEATURES = ["type_vehicule", "type_sinistre"]

NUMERIC_FEATURES = [
    "puissance_cv",
    "valeur_vehicule",
    "nb_places",
    "classe_bonus_malus",
    "montant_declare",
    "ratio_montant_valeur",
    "delai_declaration_jours",
    "nb_sinistres_anterieurs",
]

BOOLEAN_FEATURES = ["accident_nuit", "presence_temoins", "constat_amiable"]

FEATURES = CATEGORICAL_FEATURES + NUMERIC_FEATURES + BOOLEAN_FEATURES

TARGET = "sinistre_a_risque"

TYPES_VEHICULE = ["privee", "commerciale", "taxi"]
TYPES_SINISTRE = ["collision", "vol", "incendie", "bris_de_glace"]


def ratio_montant_valeur(montant_declare: float, valeur_vehicule: float) -> float:
    """Montant déclaré rapporté à la valeur du véhicule.

    valeur_vehicule est exprimée en milliers de dinars (comme Voiture.prix),
    montant_declare en dinars.
    """
    valeur_dt = max(valeur_vehicule, 0.1) * 1000
    return montant_declare / valeur_dt
