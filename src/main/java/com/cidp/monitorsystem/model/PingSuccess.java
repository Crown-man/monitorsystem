package com.cidp.monitorsystem.model;

import lombok.Data;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/416:17
 **/
@Data
public class PingSuccess {
    private String ip;
    private String segment;
    private Integer netmask;
    private Integer isSnmp;
    private String time;
}
