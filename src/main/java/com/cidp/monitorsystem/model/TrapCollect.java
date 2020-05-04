package com.cidp.monitorsystem.model;

import lombok.Data;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/218:55
 **/
@Data
public class TrapCollect {
    private Integer id;
    private String ip;
    private String version;
    private String community;
    private String time;
    private String value;
    private String zhpoint;
    private Integer status;
}
