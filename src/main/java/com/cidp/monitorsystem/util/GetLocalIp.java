package com.cidp.monitorsystem.util;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/214:17
 **/
public class GetLocalIp {

   public static String getLocalIp() throws UnknownHostException {
       InetAddress inetAddress=InetAddress.getLocalHost();
       String ip=inetAddress.getHostAddress().toString();//获得本机Ip
       return ip;
   }

}
