from .switchman import app
from flask import json

import pytest

def test_missing_parameters():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateAmount", "body":{"user_email_not_a_param":"null"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Les cles obligatoires de parsage (mise a jour montant) ne sont pas presentes."

def test_bad_user_email():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateAmount", "body":{"user_email":"not_a_valid_email"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Cet utilisateur n'existe pas !"

def test_ok():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"UpdateAmount", "body":{"user_email":"example3@email.com"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert data['body']['amount'] == 999