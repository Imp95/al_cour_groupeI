from flask import Flask, request
from flask import jsonify
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from datetime import datetime
from sqlalchemy import exc, and_

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

    jsonData = request.get_json(force=True)
    
    if not 'action' in jsonData or not 'body' in jsonData:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (globale) ne sont pas presentes.")
    
    if jsonData["action"] == "Connexion":
        return connexionAction(jsonData["body"])
    elif jsonData["action"] == "Inscription":
        return inscriptionAction(jsonData["body"])
    elif jsonData["action"] == "RechercheAnnonce":
        return findAdAction(jsonData["body"])
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

def connexionAction(body):
    from .models import User
    if not 'email' in body or not 'password' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (connexion) ne sont pas presentes.")

    user = session.query(User).filter(User.email==body["email"]).first()

    if user is None:
        return jsonify(status = False, body = "Cet utilisateur n'existe pas !")

    if user.password != body["password"]:
        return jsonify(status = False, body = "Mot de passe incorrect !")
    
    return jsonify(status = True, body = user.toJSON())

def inscriptionAction(body):
    from .models import User
    if not 'email' in body or not 'password' in body or not 'name' in body or not 'firstname' in body or not 'birthday' in body or not 'phone_number' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (inscription) ne sont pas presentes.")

    userToCreate = User(body["email"], body["password"], body["name"], body["firstname"], datetime.strptime(body["birthday"], '%Y-%m-%d'), body["phone_number"], 0)

    try:
        session.add(userToCreate)
        session.commit()
    except exc.IntegrityError as e:
        print(e)
        session.rollback()
        return jsonify(status = False, body = "Probleme d'integrite de la requete.")
    
    return jsonify(status = True, body = userToCreate.toJSON())

def findAdAction(body):
    from .models import Ad
    if not 'departure_town' in body or not 'arrival_town' in body or not 'bagage' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (recherche d'annonce) ne sont pas presentes.")

    ads = session.query(Ad).filter(and_(Ad.departure_address.like('%'+body["departure_town"]+'%'), Ad.arrival_address.like('%'+body["arrival_town"]+'%'), Ad.bagage.like('%'+body["bagage"]+'%'))).all()

    result = []
    for ad in ads:
        item = ad.toJSON()
        result.append(item)
    
    return jsonify(status = True, body = result)