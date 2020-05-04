package com.cidp.monitorsystem.topology;

import lombok.Data;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/1020:35
 **/
@Data
public class IpAddrTable {
    private String ip;
    private String ipAdEntAddr;
    private String ipAdEntIfIndex;
    private String ipAdEntNetMask;
}
