# from coapthon.client.helperclient import HelperClient
# import json
# from collections import OrderedDict
# import threading

import Global
from Client import coapClient
from handleRas import machine

jsondata = Global.jsonData()
host = jsondata.getServerIp()
port = jsondata.getPort()
# lock = threading.Lock()


def main():
	rasec = machine(jsondata)
	rasec.start()

	client = coapClient(jsondata)
	client.start()

	# client.stop()
	return


if __name__ == '__main__':
	main()
