package com.rasec.server.core;

import com.rasec.server.config.DeviceConfig;
import com.rasec.server.model.Device;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;

public class ReportResource extends CoapResource {


    /*
     * Start Report Resource
     *
     * 1. Apply "report"resource through Constructor
     * 2. PUT Handler
     *
     */

    // 1. Apply "report"resource through Constructor
    public ReportResource(String name) {
        super(name);
    }

    public static byte[] decodeImage(String imgstr) {
        Decoder decoder = Base64.getDecoder();
        return decoder.decode(imgstr.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        /*
         * 2. PUT Handler
         *  2-1. Search DeviceID through Client's URL.
         *  2-2. Search DeviceID value from Data Structure
         * 	2-3. JSON Parsing requested value "State" from Device(Client)
         *  2-4. Make a Response value with JSONObject
         *  2-5. Response Values to Client
         *
         */

        try {

            //2-1. Search DeviceID through Client's URL.
            //* Fill () in here

            String deviceID = getName();
            //2-2. Search DeviceID value from Data Structure
            Device device = DeviceConfig.device;
            // DeviceInfo device = Global.device_list.get(deviceID);
            //2-3. JSON Parsing requested value "State" from Device(Client)
            //* Fill () in here
            JSONObject parse = new JSONObject(exchange.getRequestText().toString());
            String state = "";
            String imgstr = "";
            byte[] imgbyte = {};
            boolean getimg = false;

            if (parse.has("State"))
                state = parse.getString("State");

            if (parse.has("Image")) {
                imgstr = parse.getString("Image");
                imgbyte = decodeImage(imgstr);
                getimg = true;
            }

            //2-4. Make a Response value with JSONObject

            //* Fill () in here
            JSONObject json = new JSONObject();
            String payload;
            if (device == null) {
                json.put("Response", "failure");
            } else {
                if (!"".equals(state))
                    device.setDetectState(state);

                if (getimg) {
                    try {
                        FileOutputStream imgOutput = new FileOutputStream("/received.jpg");
                        imgOutput.write(imgbyte);
                        imgOutput.close();
                    } catch (IOException e) {
                        System.err.println("Exception while reading image!" + e);
                    }

                }
                json.put("Response", "success");
            }
            payload = json.toString();

            //2-5. Response Values to Client
            // Fill () in here
            exchange.respond(ResponseCode.CREATED, payload, MediaTypeRegistry.APPLICATION_JSON);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
