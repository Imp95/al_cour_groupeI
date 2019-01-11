import api
import sys
from api import app

if __name__ == "__main__":
    host = 'localhost'
    port = '5555'

    argv = sys.argv[1:]

    # Example python3 run.py host=192.168.0.2 port=9999
    for i in range(0, len(argv)):
        splitted = argv[i].split('=', 1)
        if splitted[0] == 'host':
            host = splitted[1]
            continue
        elif splitted[0] == 'port':
            port = splitted[1]
            continue

    app.run(host=host,port=port,debug=True)