from flask import Flask, request
from flask import jsonify
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from datetime import datetime
from sqlalchemy import exc, and_

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
        return createOfferAction(jsonData["body"])
    elif jsonData["action"] == "VoirOffres":
        return seeOffersAction(jsonData["body"])
    elif jsonData["action"] == "VoirContrats":
        return seeContractsAction(jsonData["body"])
    elif jsonData["action"] == "UpdateContrat":
        return updateContractAction(jsonData["body"])
    elif jsonData["action"] == "HistoriqueContrats":
        return logContractsAction(jsonData["body"])
    elif jsonData["action"] == "UpdateAmount":
        return updateAmountAction(jsonData["body"])
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

    ads = session.query(Ad).filter(and_(Ad.departure_address.like('%'+body["departure_town"].upper()+'%'), Ad.arrival_address.like('%'+body["arrival_town"].upper()+'%'))).all()

    result = []
    if len(body["bagage"].split('x')) == 3:
        x0,y0,z0 = body["bagage"].split('x')   
        for ad in ads:
            x,y,z = ad.bagage.split('x')
            if x <= x0 and y <= y0 and z <= z0:
                item = ad.toJSON()
                result.append(item)
    else:
        for ad in ads:
            item = ad.toJSON()
            result.append(item)
    
    return jsonify(status = True, body = result)

def createOfferAction(body):
    from .models import Offer, User, Ad
    if not 'ad_id' in body or not 'carrier_email' in body or not 'proposed_date' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (creation d'offre) ne sont pas presentes.")
    
    user = session.query(User).filter(User.email==body["carrier_email"]).first()
    if user is None:
        return jsonify(status = False, body = "Cet email n'existe pas !")
	
    ad = session.query(Ad).get(body["ad_id"])
    if ad is None:
        return jsonify(status = False, body = "Cette annonce n'existe pas !")
    offerToCreate = Offer(body["proposed_date"], ad.bagage, ad.payment, ad.departure_address, ad.arrival_address, user.id, ad.id)

    try:
        session.add(offerToCreate)
        session.commit()
    except exc.IntegrityError as e:
        print(e)
        session.rollback()
        return jsonify(status = False, body = "Probleme d'integrite de la requete.")

    return jsonify(status = True, body = offerToCreate.toJSON())

def seeOffersAction(body):
    from .models import Offer, User
    if not 'user_email' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (voir offres) ne sont pas presentes.")
    
    user = session.query(User).filter(User.email==body["user_email"]).first()
    if user is None:
        return jsonify(status = False, body = "Cet email n'existe pas !")

    offers = session.query(Offer).filter(and_(Offer.carrier_id == user.id, Offer.status == 0)).all()

    result = []
    for offer in offers:
        item = offer.toJSON()
        result.append(item)

    return jsonify(status = True, body = result)

def seeContractsAction(body):
    from .models import Contract, Offer, User
    if not 'user_email' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (voir contrats) ne sont pas presentes.")
    
    user = session.query(User).filter(User.email==body["user_email"]).first()
    if user is None:
        return jsonify(status = False, body = "Cet email n'existe pas !")

    offers = session.query(Offer).filter(Offer.carrier_id == user.id).all()

    contracts = []
    for offer in offers:
        contract = session.query(Contract).filter(and_(Contract.offer_id==offer.id, Contract.status < 2)).first()
        if not contract is None:
            contracts.append(contract.toJSON())

    return jsonify(status = True, body = contracts)

def updateContractAction(body):
    from .models import Contract, Offer, User
    if not 'id_contract' in body or not 'preuve' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (mise a jour contrat) ne sont pas presentes.")
    
    contract = session.query(Contract).filter(Contract.id==body["id_contract"]).first()
    if contract is None:
        return jsonify(status = False, body = "Ce contrat n'existe pas !")

    if contract.status == 0:
        if contract.deposit_accused == body["preuve"]:
            contract.status = 1
            session.commit()
        else:
            return jsonify(status = False, body = "Preuve de depot incorrecte !")
    elif contract.status == 1:
        if contract.acknowledgement == body["preuve"]:
            contract.status = 2
            offer = session.query(Offer).filter(Offer.id==contract.offer_id).first()
            user = session.query(User).filter(User.id==offer.carrier_id).first()
            user.amount += offer.payment
            session.commit()
        else:
            return jsonify(status = False, body = "Preuve de reception incorrecte !")
    else:
        return jsonify(status = False, body = "Le contrat ne peut etre mis a jour !")

    return jsonify(status = True, body = contract.toJSON())

def logContractsAction(body):
    from .models import Contract, Offer, User
    if not 'user_email' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (historique contrats) ne sont pas presentes.")
    
    user = session.query(User).filter(User.email==body["user_email"]).first()
    if user is None:
        return jsonify(status = False, body = "Cet email n'existe pas !")

    offers = session.query(Offer).filter(Offer.carrier_id == user.id).all()

    contracts = []
    for offer in offers:
        contract = session.query(Contract).filter(and_(Contract.offer_id==offer.id, Contract.status == 2)).first()
        if not contract is None:
            contracts.append(contract.toJSON())

    return jsonify(status = True, body = contracts)

def updateAmountAction(body):
    from .models import User
    if not 'user_email' in body:
        return jsonify(status = False, body = "Les cles obligatoires de parsage (mise a jour montant) ne sont pas presentes.")

    user = session.query(User).filter(User.email==body["user_email"]).first()

    if user is None:
        return jsonify(status = False, body = "Cet utilisateur n'existe pas !")
    
    return jsonify(status = True, body = {"amount":user.amount})