"""Entraîne un modèle avant les tests s'il n'existe pas encore (dépôt fraîchement cloné, CI)."""

import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent


def pytest_configure(config):
    if not (ROOT / "models" / "risk_model.joblib").exists():
        subprocess.run([sys.executable, "generate_data.py"], cwd=ROOT, check=True)
        subprocess.run([sys.executable, "train.py"], cwd=ROOT, check=True)
