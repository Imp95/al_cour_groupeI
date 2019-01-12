from .switchman import app
from flask import json

import pytest

def test_missing_parameters():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"VoirContrats", "body":{"bad_param":"naaaab"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Les cles obligatoires de parsage (voir contrats) ne sont pas presentes."

def test_bad_user_mail():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"VoirContrats", "body":{"user_email":"bad.email@naze.lol"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Cet email n'existe pas !"

def test_see_empty():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"VoirContrats", "body":{"user_email":"example3@email.com"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert len(data['body']) == 0

def test_regular_see():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"VoirContrats", "body":{"user_email":"example2@email.com"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert len(data['body']) == 1