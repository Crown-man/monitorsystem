package com.cidp.monitorsystem.topology;

import com.cidp.monitorsystem.handler.SysncDeviceTypeHandler;
import com.cidp.monitorsystem.mapper.DeviceTypeMapper;
import com.cidp.monitorsystem.mapper.NodeMapper;
import com.cidp.monitorsystem.model.DeviceType;
import com.cidp.monitorsystem.model.SystemInfo;
import com.cidp.monitorsystem.service.ArpService;
import com.cidp.monitorsystem.service.SystemService;
import com.cidp.monitorsystem.util.ListUtil;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import org.snmp4j.PDU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 用于子网发现的主要类
 */
@Component
public class SubNet {
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    DeviceTypeMapper deviceTypeMapper;
    @Autowired
    SysncDeviceTypeHandler sysncDeviceTypeHandler;

    public void getSubNet() throws Exception {
        List<CompletableFuture<List<DeviceType>>> addlist = new ArrayList<>();
        List<String> allIp = nodeMapper.getAllIp();
        List<String> hadSearchIp = new ArrayList<>();
        String[] arpIp = {".1.3.6.1.2.1.4.22.1.3"};
        List<DeviceType> subip = new ArrayList<>();
        DeviceType deviceType1;
        for (int j = 0; j < allIp.size(); j++) {
            SNMPSessionUtil dt = new SNMPSessionUtil(allIp.get(j), "161", "public", "2");
            ArrayList<String> d = dt.snmpWalk2(arpIp);
            for (String s : d) {
                if (hadSearchIp.contains(s.substring(s.lastIndexOf("=")).replace("=", "").trim())) continue;
                deviceType1 = new DeviceType();
                deviceType1.setDeviceIp(allIp.get(j));
                deviceType1.setIp(s.substring(s.lastIndexOf("=")).replace("=", "").trim());
                subip.add(deviceType1);
                hadSearchIp.add(s.substring(s.lastIndexOf("=")).replace("=", "").trim());
            }
            System.out.println(hadSearchIp);
        }
        List<List<DeviceType>> lists = ListUtil.averageAssign(subip, 30);//30线程
        for (List<DeviceType> list : lists) {
            addlist.add(sysncDeviceTypeHandler.searchDevice(list));
        }
        List<DeviceType> all = new ArrayList<>();
        for (CompletableFuture<List<DeviceType>> future : addlist) {
            all.addAll(future.get());
        }
        deviceTypeMapper.insert(all);
    }
}
