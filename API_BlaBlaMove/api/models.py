from flask_sqlalchemy import SQLAlchemy
from sqlalchemy_utils import EmailType
import logging as lg
from datetime import datetime
from .switchman import app

db = SQLAlchemy(app)

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(EmailType, nullable=False, unique=True)
    password = db.Column(db.String(55), nullable=False)
    name = db.Column(db.String(255), nullable=False)
    firstname = db.Column(db.String(255), nullable=False)
    birthday = db.Column(db.Date, nullable=False)
    phone_number = db.Column(db.String(20), nullable=False)
    amount = db.Column(db.Integer, nullable=False)
    societe = db.Column(db.String(255), nullable=False)

    ads = db.relationship('Ad', backref="user", lazy=True)
    offers = db.relationship('Offer', backref="user", lazy=True)

    def __init__(self, email, password, name, firstname, birthday, phone_number, amount, societe=""):
        self.email = email
        self.password = password
        self.name = name
        self.firstname = firstname
        self.birthday = birthday
        self.phone_number = phone_number
        self.amount = amount
        self.societe = societe

    def toJSON(self):
        json = {
            "id":self.id,
            "email":self.email,
            "name":self.name,
            "firstname":self.firstname,
            "birthday":self.birthday.strftime("%Y-%m-%d"),
            "phone_number":self.phone_number,
            "amount":self.amount,
            "societe":self.societe
        }
        return json

class Ad(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    departure_date = db.Column(db.DateTime, nullable=False)
    arrival_date = db.Column(db.DateTime, nullable=False)
    bagage = db.Column(db.String(255), nullable=False)
    payment = db.Column(db.Integer, nullable=False)
    status = db.Column(db.Integer, nullable=False)
    departure_address = db.Column(db.Text, nullable=False)
    arrival_address = db.Column(db.Text, nullable=False)

    offers = db.relationship('Offer', backref="ad", lazy=True)
    client_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)

    def __init__(self, departure_date, arrival_date, bagage, payment, status, departure_address, arrival_address, client_id):
        self.departure_date = departure_date
        self.arrival_date = arrival_date
        self.bagage = bagage
        self.payment = payment
        self.status = status
        self.departure_address = departure_address
        self.arrival_address = arrival_address
        self.client_id = client_id

    def toJSON(self):
        json = {
            "id":self.id,
            "departure_date":self.departure_date.strftime("%d-%m-%Y"),
            "arrival_date":self.arrival_date.strftime("%d-%m-%Y"),
            "bagage":self.bagage,
            "payment":self.payment,
            "status":self.status,
            "departure_address":self.departure_address,
            "arrival_address":self.arrival_address,
            "client_id":self.client_id
        }
        return json

class Offer(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    proposed_date = db.Column(db.DateTime, nullable=False)
    status = db.Column(db.Integer, nullable=False)
    bagage = db.Column(db.String(255), nullable=False)
    payment = db.Column(db.Integer, nullable=False)
    departure_address = db.Column(db.Text, nullable=False)
    arrival_address = db.Column(db.Text, nullable=False)

    carrier_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    ad_id = db.Column(db.Integer, db.ForeignKey('ad.id'), nullable=False)

    def __init__(self, proposed_date, bagage, payment, departure_address, arrival_address, carrier_id, ad_id):
        self.proposed_date = proposed_date
        self.status = 0
        self.bagage = bagage
        self.payment = payment
        self.departure_address = departure_address
        self.arrival_address = arrival_address
        self.carrier_id = carrier_id
        self.ad_id = ad_id

    def toJSON(self):
        json = {
            "id":self.id,
            "proposed_date":self.proposed_date.strftime("%d-%m-%Y %H:%M"),
            "status":self.status,
            "bagage":self.bagage,
            "payment":self.payment,
            "departure_address":self.departure_address,
            "arrival_address":self.arrival_address,
            "carrier_id":self.carrier_id,
            "ad_id":self.ad_id
        }
        return json

class Contract(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    proposed_date = db.Column(db.DateTime, nullable=False)
    status = db.Column(db.Integer, nullable=False)
    deposit_accused = db.Column(db.Integer, nullable=False)
    acknowledgement = db.Column(db.Integer, nullable=False)
    payment = db.Column(db.Integer,nullable=False)
    departure_address = db.Column(db.Text, nullable=False)
    arrival_address = db.Column(db.Text, nullable=False)

    offer_id = db.Column(db.Integer, db.ForeignKey('offer.id'), nullable=False, unique=True)

    def __init__(self, proposed_date, deposit_accused, acknowledgement, payment, departure_address, arrival_address, offer_id):
        self.proposed_date = proposed_date
        self.status = 0
        self.deposit_accused = deposit_accused
        self.acknowledgement = acknowledgement
        self.payment = payment
        self.departure_address = departure_address
        self.arrival_address = arrival_address
        self.offer_id = offer_id

    def toJSON(self):
        json = {
            "id":self.id,
            "proposed_date":self.proposed_date.strftime("%d-%m-%Y %H:%M"),
            "status":self.status,
            "deposit_accused":self.deposit_accused,
            "acknowledgement":self.acknowledgement,
            "payment":self.payment,
            "departure_address":self.departure_address,
            "arrival_address":self.arrival_address,
            "offer_id":self.offer_id
        }
        return json

def init_db():
    db.drop_all()
    db.create_all()
    # Users
    user1 = User('example1@email.com', 'azerty', 'Doe', 'John', datetime.strptime('1980-01-20', '%Y-%m-%d'), '0122334455', 0, "delete")
    user2 = User('example2@email.com', 'password', 'Valjean', 'Jean', datetime.strptime('1977-03-18', '%Y-%m-%d'), '0322334459', 50, "delete")
    user3 = User('example3@email.com', 'pass', 'Gasaï', 'Yuno', datetime.strptime('1999-11-22', '%Y-%m-%d'), '0999999999', 260)
    user4 = User('example4@email.com', 'passu', 'Marie', 'Julien', datetime.strptime('1999-11-14', '%Y-%m-%d'), '0999777999', 99, "delete")
    user5 = User('example5@email.com', 'azerty', 'Duminy', 'Gaétan', datetime.strptime('1995-06-18', '%Y-%m-%d'), '0601020304', 150, "Pro société")
    db.session.add(user1)
    db.session.add(user2)
    db.session.add(user3)
    db.session.add(user4)
    db.session.add(user5)
    db.session.commit()
    # Ads
    ad1 = Ad(datetime.strptime('13-01-2019', '%d-%m-%Y'), datetime.strptime('14-01-2019', '%d-%m-%Y'), '15x15x15', 80, 0, '3 rue liberté 03330 BELETTES', '3 rue de l\'élite 09333 SAINT-GRACIEN', user1.id)
    ad2 = Ad(datetime.strptime('22-01-2019', '%d-%m-%Y'), datetime.strptime('23-01-2019', '%d-%m-%Y'), '15x15x15', 50, 0, '53 avenue de Laplaine 01111 MEULIN', '42 place Elegante 69000 LYON', user4.id)
    ad3 = Ad(datetime.strptime('24-01-2019', '%d-%m-%Y'), datetime.strptime('26-01-2019', '%d-%m-%Y'), '15x15x22', 20, 0, '53 avenue de Laplaine 01111 MEULIN', '57 rue Sintillante 01111 MEULIN', user4.id)
    ad4 = Ad(datetime.strptime('23-01-2019', '%d-%m-%Y'), datetime.strptime('25-01-2019', '%d-%m-%Y'), '50x30x40', 50, 0, '3 rue Liberté 06000 NICE', '22 avenue Paradis 91000 PARIS', user2.id)
    ad5 = Ad(datetime.strptime('22-01-2019', '%d-%m-%Y'), datetime.strptime('24-01-2019', '%d-%m-%Y'), '50x30x40', 50, 0, '20 boulevard Saint-Roch 06000 NICE', '108 rue Belleville 31000 TOULOUSE', user5.id)
    db.session.add(ad1)
    db.session.add(ad2)
    db.session.add(ad3)
    db.session.add(ad4)
    db.session.add(ad5)
    db.session.add(Ad(datetime.strptime('18-01-2019', '%d-%m-%Y'), datetime.strptime('20-01-2019', '%d-%m-%Y'), '15x15x30', 20, 0, '3 rue ferry 06700 SAINT-LAURENT', '3 rue crocus 06700 SAINT-LAURENT', user2.id))
    db.session.add(Ad(datetime.strptime('22-01-2019', '%d-%m-%Y'), datetime.strptime('24-01-2019', '%d-%m-%Y'), '15x15x74', 35, 0, '3 rue bonaparte 03333 VILLENEUVE', '3 rue ibiscus 06700 SAINT-LAURENT', user1.id))
    ad8 = Ad(datetime.strptime('22-01-2019', '%d-%m-%Y'), datetime.strptime('24-01-2019', '%d-%m-%Y'), '80x60x50', 70, 0, '3 rue bonaparte 03333 VILLENEUVE', '3 rue ibiscus 06700 SAINT-LAURENT', user1.id)
    ad9 = Ad(datetime.strptime('22-01-2019', '%d-%m-%Y'), datetime.strptime('24-01-2019', '%d-%m-%Y'), '80x60x50', 70, 0, '10 rue Laville 91000 PARIS', '38 rue Foch 06000 NICE', user1.id)
    db.session.add(ad8)
    db.session.add(ad9)
    db.session.commit()
    # Offers
    offer1 = Offer(datetime.strptime('13-01-2019 16:00', '%d-%m-%Y %H:%M'), '15x15x15', 80, '3 rue liberté 03330 BELETTES', '3 rue de l\'élite 09333 SAINT-GRACIEN', user2.id, ad1.id)
    offer2 = Offer(datetime.strptime('22-01-2019 11:00', '%d-%m-%Y %H:%M'), '15x15x15', 50, '53 avenue de Laplaine 01111 MEULIN', '42 place Elegante 69000 LYON', user4.id, ad2.id)
    offer3 = Offer(datetime.strptime('24-01-2019 08:30', '%d-%m-%Y %H:%M'), '15x15x22', 20, '53 avenue de Laplaine 01111 MEULIN', '57 rue Sintillante 01111 MEULIN', user1.id, ad3.id)
    db.session.add(offer1)
    db.session.add(offer2)
    db.session.add(offer3)
    db.session.add(Offer(datetime.strptime('18-01-2019 08:20', '%d-%m-%Y %H:%M'), '15x15x15', 80, '3 rue liberté 03330 BELETTES', '3 rue de l\'élite 09333 SAINT-GRACIEN', user2.id, ad1.id))
    db.session.add(Offer(datetime.strptime('23-01-2019 11:00', '%d-%m-%Y %H:%M'), '50x30x40', 50, '20 boulevard Saint-Roch 06000 NICE', '108 rue Belleville 31000 TOULOUSE', user3.id, ad8.id))
    db.session.commit()
    # Contracts
    contract2 = Contract(datetime.strptime('13-01-2019 16:00', '%d-%m-%Y %H:%M'), 114218181, 202255816, 50, '53 avenue de Laplaine 01111 MEULIN', '42 place Elegante 69000 LYON', offer2.id)
    contract2.status = 2
    db.session.add(Contract(datetime.strptime('22-01-2019 11:00', '%d-%m-%Y %H:%M'), 475644263, 491141063, 80, '3 rue liberté 03330 BELETTES', '3 rue de l\'élite 09333 SAINT-GRACIEN', offer1.id))
    db.session.add(contract2)
    db.session.add(Contract(datetime.strptime('24-01-2019 08:30', '%d-%m-%Y %H:%M'), 534350963, 964807063, 20, '53 avenue de Laplaine 01111 MEULIN', '57 rue Sintillante 01111 MEULIN', offer3.id))
    ad1.status = 1
    ad2.status = 1
    ad3.status = 1
    offer1.status = 1
    offer2.status = 1
    offer3.status = 1
    db.session.commit()
    lg.warning('Database initialized!')
