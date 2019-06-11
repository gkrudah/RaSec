package com.mir.GPIO;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class MotionDetect extends Thread{
	// private String state;

	//Create gpio controller for PIR Motion Sensor listening on the pin GPIO_12
	// final GpioController gpioPIRMotionSensor = GpioFactory.getInstance();
	// final GpioPinDigitalInput pirMotionsensor = gpioPIRMotionSensor.provisionDigitalInputPin(RaspiPin.GPIO_12, PinPullResistance.PULL_DOWN);
	GpioController gpioPIRMotionSensor;
	GpioPinDigitalInput pirMotionsensor;

	//Create gpio controller for Buzzer listening on the pin GPIO_24
	// final GpioController gpioBuzzer = GpioFactory.getInstance();
	// final GpioPinDigitalOutput buzzer = gpioBuzzer.provisionDigitalOutputPin(RaspiPin.GPIO_24,"Buzzer",PinState.HIGH);
	GpioController gpioBuzzer;
	GpioPinDigitalOutput buzzer;

	public MotionDetect() {
		this.gpioPIRMotionSensor = GpioFactory.getInstance();
		this.pirMotionsensor = gpioPIRMotionSensor.provisionDigitalInputPin(RaspiPin.GPIO_12, PinPullResistance.PULL_DOWN);

		this.gpioBuzzer = GpioFactory.getInstance();
		this.buzzer = gpioBuzzer.provisionDigitalOutputPin(RaspiPin.GPIO_24,"Buzzer",PinState.HIGH);
	}

	@Override
	public void run() {
		super.run();

		//This is required to enable Non Privileged Access to avoid applying sudo to run Pi4j programs
		// GpioUtil.enableNonPrivilegedAccess();
		System.out.println("PIR Motion Sensor is ready and looking for any movement..");

		buzzer.high();
		System.out.println("wtffffffffffffffffffffffffffffffffff");

		//Create and register gpio pin listener on PIRMotion Sensor GPIO Input instance
		pirMotionsensor.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				//if the event state is High then print "Intruder Detected" and turn the LED ON by invoking the high() method
				System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
				if(event.getState().isHigh()){
					System.out.println("Intruder Detected!, Buzzer is ON");
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
					System.out.println("All is quiet, Buzzer is OFF");
					// led.low();
					buzzer.low();
				}
			}
		});

		gpioPIRMotionSensor.shutdown();
		gpioPIRMotionSensor.unprovisionPin(pirMotionsensor);
		gpioBuzzer.shutdown();
		gpioBuzzer.unprovisionPin(buzzer);
	}
}
