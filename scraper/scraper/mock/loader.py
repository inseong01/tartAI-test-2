import os
import json


def load_mock_data():
    base_dir = os.path.dirname(__file__)
    file_path = os.path.join(base_dir, "output.json")

    with open(file_path, "r", encoding="utf-8") as f:
        return json.load(f)
