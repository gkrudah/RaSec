package com.rasec.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photos {
    private String name;
   // private String mimeType;
    private byte[] imageByte;

}
