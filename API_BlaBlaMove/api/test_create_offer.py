from .switchman import app
from flask import json

import pytest

def test_missing_parameters():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"CreationOffre", "body":{"ad_id":1, "proposed_date":"2000-01-20", "badparam":"naaaa"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Les cles obligatoires de parsage (creation d'offre) ne sont pas presentes."

def test_bad_user_mail():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"CreationOffre", "body":{"ad_id":1, "proposed_date":"2000-01-20", "carrier_email":"bad.email@naze.lol", "bagage":"9x9x9", "payment":46, "departure_address":"adressD", "arrival_address":"adressA"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Cet email n'existe pas !"

def test_bad_ad_id():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"CreationOffre", "body":{"ad_id":999, "proposed_date":"2000-01-20", "carrier_email":"example1@email.com", "bagage":"9x9x9", "payment":46, "departure_address":"adressD", "arrival_address":"adressA"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Probleme d'integrite de la requete."

def test_ok():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"CreationOffre", "body":{"ad_id":1, "proposed_date":"2000-01-20", "carrier_email":"example1@email.com", "bagage":"9x9x9", "payment":46, "departure_address":"adressD", "arrival_address":"adressA"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert data['body']['status'] == 0
    assert data['body']['carrier_id'] == 1
    assert data['body']['proposed_date'] == "2000-01-20"
