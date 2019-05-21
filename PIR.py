import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

pir_sensor = 4 # GPIO pin 4
led = 25 #GPIO pin 25

GPIO.setup(pir_sensor, GPIO.IN, GPIO.PUD_DOWN)

current_state = 0
GPIO.setup(led,GPIO.OUT)

while True:
    try:
        time.sleep(1)
        current_state = GPIO.input(pir_sensor)
        if current_state == 1:
            print("Detected", current_state)
            GPIO.output(led,True)
            time.sleep(2)
            GPIO.output(led,False)
            time.sleep(4)
        else:
            print("Not Detected",current_state)
    except KeyboardInterrupt:
        GPIO.cleanup()
    '''
    i = GPIO.input(7)
    if i == 0:
        print("Not Detected",i)
        GPIO.output(22,0)
        time.sleep(1)
    elif i == 1:
        print("Detected",i)
        GPIO.output(22,1)
        time.sleep(1)'''
