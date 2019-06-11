package com.mir.Service;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.CoapClient;
import org.json.JSONObject;

import Global.Global;

public class Control extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Timer timer = new Timer();
		timer.schedule(new UpdateTask(), 0, Global.pollinginterval);
	}
	
	CoapClient client=new CoapClient();

	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			// 2-2. CoAP Server Control URI Setting
			// Fill () in  here
			String uri =  "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "control" + "/" + Global.SYSTEMID;
			System.out.println("ControlURI"+uri);
			client.setURI(uri);
			
			
			try {
				
				// Fill () in  here
				String response = client.get().getResponseText();
				System.out.println("Control Response:" + response);
				JSONObject js = new JSONObject(response);
				// Fill () in  here
				String control =  js.get("Control").toString();
				Global.setState(control);
				

				if (control.matches("ON")) {
					new com.mir.GPIO.StateMachine("ON").start();

				} else if (control.matches("OFF")) {
					new com.mir.GPIO.StateMachine("OFF").start();
				}

			} catch (Exception e) {
				e.getStackTrace();
			}

		}
	}
}
