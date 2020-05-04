package com.cidp.monitorsystem.util;

import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.snmp4j.PDU;

import java.lang.invoke.LambdaConversionException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 获取某一设备的接口流量 单位 Kbits/s
 * @author: Zdde丶
 * @create: 2020/4/8 14:10
 **/
public class GetInterFlow {
    public static List<String> getFlow(String ip,Integer time) throws Exception {
        SNMPSessionUtil flow = new SNMPSessionUtil(ip, "161", "public", "2");
        String[] oids1 = {".1.3.6.1.2.1.2.2.1.10"};
        List<String> inter = new ArrayList<>();
        ArrayList<String> fl = flow.snmpWalk2(oids1);
        DecimalFormat df = new DecimalFormat("#.##");
        List<String> list = new ArrayList<>();
        for (String s : fl) {
            inter.add(s.substring(0, s.lastIndexOf("=")).replace("=", "").trim());
        }
        for (String s : inter) {
            ArrayList<String> flowSnmpGet1 = flow.getSnmpGet(PDU.GET, s);
            Thread.sleep(time);
            ArrayList<String> flowSnmpGet2 = flow.getSnmpGet(PDU.GET, s);
            double v = (Double.parseDouble(flowSnmpGet2.get(0)) - Double.parseDouble(flowSnmpGet1.get(0))) * 8 / (3 * 1024);
            list.add(df.format(v));
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        GetInterFlow.getFlow("172.17.137.60",3000);
    }
}
