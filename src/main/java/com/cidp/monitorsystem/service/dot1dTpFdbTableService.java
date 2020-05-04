package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.dot1dTpFdbTableMapper;
import com.cidp.monitorsystem.model.dot1dTpFdbTable;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/515:30
 **/
@Service
public class dot1dTpFdbTableService {
    @Autowired
    dot1dTpFdbTableMapper tableMapper;

    public void getdot1dTpFdbTable(List<String> ips) {
        for (String ip : ips) {
            SNMPSessionUtil snmpSessionUtil = new SNMPSessionUtil(ip, "161", "public", "2");
            String[] oids1 = {".1.3.6.1.2.1.17.4.3.1.1"};
            String[] oids2 = {".1.3.6.1.2.1.17.4.3.1.2"};
            String[] oids3 = {".1.3.6.1.2.1.17.4.3.1.3"};
            ArrayList<String> list1 = snmpSessionUtil.snmpWalk2(oids1);
            ArrayList<String> list2 = snmpSessionUtil.snmpWalk2(oids2);
            ArrayList<String> list3 = snmpSessionUtil.snmpWalk2(oids3);
            dot1dTpFdbTable table;
            ArrayList<dot1dTpFdbTable> list = new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                table = new dot1dTpFdbTable();
                table.setIp(ip);
                table.setDot1dTpFdbAddress(list1.get(i).substring(list1.get(i).lastIndexOf("=")).replace("=", ""));
                table.setDot1dTpFdbPort(list2.get(i).substring(list2.get(i).lastIndexOf("=")).replace("=", ""));
                table.setDot1dTpFdbStatus(list3.get(i).substring(list3.get(i).lastIndexOf("=")).replace("=", ""));
                list.add(table);
            }
            tableMapper.insert(list);
        }
    }
}
