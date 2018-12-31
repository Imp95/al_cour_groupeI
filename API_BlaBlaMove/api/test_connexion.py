from .switchman import app
from flask import json
from .models import init_db

import pytest

@pytest.fixture
def fix():
    models.init_db()

def test_missing_parameters():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"Connexion", "body":{"email":"aaa@aaa.com", "badparam":"null"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Les cles obligatoires de parsage (connexion) ne sont pas presentes."

def test_bad_email():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"Connexion", "body":{"email":"aaa@aaa.com", "mdp":"strong"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Cet utilisateur n'existe pas !"

def test_bad_password():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"Connexion", "body":{"email":"example1@email.com", "mdp":"azertyu"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Mot de passe incorrect !"

def test_ok():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"Connexion", "body":{"email":"example1@email.com", "mdp":"azerty"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert data['body']['name'] == "Doe"