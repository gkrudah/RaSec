package com.mir.GPIO;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.wiringpi.GpioUtil;
import com.pi4j.wiringpi.SoftPwm;
import Global.Global;

public class StateMachine extends Thread {
	private String control;
	// private String buzzer;
	final int PIN_NUMBER = 1;

	//Create gpio controller for PIR Motion Sensor listening on the pin GPIO_12
	// final GpioController gpioPIRMotionSensor = GpioFactory.getInstance();
	// final GpioPinDigitalInput pirMotionsensor = gpioPIRMotionSensor.provisionDigitalInputPin(RaspiPin.GPIO_12, PinPullResistance.PULL_DOWN);

	//Create gpio controller for Buzzer listening on the pin GPIO_24
	// final GpioController gpioBuzzer = GpioFactory.getInstance();
	// final GpioPinDigitalOutput buzzer = gpioBuzzer.provisionDigitalOutputPin(RaspiPin.GPIO_24,"Buzzer",PinState.LOW);

	public StateMachine(String control) {
		this.control = control;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		SoftPwm.softPwmCreate(PIN_NUMBER, 0, 100);

		System.out.println("=========");
		System.out.println("LED CONTROL");
		System.out.println("DIMMING =" + control);
		System.out.println("=========");

		if (control.equals("ON")) {
			SoftPwm.softPwmWrite(PIN_NUMBER, 100);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("ON!");

			// motionDetect();
			new com.mir.GPIO.MotionDetect().start();
		}

		else {
			SoftPwm.softPwmWrite(PIN_NUMBER, 0);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("OFF!");
		}
		Global.setState(control);
	}
	/*
	public void motionDetect() {
		System.out.println("PIR Motion Sensor is ready and looking for any movement..");

		//This is required to enable Non Privileged Access to avoid applying sudo to run Pi4j programs
		GpioUtil.enableNonPrivilegedAccess();

		buzzer.low();

		//Create and register gpio pin listener on PIRMotion Sensor GPIO Input instance
		pirMotionsensor.addListener(new GpioPinListenerDigital() {

			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				//if the event state is High then print "Intruder Detected" and turn the LED ON by invoking the high() method
				if(event.getState().isHigh()){
					System.out.println("Intruder Detected!, LED is ON and Buzzer is ON");
					//led.high();
					buzzer.high();
					try {
						//Take two snaps from Camera Module
						//takeSnap();
					} catch (Exception e) {
						System.out.println("Exception happened while taking the Snap..");
						e.printStackTrace();
					}
				}
				//if the event state is Low then print "All is quiet.." and make the LED OFF by invoking the low() method
				if(event.getState().isLow()){
					System.out.println("All is quiet, LED is OFF and Buzzer is OFF");
					// led.low();
					buzzer.low();
				}
			}
		});

	}
	*/
}
