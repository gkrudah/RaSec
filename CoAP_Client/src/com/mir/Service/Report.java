package com.mir.Service;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONException;
import org.json.JSONObject;

import Global.Global;

public class Report extends Thread {

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
			// 2-2. CoAP Server Report URI Setting
			// 2-2. CoAP Server Report URI Setting

			// Fill () in  here
			String uri = "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "report"+"/" + Global.SYSTEMID;
			client.setURI(uri);

			JSONObject json = new JSONObject();
			try {
				String state=Global.getState();
				
				if(state.equals("")) {
					state="OFF";
				}
				
				json.put("State", state);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String payload = json.toString();
			System.out.println("Report Request:"+payload);

			// Fill () in  here
			CoapResponse resp = client.put(payload, MediaTypeRegistry.APPLICATION_JSON);
			System.out.println("Report Response:"+resp.getResponseText());

		}

	}
}
