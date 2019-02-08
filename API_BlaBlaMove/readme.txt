
LINUX SHELL
installer pip3 (voir des tutos)

pip3 install flask
pip3 install flask_sqlalchemy
pip3 install pymysql
pip3 install sqlalchemy-utils
pip3 install pytest


service mysql start
_________________________________________________________________

MYSQL SHELL
CREATE DATABASE blablamove;
CREATE OR REPLACE USER 'blablamove' IDENTIFIED BY 'strongpassword';
GRANT ALL PRIVILEGES ON blablamove. * TO 'blablamove'@'%' IDENTIFIED BY 'strongpassword';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'blablamove'@'%';

__________________________________________________________________

lancer app
python3 run.py

FLASK_APP=run.py flask init_db


pytest
____________________
faire multiple update contrats!!!!!!
