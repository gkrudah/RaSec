import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)

GPIO.setup(7,GPIO.IN)

while 1:
    if GPIO.input(7):
        print("Detected")
    else:
        print("Not Detected")

    time.sleep(1)

GPIO.cleanup()
