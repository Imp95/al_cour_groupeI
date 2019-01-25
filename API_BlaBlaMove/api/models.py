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

    ads = db.relationship('Ad', backref="user", lazy=True)
    offers = db.relationship('Offer', backref="user", lazy=True)

    def __init__(self, email, password, name, firstname, birthday, phone_number, amount):
        self.email = email
        self.password = password
        self.name = name
        self.firstname = firstname
        self.birthday = birthday
        self.phone_number = phone_number
        self.amount = amount

    def toJSON(self):
        json = {
            "id":self.id,
            "email":self.email,
            "name":self.name,
            "firstname":self.firstname,
            "birthday":self.birthday.strftime("%Y-%m-%d"),
            "phone_number":self.phone_number,
            "amount":self.amount
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
            "departure_date":self.departure_date.strftime("%Y-%m-%d"),
            "arrival_date":self.arrival_date.strftime("%Y-%m-%d"),
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

    carrier_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    ad_id = db.Column(db.Integer, db.ForeignKey('ad.id'), nullable=False)
    bagage = db.Column(db.String(255), db.ForeignKey('ad.bagage'), nullable=False)
    payment = db.Column(db.Integer,db. ForeignKey('ad.payment'), nullable=False)
    departure_address = db.Column(db.Text, db.ForeignKey('ad.departure_address'), nullable=False)
    arrival_address = db.Column(db.Text, db.ForeignKey('ad.arrival_address'), nullable=False)

    def __init__(self, proposed_date, carrier_id, ad_id, bagage, payment, departure_address, arrival_address):
        self.proposed_date = proposed_date
        self.status = 0
        self.carrier_id = carrier_id
        self.ad_id = ad_id
        self.bagage = bagage
        self.payment = payment
        self.departure_address = departure_address
        self.arrival_address = arrival_address

    def toJSON(self):
        json = {
            "id":self.id,
            "proposed_date":self.proposed_date.strftime("%Y-%m-%d"),
            "status":self.status,
            "carrier_id":self.carrier_id,
            "bagage":self.bagage,
            "payment":self.payment,
            "departure_address":self.departure_address,
            "arrival_address":self.arrival_address,
            "ad_id":self.ad_id
        }
        return json

class Contract(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    proposed_date = db.Column(db.DateTime, nullable=False)
    status = db.Column(db.Integer, nullable=False)
    deposit_accused = db.Column(db.Integer, nullable=False)
    acknowledgement = db.Column(db.Integer, nullable=False)

    offer_id = db.Column(db.Integer, db.ForeignKey('offer.id'), nullable=False, unique=True)
    payment = db.Column(db.Integer,db. ForeignKey('ad.payment'), nullable=False)
    departure_address = db.Column(db.Text, db.ForeignKey('ad.departure_address'), nullable=False)
    arrival_address = db.Column(db.Text, db.ForeignKey('ad.arrival_address'), nullable=False)

    def __init__(self, proposed_date, deposit_accused, acknowledgement, offer_id, payment, departure_address, arrival_address):
        self.proposed_date = proposed_date
        self.status = 0
        self.deposit_accused = deposit_accused
        self.acknowledgement = acknowledgement
        self.offer_id = offer_id
        self.payment = payment
        self.departure_address = departure_address
        self.arrival_address = arrival_address

    def toJSON(self):
        json = {
            "id":self.id,
            "proposed_date":self.proposed_date.strftime("%Y-%m-%d"),
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
    user1 = User('example1@email.com', 'azerty', 'Doe', 'John', datetime.strptime('1980-01-20', '%Y-%m-%d'), '0122334455', 0)
    user2 = User('example2@email.com', 'password', 'Bon', 'Jean', datetime.strptime('1977-03-18', '%Y-%m-%d'), '0322334459', 50)
    user3 = User('example3@email.com', 'pass', 'Nyan', 'Cat', datetime.strptime('1999-11-22', '%Y-%m-%d'), '0999999999', 999)
    user4 = User('example4@email.com', 'passu', 'Nyanpasu', 'Jul', datetime.strptime('1999-11-14', '%Y-%m-%d'), '0999777999', 99)
    db.session.add(user1)
    db.session.add(user2)
    db.session.add(user3)
    db.session.add(user4)
    db.session.commit()
    # Ads
    ad1 = Ad(datetime.strptime('2018-01-13', '%Y-%m-%d'), datetime.strptime('2018-01-14', '%Y-%m-%d'), '15x15x15', 50, 0, '3 rue swag 03333 Magic Town', '3 rue nulle 09333 Meat City', user1.id)
    ad2 = Ad(datetime.strptime('2018-01-22', '%Y-%m-%d'), datetime.strptime('2018-01-23', '%Y-%m-%d'), '15x15x15', 50, 0, 'none', 'none', user4.id)
    ad3 = Ad(datetime.strptime('2018-01-24', '%Y-%m-%d'), datetime.strptime('2018-01-25', '%Y-%m-%d'), '15x15x15', 50, 0, 'none', 'none', user4.id)
    db.session.add(ad1)
    db.session.add(ad2)
    db.session.add(ad3)
    db.session.add(Ad(datetime.strptime('2018-01-18', '%Y-%m-%d'), datetime.strptime('2018-01-19', '%Y-%m-%d'), '15x15x30', 50, 0, '3 rue swag 03333 Ville', '3 rue nulle 09333 Villeuuu', user2.id))
    db.session.add(Ad(datetime.strptime('2018-01-22', '%Y-%m-%d'), datetime.strptime('2018-01-24', '%Y-%m-%d'), '15x15x74', 45, 0, '3 rue swag 03333 Ville', '3 rue nulle 09333 Meat City', user1.id))
    db.session.commit()
    # Offers
    offer1 = Offer(datetime.strptime('2018-01-13 14:30', '%Y-%m-%d %M:%S'), user2.id, ad1.id, ad1.bagage, ad1.payment, ad1.departure_address, ad1.arrival_address)
    offer2 = Offer(datetime.strptime('2018-01-02 13:00', '%Y-%m-%d %M:%S'), user4.id, ad2.id, ad2.bagage, ad2.payment, ad2.departure_address, ad2.arrival_address)
    offer3 = Offer(datetime.strptime('2018-01-06 16:00', '%Y-%m-%d %M:%S'), user1.id, ad3.id, ad3.bagage, ad3.payment, ad3.departure_address, ad3.arrival_address)
    db.session.add(offer1)
    db.session.add(offer2)
    db.session.add(offer3)
    db.session.add(Offer(datetime.strptime('2018-01-18 15:50', '%Y-%m-%d %M:%S'), user2.id, ad1.id, ad1.bagage, ad1.payment, ad1.departure_address, ad1.arrival_address))
    db.session.commit()
    # Contracts
    contract2 = Contract('2018-01-12', 111, 222, offer2.id, offer2.payment, offer2.departure_address, offer2.arrival_address)
    contract2.status = 2
    db.session.add(Contract('2018-01-14', 579, 975, offer1.id, offer1.payment, offer1.departure_address, offer1.arrival_address))
    db.session.add(contract2)
    db.session.add(Contract('2018-01-11', 333, 444, offer3.id, offer3.payment, offer3.departure_address, offer3.arrival_address))
    db.session.commit()
    lg.warning('Database initialized!')
