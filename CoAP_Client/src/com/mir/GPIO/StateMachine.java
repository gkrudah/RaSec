package com.mir.GPIO;

import com.pi4j.wiringpi.SoftPwm;
import Global.Global;

import org.python.util.PythonInterpreter;

public class StateMachine extends Thread {
	private String control;
	// private String buzzer;
	final int PIN_NUMBER = 1;
	private static PythonInterpreter interpreter = new PythonInterpreter();

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

		//SoftPwm.softPwmCreate(PIN_NUMBER, 0, 100);
		/*
		System.out.println("=========");
		System.out.println("LED CONTROL");
		System.out.println("DIMMING =" + control);
		System.out.println("=========");
		*/
		if (control.equals("ON")) {
			//SoftPwm.softPwmWrite(PIN_NUMBER, 100);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("ON!");

			// motionDetect();
			// new com.mir.GPIO.MotionDetect().start();
			rasec();
		}

		else {
			//SoftPwm.softPwmWrite(PIN_NUMBER, 0);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("OFF!");
		}
		Global.setState(control);
	}


	public void rasec() {
		//interpreter.execfile("buzzer.py");
		interpreter.exec("print(\"wowwwwwwwwwwwwwwwwwwwwwwwwww\")");
	}

}
