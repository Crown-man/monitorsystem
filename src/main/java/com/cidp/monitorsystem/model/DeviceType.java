package com.cidp.monitorsystem.model;

import lombok.Data;

@Data
public class DeviceType {
    private Integer id;
    private String deviceIp;
    private String ip;
    private String typeName;
    private String name;
    private String icon;
}
