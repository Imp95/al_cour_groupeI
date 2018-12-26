from flask import jsonify
def connexion_response(body):
    print(body["email"])
    data = {
            "id": 3,
            "email": "example1@email.com",
            "password": "azerty",
            "name": "Doe",
            "firstname": "John",
            "birthday": "1980-01-20",
            "phone_number": "0122334455",
            "amount": 421
        }
    
    return jsonify(status = "true", body = data)
