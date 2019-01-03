import api
from api import app

if __name__ == "__main__":
    app.run(host='192.168.0.42',port='80',debug=True)