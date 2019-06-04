import threading
from time import sleep
# from picamera import PiCamera


class machine(threading.Thread):
	def __init__(self, jsondata):
		threading.Thread.__init__(self)
		self.jsondata = jsondata
		# self.lock = lock
		return

	def run(self):
		# codes for obtain sensor's info
		return
