from flask import Flask, request, jsonify
import pandas as pd

app = Flask(__name__)

@app.route("/recommend", methods=["POST"])
def recommend():
    data = request.get_json()
    keywords = set([kw.lower() for kw in data.get("keywords", [])])
    products = data.get("products", [])

    matched = []
    for product in products:
        name = product["name"].lower()
        if any(kw in name for kw in keywords):
            matched.append(product)

    return jsonify(matched)

if __name__ == "__main__":
    app.run(port=5000)
