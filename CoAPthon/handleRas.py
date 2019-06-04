import threading
from time import sleep
import RPi.GPIO as GPIO
# from picamera import PiCamera

pirPin = 12


class machine(threading.Thread):
	def __init__(self, jsondata):
		threading.Thread.__init__(self)
		self.jsondata = jsondata

		GPIO.setmode(GPIO.BCM)
		GPIO.setup(pirPin, GPIO.IN)

		# self.lock = lock
		return

	def checkmachine(self, interval=0.5):

		if GPIO.input(pirPin):
			self.jsondata.setState('on')
		else:
			self.jsondata.setState('off')

		threading.Thread(interval, self.checkmachine).start()
		return

	def run(self):
		# codes for obtain sensor's info
		self.checkmachine()
		return
