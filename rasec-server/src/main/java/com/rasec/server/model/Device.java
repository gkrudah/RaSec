package com.rasec.server.model;

import com.rasec.server.config.DeviceConfig;
import com.rasec.server.core.ObserveResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    private String deviceId;
    private String mode;
    private Boolean camState;
    private Boolean buzzerState;
    private String detectState;
    ObserveResource resource;

    public void observeResourceChanged(){
        log.info("observe_resource changed");
        this.resource.changed();
        log.info("after obs changed");
    }

}
