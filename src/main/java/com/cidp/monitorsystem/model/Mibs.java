package com.cidp.monitorsystem.model;

import lombok.Data;

@Data
public class Mibs {
    private String oid;
    private String val;
    private Integer pid;
    private String zhval;
}
