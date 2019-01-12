from .switchman import app
from flask import json

import pytest

def test_missing_parameters():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateContrat", "body":{"id_contract":0, "bad_param":"naze"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Les cles obligatoires de parsage (mise a jour contrat) ne sont pas presentes."

def test_id_contract():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateContrat", "body":{"id_contract":0, "preuve":0}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Ce contrat n'existe pas !"

def test_global():        
    # bad deposit
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateContrat", "body":{"id_contract":1, "preuve":0}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Preuve de depot incorrecte !"

    # good deposit
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateContrat", "body":{"id_contract":1, "preuve":579}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert data['body']['status'] == 1
    assert data['body']['proposed_date'] == '2018-01-14'

    # bad acknowledgement
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateContrat", "body":{"id_contract":1, "preuve":0}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Preuve de reception incorrecte !"

    # good acknowledgement
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateContrat", "body":{"id_contract":1, "preuve":975}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert data['body']['status'] == 2
    assert data['body']['proposed_date'] == '2018-01-14'

    # bad acknowledgement
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateContrat", "body":{"id_contract":1, "preuve":555}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Le contrat ne peut etre mis a jour !"
