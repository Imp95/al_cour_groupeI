from flask import Flask, request
from connexion_module import connexion_response
app = Flask(__name__)

@app.route('/receive_event', methods=['POST'])
def computeEta():
    jsonFile = request.get_json(force=True)
    if jsonFile["action"] == "Connexion":
        return connexion_response(jsonFile["body"])
    else :
        return "Not-A-JSON"

@app.route('/')
def main():
    return "Welcome"

if __name__ == '__main__':
    app.run(host='192.168.0.42',port='80')
