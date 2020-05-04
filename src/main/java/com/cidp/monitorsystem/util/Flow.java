package com.cidp.monitorsystem.util;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * @description: 统计流量的类，取时间与流量
 * @author: Zdde丶
 * @create: 2020/4/812:50
 **/
public class Flow {
    final String OCTER_STRING = "public";// 共同体
    final String TIME_OID = ".1.3.6.1.2.1.1.3.0";// 时间OID，所有设备一样
    String IpAddress;// 设备IP地址
    ArrayList<String> FlowOidGroup;// 流量OID，可能有多个
    private String NowTime;// 端口流量的采集时间
    private double FlowValue;// 端口流量的值
    public boolean isSuccess = true;

    // 构造器：IP地址和流量OID组(因为可能需要多个端口的流量加在一起)
    public Flow(String IpAddress, ArrayList<String> FlowOidGroup) {
        this.IpAddress = IpAddress;
        this.FlowOidGroup = FlowOidGroup;
        this.calc();
    }

    // 取当前时间
    public String getNowTime() {
        return NowTime;
    }

    // 取端口流量
    public double getFlowValue() {
        return FlowValue;
    }

    // 计算端口流量
    @SuppressWarnings("unchecked")
    private void calc() {
        try {
            Snmp snmp;
            TransportMapping transport;
            Address targetAddress = GenericAddress.parse("udp:" + IpAddress
                    + "/161");
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();// 监听
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(OCTER_STRING));// 设置共同体名
            target.setAddress(targetAddress);// 设置目标Agent地址
            target.setRetries(2);// 重试次数
            target.setTimeout(3000);// 超时设置
            target.setVersion(1);// 版本
            PDU request = new PDU();
            request.setType(PDU.GET);// 操作类型GET
            request.add(new VariableBinding(new OID(TIME_OID)));// OID_sysUpTime
            for (String FlowOid : FlowOidGroup) {
                request.add(new VariableBinding(new OID(FlowOid)));
            }
// 取两次数据，间隔10秒，算差值
            long[] time = new long[2];
            long[][] flow = new long[2][FlowOidGroup.size()];
            for (int count = 0; count < 2; count++) {
                ResponseEvent respEvt = snmp.send(request, target);// 发送请求
                if (respEvt != null && respEvt.getResponse() != null) {
// 从目的设备取值，得到Vector
                    Vector<VariableBinding> revBindings = (Vector<VariableBinding>) respEvt.getResponse().getVariableBindings();
                    String TimeTicks = revBindings.elementAt(0)
                            .getVariable().toString().trim();
                    String[] TimeString = TimeTicks.split(" ");// 得到时间字符串数组
// 取时间 186 days, 21:26:15.24，也有可能没有day，就是不到一天
                    if (TimeTicks.contains("day")) {
                        time[count] = Long.parseLong(TimeString[0])
                                * 24
                                * 3600
                                + Long.parseLong(TimeString[2].split(":")[0])
                                * 3600
                                + Long.parseLong(TimeString[2].split(":")[1])
                                * 60
                                + Math.round(Double.parseDouble(TimeString[2]
                                .split(":")[2]));
                    } else {
                        time[count] = Long.parseLong(TimeString[0].split(":")[0])
                                * 3600
                                + Long.parseLong(TimeString[0].split(":")[1])
                                * 60
                                + Math.round(Double.parseDouble(TimeString[0]
                                .split(":")[2]));
                    }
// 取端口流量
                    for (int i = 0; i < FlowOidGroup.size(); i++) {
                        flow[count][i] = Long.parseLong(revBindings
                                .elementAt(i + 1).getVariable().toString());
                        System.out.println("flow:"+flow[count][i]);
                    }
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }
                if (count == 0)
                    Thread.sleep(5000);// 延时5秒后，第二次取值
            }
            snmp.close();
            transport.close();
// 计算并为时间和最终流量赋值
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            NowTime = sdf.format(c.getTime());// 当前时间
            long AllSubValue = 0;
            for (int i = 0; i < FlowOidGroup.size(); i++) {
                long sub = flow[1][i] - flow[0][i];
                /*
                 * 端口流量值为无符号32位，超出后就归0，所以如果两次取值差值为负，
                 * 必然出现一次归0的情况，由于单个端口的流量不可能超过每5秒1*2^32字节
                 */
                if (sub < 0) {
// 因为端口流量为无符号32位，所以最大值是有符号32位的两倍
                    sub += 2L * Integer.MAX_VALUE;
                }
                AllSubValue += sub;
            }
            if (time[1] - time[0] != 0) {
// 字节换算成兆比特才是最终流量
                FlowValue = (AllSubValue / 1024.0 / 1024 * 8 / (time[1] - time[0]));
                isSuccess = true;
            } else {
                System.out.println("地址：" + IpAddress + "数据采集失败！");
                isSuccess = false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add(".1.3.6.1.2.1.2.2.1.10.1");
        list.add(".1.3.6.1.2.1.2.2.1.10.10001");
        list.add(".1.3.6.1.2.1.2.2.1.10.10005");
        Flow flow = new Flow("172.17.137.60", list);
        flow.calc();
        System.out.println(flow.getFlowValue());
    }
}

