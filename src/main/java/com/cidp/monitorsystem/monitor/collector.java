package com.cidp.monitorsystem.monitor;

import com.cidp.monitorsystem.util.GetLocalIp;

import java.net.UnknownHostException;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/218:52
 **/

public class collector {
    public static void main(String[] args) throws UnknownHostException {
        SnmpTrapHandler handler = new SnmpTrapHandler();
        handler.SnmpTrapHandler(GetLocalIp.getLocalIp(),"162");

        handler.start();
    }
}
