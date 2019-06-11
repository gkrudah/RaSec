package com.rasec.server.core;

import com.rasec.server.config.DeviceConfig;
import com.rasec.server.model.Device;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;


public class ControlResource extends CoapResource {


    /*
     * Start Connect Resource
     *
     * 1. Apply "control" resource through Constructor
     * 2. Get Handler
     *
     */


    //1. Apply "control"resource through Constructor
    public ControlResource(String name) {
        super(name);
    }



    @Override
    public void handleGET(CoapExchange exchange) {
        /*
         * 2. Get Handler
         * 	2-1. Search DeviceID through Client's URL.
         *  2-2. Search DeviceID value from Data Structure
         *  2-3.  Make a Response value with JSONObject
         *  	2-3.1. If there is not DeviceID, Make a "NOT Device" JSON Object Value.
         *  	2-3.2. Else if there are DeviceID and Event, Make a Event of Devices JSON Object Value.
         *  	2-3.3  Else if there are DeviceID but Not Event, Make a "none" JSON Object Value.
         *  2-4. Response Values to Client
         */


        try {
            //2-1. Search DeviceID through Client's URL.
            //* Fill () in here

            String id = getName();

            //2-2. Search DeviceID value from Data Structure
            Device device = DeviceConfig.device;
            // DeviceInfo device = Global.device_list.get(id);


            //Fill () in here
            /*2-3.  Make a Response value with JSONObject & Get Event
             * 2-3.1. If there is not DeviceID, Make a "NOT Device" JSON Object Value.
             * 2-3.2. Else if there are DeviceID and getISEvent, Make a Event of Devices JSON Object Value.
             * 2-3.3  Else there are DeviceID but Not Event, Make a "none" JSON Object Value.
             */
            JSONObject json = new JSONObject();
            if (device == null) {
                json.put("Control", "Not Device");
            } else {
                // json.put("State", device.getState());
                json.put("CamState", device.getCamState());
                json.put("BuzzerState", device.getBuzzerState());
            }


            String payload = json.toString();

            //2-4. Response Values to Client
            // Fill () in here
            exchange.respond(ResponseCode.CONTENT, payload, MediaTypeRegistry.APPLICATION_JSON);

        } catch (Exception e) {
            exchange.respond(ResponseCode.BAD_REQUEST, "Wrong Access");
        }

		/*
		if (device.getIsEvent() == true) {
					json.put("State", device.getState());
					json.put("CamState", device.getCamera());
					json.put("BuzzerState", device.getBuzzer());
				} else {
					json.put("State", device.getState());
		 */

    }
}
