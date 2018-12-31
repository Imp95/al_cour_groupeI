from flask import Flask, request
from flask import jsonify
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

# debug
from pprint import pprint
# pprint(vars(var)) to print complexe object

app = Flask(__name__)

app.config.from_object('config')

engine = create_engine(app.config['SQLALCHEMY_DATABASE_URI'])
Session = sessionmaker(bind=engine)
session = Session()

@app.route('/receive_event', methods=['POST'])
def receiveEvent():
    if not request.json:
        return jsonify(status = False, body = "Le contenu de la requete n'est pas valide")
    print(request.data)
    if request == None:
        return jsonify(status = False, body = "Requete vide")

    jsonData = request.get_json(force=True)
    
    if not 'action' in jsonData or not 'body' in jsonData:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (globale) ne sont pas presentes.")
    
    if jsonData["action"] == "Connexion":
        return connexionAction(jsonData["body"])
    elif jsonData["action"] == "Inscription":
        return "Non implemente"
    elif jsonData["action"] == "RechercheAnnonce":
        return "Non implemente"
    elif jsonData["action"] == "CreationOffre":
        return "Non implemente"
    elif jsonData["action"] == "VoirOffres":
        return "Non implemente"
    elif jsonData["action"] == "VoirContrats":
        return "Non implemente"
    elif jsonData["action"] == "UpdateContrats":
        return "Non implemente"
    elif jsonData["action"] == "HistoriqueContratsEmail":
        return "Non implemente"
    else:
        return jsonify(status = False, body = "L'action demandee n'existe pas.")

if __name__ == "__main__":
    app.run()

def connexionAction(body):
    from .models import User
    if not 'email' in body or not 'mdp' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (connexion) ne sont pas presentes.")

    user = session.query(User).filter(User.email==body["email"]).first()

    if user is None:
        return jsonify(status = False, body = "Cet utilisateur n'existe pas !")

    if user.password != body["mdp"]:
        return jsonify(status = False, body = "Mot de passe incorrect !")
    
    return jsonify(status = True, body = user.toJSON())
