import threading
import socket
from collections import OrderedDict
import json
import requests
from datetime import datetime


class imageHandler(threading.Thread):
	def __init__(self, jsondata):
		threading.Thread.__init__(self)
		self.jsondata = jsondata
		# self.sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		self.url = "http://" + self.jsondata.getServerIp() + ":8080/RaSec/photos"
		self.date = ""
		return

	def sendImage(self):
		payload = OrderedDict()
		with open('./bing.jpg', mode='rb') as file:
			img = file.read()
		# img_bytes = bytearray(img)
		# img_bytes = "".join(map(chr, img))

		payload['name'] = datetime.now()
		payload['imageByte'] = img
		# payload = json.dumps(data, ensure_ascii=False, indent='\t')
		return requests.put(self.url, data=payload)
		# return self.sock.sendto(img, ('127.0.0.1', 5683))

	def run(self):
		response = self.sendImage()
		print(response)
		return
