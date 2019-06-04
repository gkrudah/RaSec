from coapthon.client.helperclient import HelperClient
import json


class Observer(object):
	def __init__(self, jsondata):
		self.jsondata = jsondata
		self.host = self.jsondata.getServerIp()
		self.port = self.jsondata.getPort()
		self.deviceid = self.jsondata.getDeviceId()
		self.client = HelperClient(server=(self.host, self.port))
		return

	def client_callback_observe(self, response):
		if response.payload is not None:
			data = json.loads(response.payload)
			# self.jsondata.setCamera(data['CamState'])
			# self.jsondata.setBuzzer(data['BuzzerState'])
			print("Camstate: ", data['CamState'])
			print("Buzzerstate: ", data['BuzzerState'])
		return

	def observe(self):
		response = self.client.observe(path='obs/' + self.deviceid, callback=self.client_callback_observe)
		return
