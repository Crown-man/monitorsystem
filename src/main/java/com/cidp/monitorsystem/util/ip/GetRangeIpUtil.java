package com.cidp.monitorsystem.util.ip;

import com.cidp.monitorsystem.util.GetLocalIp;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/410:36
 **/
public class GetRangeIpUtil {
    public void GetDeviceInfo() {
        IPUtil.getPoolMax(IPUtil.getNetMask("255.255.255.252"));
    }

    /**
     * @param
     * @return java.util.List<java.lang.String>
     * @Description 获取本机所在网段内从全部子网
     * @Date 2020/4/4 12:23
     **/
    public static List<String> GetIpListWithMask() throws UnknownHostException {
        ArrayList<String> list = new ArrayList<>();
        String endIP = IPUtil.getEndIP(GetLocalIp.getLocalIp(), 25).getEndIP();
        String startIP = IPUtil.getEndIP(GetLocalIp.getLocalIp(), 25).getStartIP();
        String startInt = startIP.substring(startIP.lastIndexOf(".")).replace(".", "");
        String endInt = endIP.substring(endIP.lastIndexOf(".")).replace(".", "");
        String statrIpList = startIP.substring(0, startIP.lastIndexOf("."));
        for (int i = Integer.parseInt(startInt); i <= Integer.parseInt(endInt); i++) {
            String ip = statrIpList + "." + String.valueOf(i);
            list.add(ip);
        }
        return list;
    }

    /**
     * @Description 自定义掩码、IP
     * @Date 2020/4/4 12:27
     * @param destIp
     * @param netmask
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> GetIpListWithMask(String destIp, int netmask) {
        ArrayList<String> list = new ArrayList<>();
        String endIP = IPUtil.getEndIP(destIp, netmask).getEndIP();
        String startIP = IPUtil.getEndIP(destIp, netmask).getStartIP();
        String startInt = startIP.substring(startIP.lastIndexOf(".")).replace(".", "");
        String endInt = endIP.substring(endIP.lastIndexOf(".")).replace(".", "");
        String statrIpList = startIP.substring(0, startIP.lastIndexOf("."));
        for (int i = Integer.parseInt(startInt); i <= Integer.parseInt(endInt); i++) {
            String ip = statrIpList + "." + String.valueOf(i);
            list.add(ip);
        }
        return list;
    }

    /**
     * @param destIp
     * @return java.util.List<java.lang.String>
     * @Description 默认掩码24
     * @Date 2020/4/4 12:27
     **/
    public static List<String> GetIpListWithMask(String destIp) {
        ArrayList<String> list = new ArrayList<>();
        String endIP = IPUtil.getEndIP(destIp, 24).getEndIP();
        String startIP = IPUtil.getEndIP(destIp, 24).getStartIP();
        String startInt = startIP.substring(startIP.lastIndexOf(".")).replace(".", "");
        String endInt = endIP.substring(endIP.lastIndexOf(".")).replace(".", "");
        String statrIpList = startIP.substring(0, startIP.lastIndexOf("."));
        for (int i = Integer.parseInt(startInt); i <= Integer.parseInt(endInt); i++) {
            String ip = statrIpList + "." + String.valueOf(i);
            list.add(ip);
        }
        return list;
    }
}
