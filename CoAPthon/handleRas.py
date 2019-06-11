import threading
from time import sleep
# from picamera import PiCamera
# import RPi.GPIO as GPIO
# from picamera import PiCamera

pirPin = 12


class machine(threading.Thread):
	def __init__(self, jsondata):
		threading.Thread.__init__(self)
		self.jsondata = jsondata
		# self.camera = PiCamera()
		# self.camera.resolution = (2592, 1944)
		# self.camera.start_preview()
		# self.picnum = 0

		# GPIO.setmode(GPIO.BCM)
		# GPIO.setup(pirPin, GPIO.IN)

		# self.lock = lock
		return
		'''
	def ___checkmachine(self, interval=3.0):

		# tmp = input("set State on, off")
		# if tmp == 'on':
			# self.jsondata.setState('on')
		# else:
			# self.jsondata.setState('off')
		
		if GPIO.input(pirPin):
			self.jsondata.setState('on')
		else:
			self.jsondata.setState('off')
		
		print(self.jsondata.getBuzzer())
		if self.jsondata.getBuzzer():
			print("Buzzer Ring!")

		threading.Timer(interval, self.checkmachine).start()
		return
		'''

	def checkmachine(self):
		while(True):
			print(self.jsondata.getBuzzer())
			if self.jsondata.getBuzzer():
				print("Buzzer Ring!")
				# self.camera.capture('./%d.jpg', self.picnum)
				# self.picnum += 1
			'''
			tmp = input("set State on, off")
			if tmp == 'on':
				self.jsondata.setState('on')
			else:
				self.jsondata.setState('off')
			'''
			sleep(1.0)
		return

	def run(self):
		# codes for obtain sensor's info
		self.checkmachine()
		return
