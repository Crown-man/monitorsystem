package com.cidp.monitorsystem.topology;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/10 20:29
 **/
@Data
public class IpRouteTable implements Serializable {
    private String ip;
    private String ipRouteDest;
    private String ipRouteIfIndex;
    private String ipRouteNextHop;
    private String ipRouteType;
    private String ipRouteMask;
}
