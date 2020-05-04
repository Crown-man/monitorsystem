package com.cidp.monitorsystem.handler;

import com.cidp.monitorsystem.model.DeviceType;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.snmp4j.PDU;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class SysncDeviceTypeHandler {

    @Async(value = "taskExecutor")
    public CompletableFuture<List<DeviceType>> searchDevice (List<DeviceType> deviceTypes) throws Exception {
        ArrayList<DeviceType> list1 = new ArrayList<>();
        DeviceType d1 ;
        String[] host = {".1.3.6.1.2.1.25.1.2.0"};
        String[] sw = {".1.3.6.1.2.1.17.4.3"};
        String[] router = {".1.3.6.1.2.1.4.1.0"};
        for (DeviceType s1 : deviceTypes) {
            d1 = new DeviceType();
            d1.setDeviceIp(s1.getDeviceIp());
            SNMPSessionUtil dv = new SNMPSessionUtil(s1.getIp(),"161", "public", "2");
            d1.setIp(s1.getIp());
            System.out.println(s1.getIp());
            if ("-1".equals(dv.getIsSnmpGet(PDU.GET,".1.3.6.1.2.1.1.5").get(0))) {
                d1.setTypeName("SnmpNotResponse");
            }else {
                d1.setIp(s1.getIp());
                if (!"noSuchObject".equals(dv.getSnmpGet(PDU.GET,host).get(0))){//服务器
                    d1.setTypeName("server");
                }else if ("1".equals(dv.getSnmpGet(PDU.GET,router).get(0)) && dv.snmpWalk2(sw).isEmpty()){//路由器
                    d1.setTypeName("router");
                }else if ("2".equals(dv.getSnmpGet(PDU.GET,router).get(0)) && !dv.snmpWalk2(sw).isEmpty()) {//二层交换机
                    d1.setTypeName("linkSwitch");
                }else if ("1".equals(dv.getSnmpGet(PDU.GET,router).get(0)) && !dv.snmpWalk2(sw).isEmpty()){//三层交换机
                    d1.setTypeName("networkSwitch");
                }else {//未知设备
                    d1.setTypeName("unknownDevice");
                }
            }
            list1.add(d1);
        }
        return CompletableFuture.completedFuture(list1);
    }
}
