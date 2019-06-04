import threading
from time import sleep
import RPi.GPIO as GPIO
# from picamera import PiCamera

pirPin = 12
buzzerPin = 24


class machine(threading.Thread):
	def __init__(self, jsondata):
		threading.Thread.__init__(self)
		self.jsondata = jsondata

		GPIO.setmode(GPIO.BCM)
		GPIO.setup(pirPin, GPIO.IN)
		GPIO.setup(buzzerPin, GPIO.OUT)

		# buzzer init
		GPIO.setup(buzzerPin, False)
		# self.buzzer = GPIO.PWM(buzzerPin, 100)
		# self.buzzer.start(0)
		# self.buzzer.ChangeDutyCycle(90)

		# self.lock = lock
		return

	def ringBuzzer(self):
		GPIO.output(buzzerPin, True)
		sleep(0.5)
		GPIO.output(buzzerPin, False)
		return

	def checkmachine(self, interval=1.0):

		if GPIO.input(pirPin):
			self.jsondata.setState('on')
			self.ringBuzzer()
		else:
			self.jsondata.setState('off')

		threading.Timer(interval, self.checkmachine).start()
		return

	def run(self):
		# codes for obtain sensor's info
		self.checkmachine()
		return
