package com.cidp.monitorsystem.service;
import com.cidp.monitorsystem.model.*;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.snmp4j.PDU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class InfoService {
    @Autowired
    CpuService cpuService;
    @Autowired
    MemoryService memoryService;
    @Autowired
    InstallService service;
    @Autowired
    InterfaceService interfaceService;
    public BaseInfo GetInfo() throws Exception {
        SNMPSessionUtil snmpSessionUtil = new SNMPSessionUtil("123.56.16.15","161","public","2");
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setIp("123.56.16.15");
        String [] oids ={
                ".1.3.6.1.2.1.1.3.0",//监控时间
                ".1.3.6.1.2.1.1.4.0",//系统联系人
                ".1.3.6.1.2.1.1.5.0",//获取机器名
                ".1.3.6.1.2.1.1.1.0",//服务器基本信息
        };
        ArrayList<String> InfoList = snmpSessionUtil.getSnmpGet(PDU.GET, oids);
        baseInfo.setSysUptime(InfoList.get(0));
        baseInfo.setSysContact(InfoList.get(1));
        baseInfo.setSysName(InfoList.get(2));
        baseInfo.setSysBaseInfo(InfoList.get(3));
        return baseInfo;
    }
}
