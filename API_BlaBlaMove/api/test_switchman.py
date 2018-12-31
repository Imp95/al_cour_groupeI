from .switchman import app
from flask import json
from .models import init_db

import pytest

@pytest.fixture
def fix():
    models.init_db()

def test_no_json():        
    response = app.test_client().post(
        '/receive_event',
        data='not a json',
        content_type='application/text',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Le contenu de la requete n'est pas valide"

def test_empty():        
    response = app.test_client().post(
        '/receive_event',
        data=None,
        content_type='application/json',
    )

    assert response.status_code == 400

def test_malformed():        
    response = app.test_client().post(
        '/receive_event',
        data='malformed',
        content_type='application/json',
    )
    
    assert response.status_code == 400

def test_missing_parameters():        
    response = app.test_client().post(
        '/receive_event',
        data=json.dumps({'action': 'some', 'boo': 'some'}),
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Les cles obligatoires de parsage (globale) ne sont pas presentes."

def test_bad_action():        
    response = app.test_client().post(
        '/receive_event',
        data=json.dumps({'action': 'bad action', 'body': 'some'}),
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "L'action demandee n'existe pas."