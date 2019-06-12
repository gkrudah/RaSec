package com.rasec.server.config;

import com.rasec.server.model.Device;
import lombok.Data;
import org.eclipse.californium.core.CoapResource;

@Data
public class DeviceConfig {
    public static Device device = Device.builder()
            .deviceId("thscowns")
            .buzzerState(true)
            .camState(false)
            .build();
    public static CoapResource report_resource;
    public static CoapResource control_resource;
    public static CoapResource observe_resource;
    public static CoapResource getReport_resource() {
        return report_resource;
    }

    public static  void setReport_resource(CoapResource report_resource) {
        DeviceConfig.report_resource = report_resource;
    }

    public static CoapResource getControl_resource() {
        return control_resource;
    }

    public static void setControl_resource(CoapResource control_resource) {
        DeviceConfig.control_resource = control_resource;
    }

    public static CoapResource getObserve_resource() {
        return observe_resource;
    }

    public static void setObserve_resource(CoapResource observe_resource) {
        DeviceConfig.observe_resource = observe_resource;
    }
}
