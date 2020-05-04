package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.InterfaceMapper;
import com.cidp.monitorsystem.model.Interface;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterfaceService {
    @Autowired
    InterfaceMapper interfaceMapper;
    public void GetInterInfo(String ip) throws Exception {
        interfaceMapper.deleteAll(ip);
        SNMPSessionUtil inter = new SNMPSessionUtil(ip,"161","public","2");
        Interface anInterface;
        String[] oids={".1.3.6.1.2.1.2.2.1.2"};//接口描述
        ArrayList<String> list0 = inter.snmpWalk2(oids);
        String[] oids1 = {".1.3.6.1.2.1.2.2.1.3"};//接口类型
        ArrayList<String> list1 = inter.snmpWalk2(oids1);
        String[] oids2 = {".1.3.6.1.2.1.2.2.1.8"};//接口操作状态
        ArrayList<String> list2 = inter.snmpWalk2(oids2);
        String[] oids3 = {".1.3.6.1.2.1.2.2.1.7"};//接口管理状态
        ArrayList<String> list3 = inter.snmpWalk2(oids3);
        String[] oids4 = {".1.3.6.1.2.1.2.2.1.5"};//接口带宽
        ArrayList<String> list4 = inter.snmpWalk2(oids4);
        String[] oids5 = {".1.3.6.1.2.1.2.2.1.4"};//接口Mtu
        ArrayList<String> list5 = inter.snmpWalk2(oids5);
        String[] oids6 = {".1.3.6.1.2.1.2.2.1.1"};//接口索引
        ArrayList<String> list6 = inter.snmpWalk2(oids6);
        List<Interface> list = new ArrayList<>();
        for (int i = 0;i<list0.size();i++){
            anInterface = new Interface();
            String replace0 = list0.get(i).substring(list0.get(i).lastIndexOf("=")).replace("=", "").trim();
            String replace1 = list1.get(i).substring(list1.get(i).lastIndexOf("=")).replace("=", "").trim();
            String replace2 = list2.get(i).substring(list2.get(i).lastIndexOf("=")).replace("=", "").trim();
            String replace3 = list3.get(i).substring(list3.get(i).lastIndexOf("=")).replace("=", "").trim();
            String replace4 = list4.get(i).substring(list4.get(i).lastIndexOf("=")).replace("=", "").trim();
            String replace5 = list5.get(i).substring(list5.get(i).lastIndexOf("=")).replace("=", "").trim();
            String replace6 = list6.get(i).substring(list6.get(i).lastIndexOf("=")).replace("=", "").trim();
            anInterface.setIp(ip);
            anInterface.setIfindex(replace6);
            anInterface.setIfDescr(replace0);
            anInterface.setIfType(replace1);
            anInterface.setIfOperStatus(replace2);
            anInterface.setIfAdminStatus(replace3);
            anInterface.setIfSpeed(replace4);
            anInterface.setIfMtu(replace5);
            list.add(anInterface);
        }
        interfaceMapper.insert(list);
    }

    public List<Interface> getBaseInfo(String ip) {
        return interfaceMapper.getBaseInfo(ip);
    }
}
