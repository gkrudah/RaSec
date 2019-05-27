import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)
buzzer_pin = 18
switch_pin = 19
scale = [261, 294, 329, 349, 392, 440, 493, 523]

GPIO.setup(buzzer_pin, GPIO.OUT)
GPIO.setup(switch_pin, GPIO.IN, GPIO.PUD_DOWN)
list = [4, 4, 5, 5, 4, 4, 2, 4, 4, 2, 2, 1]

try:
	p = GPIO.PWM(buzzer_pin, 100)
	switchIO = GPIO.input(switch_pin)
        p.start(100)
        p.ChangeDutyCycle(90)
	for i in range(10):
		if switchIO!=0:
		    p.ChangeFrequency(300)
                    print('ON')
                else:
                    p.ChangeFrequency(500)
                    print('OFF')
                time.sleep(1)

		'''
		for i in range(12):
			print(i + 1)
			p.ChangeFrequency(scale[list[i]])
			if i == 6:
				time.sleep(1)
			else:
				time.sleep(0.5)
		'''
	p.stop()
finally:
	GPIO.cleanup()
