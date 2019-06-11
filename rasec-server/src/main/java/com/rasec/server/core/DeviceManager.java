package com.rasec.server.core;

import com.rasec.server.config.DeviceConfig;
import com.rasec.server.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeviceManager extends CoapResource {
    private ApiController apiController;
    @Autowired
    public DeviceManager() {
        super("devices");
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        try {
            //2-1. JSON Parsing requested value from Device(Client)

            String id, state, mode;
            boolean camera, buzzer;
            JSONObject parsedObject = new JSONObject(exchange.getRequestText().toString());
            id = parsedObject.getString("DeviceID");
            state = parsedObject.getString("State");
            mode = parsedObject.getString("Mode");
            camera = parsedObject.getBoolean("CamState");
            buzzer = parsedObject.getBoolean("BuzzerState");

            System.out.println("CONNECT DEVICE");
            System.out.println("=========");
            System.out.println("DEVICE ID:" + id);
            System.out.println("DEVICE State:" + state);
            System.out.println("DEVICE Mode:" + mode);
            System.out.println("DEVICE Camera:" + camera);
            System.out.println("DEVICE Buzzer:" + buzzer);
            System.out.println("=========");

            //2-2. Make a Response value with JSONObject
            JSONObject json = new JSONObject();
            json.put("Response", "success");
            String payload = json.toString();

            //2-3. Response Values to Client
            // * () Fill in here
            exchange.respond(CoAP.ResponseCode.CONTENT, payload, MediaTypeRegistry.APPLICATION_JSON);

            //2-4. Requested value Save into Data Structure
            DeviceConfig.device = new Device().builder()
                    .deviceId(id)
                    .detectState(state)
                    .build();
            // DeviceInfo dev_info = new DeviceInfo(id, state, mode);
            // Global.device_list.put(id, dev_info);

			/*
			 * 	3.Add Resources
			 		3-1. Add DeviceID Resource to Report Resource
			 		3-2. Add DeviceID Resource to Control Resource
			 */
            //3-1. Add DeviceID Resource to Report Resource : report/Device1
            // * Fill in here
            DeviceConfig.getReport_resource().add(new ReportResource(id));

            //3-2. Add DeviceID Resource to Control Resource : control/Device1
            // * Fill in here
            DeviceConfig.getControl_resource().add(new ControlResource(id));

            if (mode.equals("push")) {
                ObserveResource obs_resource = new ObserveResource(id);
                DeviceConfig.getObserve_resource().add(obs_resource);
                DeviceConfig.device.setResource(obs_resource);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST, "Wrong Access");
        }

    }
}
