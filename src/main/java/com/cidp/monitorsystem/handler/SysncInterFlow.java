package com.cidp.monitorsystem.handler;

import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.snmp4j.PDU;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class SysncInterFlow {
    /**
     * @param ip
     * @param time
     * @return 接口流量 kbtis/s
     * @throws Exception
     */
    @Async(value = "taskExecutor")
    public CompletableFuture<List<List<String>>> getInterFlow (String ip,Integer time) throws Exception {
        SNMPSessionUtil flow = new SNMPSessionUtil(ip, "161", "public", "2");
        String[] oids1 = {".1.3.6.1.2.1.2.2.1.10"};//输入流量
        String[] oids2 = {".1.3.6.1.2.1.2.2.1.16"};//输出流量
        List<String> inter = new ArrayList<>();
        List<String> inter2 = new ArrayList<>();
        ArrayList<String> fl = flow.snmpWalk2(oids1);
        ArrayList<String> fl2 = flow.snmpWalk2(oids2);
        DecimalFormat df = new DecimalFormat("#.##");
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<List<String>> lists = new ArrayList<>();
        for (int i=0;i<fl.size();i++) {
            inter.add(fl.get(i).substring(0,fl.get(i).lastIndexOf("=")).replace("=", "").trim());
            inter2.add(fl2.get(i).substring(0,fl2.get(i).lastIndexOf("=")).replace("=", "").trim());
        }
        for (int i=0;i<inter.size();i++) {
            ArrayList<String> flowSnmpGet1 = flow.getSnmpGet(PDU.GET,inter.get(i));
            ArrayList<String> flowSnmpGet11 = flow.getSnmpGet(PDU.GET,inter2.get(i));
            Thread.sleep(time);
            ArrayList<String> flowSnmpGet2 = flow.getSnmpGet(PDU.GET, inter.get(i));
            ArrayList<String> flowSnmpGet22 = flow.getSnmpGet(PDU.GET, inter2.get(i));
            double v = (Math.abs(Double.parseDouble(flowSnmpGet2.get(0)) - Double.parseDouble(flowSnmpGet1.get(0)))) * 8 / (3 * 1024);
            double v2 = (Math.abs(Double.parseDouble(flowSnmpGet22.get(0)) - Double.parseDouble(flowSnmpGet11.get(0)))) * 8 / (3 * 1024);
            list1.add(df.format(v));
            list2.add(df.format(v2));
        }
        lists.add(list1);
        lists.add(list2);
        return CompletableFuture.completedFuture(lists);
    }

    /**
     * @param ip
     * @param time
     * @return 接收丢包率 发送丢包率
     * @throws Exception
     */
    @Async(value = "taskExecutor")
    public CompletableFuture<List<List<String>>> getUpLoss (String ip,Integer time) throws Exception {
        SNMPSessionUtil flow = new SNMPSessionUtil(ip, "161", "public", "2");
        String[] oids1 = {".1.3.6.1.2.1.2.2.1.13"};
        String[] oids2 = {".1.3.6.1.2.1.2.2.1.19"};
        List<String> inter = new ArrayList<>();
        List<String> inter2 = new ArrayList<>();
        ArrayList<String> fl = flow.snmpWalk2(oids1);
        ArrayList<String> fl2 = flow.snmpWalk2(oids2);
        DecimalFormat df = new DecimalFormat("#.##");
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<List<String>> lists = new ArrayList<>();
        for (int i=0;i<fl.size();i++) {
            inter.add(fl.get(i).substring(0, fl.get(i).lastIndexOf("=")).replace("=", "").trim());
            inter2.add(fl2.get(i).substring(0, fl2.get(i).lastIndexOf("=")).replace("=", "").trim());
        }
        for (int i=0;i<fl.size();i++) {
            ArrayList<String> flowSnmpGet1 = flow.getSnmpGet(PDU.GET, inter.get(i));
            ArrayList<String> flowSnmpGet11 = flow.getSnmpGet(PDU.GET, inter2.get(i));
            Thread.sleep(time);
            ArrayList<String> flowSnmpGet2 = flow.getSnmpGet(PDU.GET, inter.get(i));
            ArrayList<String> flowSnmpGet22 = flow.getSnmpGet(PDU.GET, inter2.get(i));
            double v = (Math.abs(Double.parseDouble(flowSnmpGet2.get(0)) - Double.parseDouble(flowSnmpGet1.get(0))))/3 ;
            double v2 = (Math.abs(Double.parseDouble(flowSnmpGet22.get(0)) - Double.parseDouble(flowSnmpGet11.get(0))))/3 ;
            list1.add(df.format(v));
            list2.add(df.format(v2));
        }
        lists.add(list1);
        lists.add(list2);
        return CompletableFuture.completedFuture(lists);
    }

    /**
     * @param ip
     * @param time
     * @return 接收率 发送率
     * @throws Exception
     */
    @Async(value = "taskExecutor")
    public CompletableFuture<List<List<String>>> getinRate (String ip,Integer time) throws Exception {
        SNMPSessionUtil flow = new SNMPSessionUtil(ip, "161", "public", "2");
        String[] oids1 = {".1.3.6.1.2.1.2.2.1.10"};//输入流量
        String[] oids2 = {".1.3.6.1.2.1.2.2.1.16"};//输出流量
        String[] oids3 = {".1.3.6.1.2.1.2.2.1.5"};//输出流量
        List<String> inter = new ArrayList<>();
        List<String> inter2 = new ArrayList<>();
        List<String> inter3 = new ArrayList<>();
        ArrayList<String> fl = flow.snmpWalk2(oids1);
        ArrayList<String> fl2 = flow.snmpWalk2(oids2);
        ArrayList<String> ifspeed = flow.snmpWalk2(oids3);
        DecimalFormat df = new DecimalFormat("#.##");
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<List<String>> lists = new ArrayList<>();
        for (int i=0;i<fl.size();i++) {
            inter.add(fl.get(i).substring(0, fl.get(i).lastIndexOf("=")).replace("=", "").trim());
            inter2.add(fl2.get(i).substring(0, fl2.get(i).lastIndexOf("=")).replace("=", "").trim());
            inter3.add(ifspeed.get(i).substring(0,ifspeed.get(i).lastIndexOf("=")).replace("=", "").trim());
        }
        for (int i=0;i<fl.size();i++) {
            ArrayList<String> speed = flow.getSnmpGet(PDU.GET, inter3.get(i));
            ArrayList<String> flowSnmpGet1 = flow.getSnmpGet(PDU.GET, inter.get(i));
            ArrayList<String> flowSnmpGet11 = flow.getSnmpGet(PDU.GET, inter2.get(i));
            Thread.sleep(time);
            ArrayList<String> flowSnmpGet2 = flow.getSnmpGet(PDU.GET, inter.get(i));
            ArrayList<String> flowSnmpGet22 = flow.getSnmpGet(PDU.GET, inter2.get(i));
            double v = ((Math.abs(Double.parseDouble(flowSnmpGet2.get(0)) - Double.parseDouble(flowSnmpGet1.get(0))))*8)/(3*Double.parseDouble(speed.get(0)))*100;
            double v2 = ((Math.abs(Double.parseDouble(flowSnmpGet22.get(0)) - Double.parseDouble(flowSnmpGet11.get(0))))*8)/(3*Double.parseDouble(speed.get(0)))*100;
            list1.add(df.format(v));
            list2.add(df.format(v2));
        }
        lists.add(list1);
        lists.add(list2);
        return CompletableFuture.completedFuture(lists);
    }

    /**
     * @param ip
     * @param time
     * @return 接收错误率 发送错误率
     * @throws Exception
     */
    @Async(value = "taskExecutor")
    public CompletableFuture<List<List<String>>> getErrorRate (String ip,Integer time) throws Exception {
        SNMPSessionUtil flow = new SNMPSessionUtil(ip, "161", "public", "2");
        String[] oids1 = {".1.3.6.1.2.1.2.2.1.14"};//输入错误包
        String[] oids2 = {".1.3.6.1.2.1.2.2.1.20"};//输出错误包
        List<String> inter = new ArrayList<>();
        List<String> inter2 = new ArrayList<>();
        ArrayList<String> fl = flow.snmpWalk2(oids1);
        ArrayList<String> fl2 = flow.snmpWalk2(oids2);
        DecimalFormat df = new DecimalFormat("#.##");
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<List<String>> lists = new ArrayList<>();
        for (int i=0;i<fl.size();i++) {
            inter.add(fl.get(i).substring(0,fl.get(i).lastIndexOf("=")).replace("=", "").trim());
            inter2.add(fl2.get(i).substring(0,fl2.get(i).lastIndexOf("=")).replace("=", "").trim());
        }
        for (int i=0;i<inter.size();i++) {
            ArrayList<String> flowSnmpGet1 = flow.getSnmpGet(PDU.GET,inter.get(i));
            ArrayList<String> flowSnmpGet11 = flow.getSnmpGet(PDU.GET,inter2.get(i));
            Thread.sleep(time);
            ArrayList<String> flowSnmpGet2 = flow.getSnmpGet(PDU.GET, inter.get(i));
            ArrayList<String> flowSnmpGet22 = flow.getSnmpGet(PDU.GET, inter2.get(i));
            double v = (Math.abs(Double.parseDouble(flowSnmpGet2.get(0)) - Double.parseDouble(flowSnmpGet1.get(0)))) / 3 ;
            double v2 = (Math.abs(Double.parseDouble(flowSnmpGet22.get(0)) - Double.parseDouble(flowSnmpGet11.get(0))))  / 3;
            list1.add(df.format(v));
            list2.add(df.format(v2));
        }
        lists.add(list1);
        lists.add(list2);
        return CompletableFuture.completedFuture(lists);
    }
}
