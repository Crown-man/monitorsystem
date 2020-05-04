package com.cidp.monitorsystem.model;

import lombok.Data;

@Data
public class AtTable {
    private Integer id;
    private String ip;
    private String atIfIndex;
    private String atPhysAddress;
    private String atNetAddress;
}
