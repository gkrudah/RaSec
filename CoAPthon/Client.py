from coapthon.client.helperclient import HelperClient
import threading
from collections import OrderedDict
import json


class coapClient(threading.Thread):
	def __init__(self, jsondata):
		threading.Thread.__init__(self)
		self.jsondata = jsondata
		# self.lock = lock
		self.host = self.jsondata.getServerIp()
		self.port = self.jsondata.getPort()
		self.deviceid = self.jsondata.getDeviceId()
		self.client = HelperClient(server=(self.host, self.port))
		return

	def generatePayload(self, post=False):
		payload = OrderedDict()

		payload["DeviceID"] = self.deviceid
		payload["State"] = self.jsondata.getState()
		payload['CamState'] = self.jsondata.getCamera()
		payload['BuzzerState'] = self.jsondata.getBuzzer()
		# payload['Detect'] = self.jsondata.getDetect()

		if post:
			payload['Mode'] = self.jsondata.getMode()

		return json.dumps(payload, ensure_ascii=False, indent='\t')

	def requestPost(self):
		payload = self.generatePayload(post=True)

		try:
			response = self.client.post(path="connect", payload=payload)
			print(response.pretty_print())
		except Exception:
			print('##############################')
			print(Exception)

		return

	def requestPut(self, path):
		payload = self.generatePayload()
		return self.client.put(path, payload)

	def requestGet(self, interval=1.0):
		response = self.client.get(path='control/' + self.deviceid)
		print(response.payload)
		data = json.loads(response.payload)
		self.jsondata.setCamera(data['CamState'])
		self.jsondata.setBuzzer(data['BuzzerState'])
		# print("Camstate: ", data['CamState'])
		# print("Buzzerstate: ", data['BuzzerState'])

		threading.Timer(interval, self.requestGet).start()
		return

	def requestObserve(self, path):
		return self.client.observe(path, self.client_callback_observe)

	def handleObserve(self, jsonresponse):
		if jsonresponse is not None:
			response = json.loads(jsonresponse)
			print(response['State'])
			print(response['Camera'])
			print(response['Buzzer'])
		return

	def client_callback_observe(self, response):
		if response.payload is not None:
			data = json.loads(response.payload)
			# self.jsondata.setCamera(data['CamState'])
			# self.jsondata.setBuzzer(data['BuzzerState'])
			print("Camstate: ", data['CamState'])
			print("Buzzerstate: ", data['BuzzerState'])
		'''
		check = True
		while check:
			chosen = eval(input("Stop observing? [y/N]: "))
			if chosen != "" and not (chosen == "n" or chosen == "N" or chosen == "y" or chosen == "Y"):
				print("Unrecognized choose.")
				continue
			elif chosen == "y" or chosen == "Y":
				while True:
					rst = eval(input("Send RST message? [Y/n]: "))
					if rst != "" and not (rst == "n" or rst == "N" or rst == "y" or rst == "Y"):
						print("Unrecognized choose.")
						continue
					elif rst == "" or rst == "y" or rst == "Y":
						self.client.cancel_observing(response, True)
					else:
						self.client.cancel_observing(response, False)
					check = False
					break
			else:
				break
		'''

	def checkState(self, interval=1.0):
		'''
		self.lock.acquire()
		try:
			state = self.jsondata.getState()
		except Exception:
			state = 'off'
			print(Exception)
		finally:
			self.lock.release()
		'''

		if self.jsondata.getState() == 'on':
			# turn on rasec if it is off
			if self.jsondata.getFirst():
				put_response = self.requestPut(path='report/' + self.deviceid)
				self.jsondata.setFirst(False)
				print(put_response.pretty_print())
		else:
			if not self.jsondata.getFirst():
				self.jsondata.setFirst(True)

		threading.Timer(interval, self.checkState).start()
		return

	def run(self):
		self.requestPost()

		observe_response = self.requestObserve(path='obs/' + self.deviceid)
		# self.handleObserve(observe_response)

		self.checkState()
		# self.requestGet()

		# get_response = self.requestGet(path='control/' + self.jsondata.getDeviceId())
		# print(get_response.pretty_print())

		return

	def end_trans(self):
		self.client.stop()
		return
