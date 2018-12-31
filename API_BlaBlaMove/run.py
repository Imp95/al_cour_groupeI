import api
from api import app

if __name__ == "__main__":
    app.run(host='localhost',port='9999',debug=True)