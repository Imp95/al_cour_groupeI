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

class Offer(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    proposedDate = db.Column(db.DateTime, nullable=False)
    status = db.Column(db.Integer, nullable=False)

    carrier_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    ad_id = db.Column(db.Integer, db.ForeignKey('ad.id'), nullable=False)

    def __init__(self, proposedDate, status, carrier_id, ad_id):
        self.proposedDate = proposedDate
        self.status = status
        self.carrier_id = carrier_id
        self.ad_id = ad_id

class Contract(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    proposedDate = db.Column(db.DateTime, nullable=False)
    status = db.Column(db.Integer, nullable=False)
    depositAccused = db.Column(db.Integer, nullable=False)
    acknowledgement = db.Column(db.Integer, nullable=False)

    offer_id = db.Column(db.Integer, db.ForeignKey('offer.id'), nullable=False)

    def __init__(self, proposedDate, status, depositAccused, acknowledgement, offer_id):
        self.proposedDate = proposedDate
        self.status = status
        self.depositAccused = depositAccused
        self.acknowledgement = acknowledgement
        self.offer_id = offer_id

db.create_all()

def init_db():
    db.drop_all()
    db.create_all()
    # Users
    user1 = User('example1@email.com', 'azerty', 'Doe', 'John', datetime.strptime('1980-01-20', '%Y-%m-%d'), '0122334455', 0)
    user2 = User('example2@email.com', 'password', 'Bon', 'Jean', datetime.strptime('1977-03-18', '%Y-%m-%d'), '0322334459', 50)
    db.session.add(user1)
    db.session.add(user2)
    db.session.commit()
    # Ads
    db.session.add(Ad(datetime.strptime('2018-01-13', '%Y-%m-%d'), datetime.strptime('2018-01-14', '%Y-%m-%d'), '15x15x15', 50, 0, '3 rue swag 03333 Magic Town', '3 rue nulle 09333 Meat City', user1.id))
    db.session.add(Ad(datetime.strptime('2018-01-18', '%Y-%m-%d'), datetime.strptime('2018-01-19', '%Y-%m-%d'), '15x15x30', 50, 0, '3 rue swag 03333 Ville', '3 rue nulle 09333 Villeuuu', user2.id))
    db.session.add(Ad(datetime.strptime('2018-01-22', '%Y-%m-%d'), datetime.strptime('2018-01-24', '%Y-%m-%d'), '15x15x74', 45, 0, '3 rue swag 03333 Ville', '3 rue nulle 09333 Meat City', user1.id))
    db.session.commit()
    lg.warning('Database initialized!')
