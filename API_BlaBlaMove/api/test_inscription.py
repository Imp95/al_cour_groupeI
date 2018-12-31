from .switchman import app
from flask import json

import pytest

def test_missing_parameters():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"Inscription", "body":{"email":"aaa@aaa.com", "password":"null", "badparam":"tsunchun"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Les cles obligatoires de parsage (inscription) ne sont pas presentes."

def test_already_used_mail():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"Inscription", "body":{"email":"example1@email.com", "password":"pass", "name":"Chun", "firstname":"Tsun", "birthday":"2000-01-20", "phone_number":"0411223344"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Probleme d'integrite de la requete."

def test_ok():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"Inscription", "body":{"email":"example@mail.com", "password":"pass", "name":"Chun", "firstname":"Tsun", "birthday":"2000-01-20", "phone_number":"0411223344"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert data['body']['name'] == "Chun"
    assert data['body']['phone_number'] == "0411223344"
    assert data['body']['birthday'] == "2000-01-20"