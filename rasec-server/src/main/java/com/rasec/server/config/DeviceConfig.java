package com.rasec.server.config;

import com.rasec.server.core.ControlResource;
import com.rasec.server.core.ObserveResource;
import com.rasec.server.core.ReportResource;
import com.rasec.server.model.Device;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapResource;

@Slf4j
public class DeviceConfig {
    public static Device device = Device.builder()
            .deviceId("device1")
            .buzzerState(false)
            .camState(false)
            .build();
    public static ReportResource report_resource;
    public static ControlResource control_resource;
    public static ObserveResource observe_resource;

    public static CoapResource getReport_resource() {
        return report_resource;
    }

    public static  void setReport_resource(ReportResource report_resource) {
        DeviceConfig.report_resource = report_resource;
    }

    public static CoapResource getControl_resource() {
        return control_resource;
    }

    public static void setControl_resource(ControlResource control_resource) {
        DeviceConfig.control_resource = control_resource;
    }

    public static CoapResource getObserve_resource() {
        return observe_resource;
    }

    public static void setObserve_resource(ObserveResource observe_resource) {
        DeviceConfig.observe_resource = observe_resource;
    }
    public static void observeResourceChanged(){
        log.info("observe_resource changed");
        DeviceConfig.observe_resource.changed();
        log.info("after obs changed");
    }
}
