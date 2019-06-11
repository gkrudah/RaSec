package com.rasec.server.model;

import com.rasec.server.core.ObserveResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    private String deviceId;
    private Boolean camState;
    private Boolean buzzerState;
    private String detectState;
    ObserveResource resource;


}
