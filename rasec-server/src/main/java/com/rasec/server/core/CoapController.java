package com.rasec.server.core;

import com.rasec.server.config.DeviceConfig;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoapController {
    @Autowired
    public CoapController(DeviceManager manager) {
        CoapServer coapServer = new CoapServer();
        coapServer.add(manager);
        CoapResource control_res = new CoapResource("control");
        DeviceConfig.setControl_resource(control_res);
        coapServer.add(control_res);

        CoapResource report_res = new CoapResource("report");
        DeviceConfig.setReport_resource(report_res);
        coapServer.add(report_res);

        CoapResource observer_res = new CoapResource("obs");
        DeviceConfig.setObserve_resource(observer_res);
        coapServer.add(observer_res);

        coapServer.start();
    }
}
