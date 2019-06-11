package com.rasec.server.core;

import org.eclipse.californium.core.CoapServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoapController {
    @Autowired
    public CoapController(DeviceManager manager) {
        CoapServer coapServer = new CoapServer();
        coapServer.add(manager);
        coapServer.start();
    }
}
