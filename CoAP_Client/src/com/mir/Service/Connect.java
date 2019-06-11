package com.mir.Service;

import java.util.Scanner;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONException;
import org.json.JSONObject;

import Global.Global;

public class Connect {
	/*
	 * Connect 
	 * 
	 */

	//fill () in here
	CoapClient client=new CoapClient();

	public void session() throws JSONException {
		

		// Fill () in  here
		String uri =  "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "connect";
		System.out.println("ConnectURI "+uri);
		client.setURI(uri);

		JSONObject json = new JSONObject();
		json.put("DeviceID", Global.SYSTEMID);
		json.put("State", "off");
		json.put("Mode", Global.Mode);

		String payload = json.toString();
		System.out.println("Connect Request:"+payload);

		// Fill () in  here
		CoapResponse resp1 = client.post(payload, MediaTypeRegistry.APPLICATION_JSON);
		System.out.println("Connect Response:"+resp1.getResponseText());
        
		
		//2. Run CoAP Client Report URI 
		com.mir.Service.Report report = new com.mir.Service.Report();
		report.start();

		if (Global.Mode.equals("pull")) {
			com.mir.Service.Control control = new com.mir.Service.Control();
			control.start();
		}
		else if (Global.Mode.equals("push")){
			com.mir.Service.Observe observe = new com.mir.Service.Observe();
			observe.start();
		}
	}
}
