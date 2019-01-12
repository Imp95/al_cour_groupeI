from .switchman import app
from flask import json

import pytest

def test_missing_parameters():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"RechercheAnnonce", "body":{"departure_town":"Bad City", "badparam":"null"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == False
    assert data['body'] == "Les cles obligatoires de parsage (recherche d'annonce) ne sont pas presentes."

def test_find_empty():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"RechercheAnnonce", "body":{"departure_town":"null", "arrival_town":"null", "bagage":"null"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert len(data['body']) == 0

def test_find_all():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"RechercheAnnonce", "body":{"departure_town":"", "arrival_town":"", "bagage":""}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert len(data['body']) == 5

def test_regular_find_1():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"RechercheAnnonce", "body":{"departure_town":"", "arrival_town":"Meat City", "bagage":"15x15x74"}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert len(data['body']) == 1

def test_regular_find_2():        
    response = app.test_client().post(
        '/receive_event',
        data='{"action":"RechercheAnnonce", "body":{"departure_town":"", "arrival_town":"Meat City", "bagage":""}}',
        content_type='application/json',
    )

    data = json.loads(response.get_data(as_text=True))

    assert response.status_code == 200
    assert data['status'] == True
    assert len(data['body']) == 2