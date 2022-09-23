import requests as requests
from flask import Flask, render_template, request, redirect, url_for, jsonify
import json

app = Flask(__name__)


class member:
    def __init__(self, name, id):
        self.name = name
        self.id = id

@app.route('/')
def hello_world():

    file_uproad()

    return 'Hello World!'

def file_uproad():
    data = {
        "name" : "won",
        "id" :  "asdsa113"
    }
    json_data = json.dumps(data)

    print(json_data)
    res = requests.post("http://localhost:8080/api/testApi", json=data)
    print("요청시도")
    print(res.headers)

if __name__ == '__main__':
    app.run()