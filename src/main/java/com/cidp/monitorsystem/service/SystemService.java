package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.handler.SysncSystemHandler;
import com.cidp.monitorsystem.mapper.PingMapper;
import com.cidp.monitorsystem.mapper.SystemInfoMapper;
import com.cidp.monitorsystem.model.PingSuccess;
import com.cidp.monitorsystem.model.SystemInfo;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.snmp4j.PDU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/617:49
 **/
@Service
public class SystemService {
    @Autowired
    PingMapper pingMapper;
    @Autowired
    SysncSystemHandler sysncSystemHandler;
    @Autowired
    SystemInfoMapper systemInfoMapper;

    public void system(List<String> ip) throws Exception {
        List<CompletableFuture<List<String>>> completableFutures = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<PingSuccess> ip = pingMapper.selectIsSnmp();
        SystemInfo systemInfo;
        List<SystemInfo> list = new ArrayList<>();
        String[] oids = {"1.3.6.1.2.1.1.1.0", "1.3.6.1.2.1.1.2.0", "1.3.6.1.2.1.1.3.0", "1.3.6.1.2.1.1.4.0", "1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.1.6.0", "1.3.6.1.2.1.1.7.0"};
        String[] issnmp ={"1.3.6.1.2.1.1.1.0"};
        for (int i = 0; i < ip.size(); i++) {
            SNMPSessionUtil sessionUtil = new SNMPSessionUtil(ip.get(i),"161","public","2");
            ArrayList<String> isSnmpGet = sessionUtil.getIsSnmpGet(PDU.GET, issnmp);
            System.out.println(isSnmpGet);
            systemInfo = new SystemInfo();
            if ("-1".equals(isSnmpGet.get(0))){
                systemInfo.setIp(ip.get(i));
                systemInfo.setSysDecsr("SNMP未响应");
                systemInfo.setSysObjectID("SNMP未响应");
                systemInfo.setSysUpTime("SNMP未响应");
                systemInfo.setSysContact("SNMP未响应");
                systemInfo.setSysName("SNMP未响应");
                systemInfo.setSysLocation("SNMP未响应");
                systemInfo.setSysService("SNMP未响应");
                systemInfo.setTime(df.format(new Date()));
            }else {
                completableFutures.add(sysncSystemHandler.system(ip.get(i), oids));
                for (int j = 0; j < completableFutures.size(); j++) {
                    systemInfo.setIp(ip.get(j));
                    systemInfo.setSysDecsr(completableFutures.get(j).get().get(0));
                    systemInfo.setSysObjectID(completableFutures.get(j).get().get(1));
                    systemInfo.setSysUpTime(completableFutures.get(j).get().get(2));
                    systemInfo.setSysContact(completableFutures.get(j).get().get(3));
                    systemInfo.setSysName(completableFutures.get(j).get().get(4));
                    systemInfo.setSysLocation(completableFutures.get(j).get().get(5));
                    systemInfo.setSysService(completableFutures.get(j).get().get(6));
                    systemInfo.setTime(df.format(new Date()));
                }
            }
            list.add(systemInfo);
        }
        systemInfoMapper.insert(list);
    }

    public void getBase(String ip) throws Exception {
        String[] oids = {"1.3.6.1.2.1.1.1.0", "1.3.6.1.2.1.1.2.0", "1.3.6.1.2.1.1.3.0", "1.3.6.1.2.1.1.4.0", "1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.1.6.0", "1.3.6.1.2.1.1.7.0"};
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SNMPSessionUtil sessionUtil = new SNMPSessionUtil(ip,"161","public","2");
        String[] issnmp ={"1.3.6.1.2.1.1.1.0"};
        SystemInfo  systemInfo = new SystemInfo();
        List<SystemInfo> list = new ArrayList<>();
        ArrayList<String> isSnmpGet = sessionUtil.getIsSnmpGet(PDU.GET, issnmp);
        System.out.println(isSnmpGet);
        if ("-1".equals(isSnmpGet.get(0))){
            systemInfo.setIp(ip);
            systemInfo.setSysDecsr("SNMP未响应");
            systemInfo.setSysObjectID("SNMP未响应");
            systemInfo.setSysUpTime("SNMP未响应");
            systemInfo.setSysContact("SNMP未响应");
            systemInfo.setSysName("SNMP未响应");
            systemInfo.setSysLocation("SNMP未响应");
            systemInfo.setSysService("SNMP未响应");
            systemInfo.setTime(df.format(new Date()));
        }else {
            ArrayList<String> base = sessionUtil.getSnmpGet(PDU.GET, oids);
            systemInfo.setIp(ip);
            systemInfo.setSysDecsr(base.get(0));
            systemInfo.setSysObjectID(base.get(1));
            systemInfo.setSysUpTime(base.get(2));
            systemInfo.setSysContact(base.get(3));
            systemInfo.setSysName(base.get(4));
            systemInfo.setSysLocation(base.get(5));
            systemInfo.setSysService(base.get(6));
            systemInfo.setTime(df.format(new Date()));
        }
        list.add(systemInfo);
        systemInfoMapper.insert(list);
    }

    public List<String> getIps() {
        return systemInfoMapper.getIps();
    }

    public SystemInfo getBaseInfo(String ip) {
        return systemInfoMapper.getBaseInfo(ip);
    }

    public List<SystemInfo> getAllDevice() {
        return systemInfoMapper.getAllDevice();
    }

    public Integer updateDevice(SystemInfo deviceType) {
        return systemInfoMapper.updateDevice(deviceType);
    }
}
