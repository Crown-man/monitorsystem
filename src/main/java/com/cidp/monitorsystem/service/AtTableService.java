package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.AtTableMapper;
import com.cidp.monitorsystem.model.AtTable;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtTableService {
    @Autowired
    AtTableMapper atTableMapper;
    public void getAtTable(List<String> ips){
        String[] IfIndex = {".1.3.6.1.2.1.3.1.1.1"};
        String[] IfPhysAddress = {".1.3.6.1.2.1.3.1.1.2"};
        String[] IfNetAddress = {".1.3.6.1.2.1.3.1.1.3"};
        AtTable atTable;
        List<AtTable> tables = new ArrayList<>();
        for (String ip : ips) {
            SNMPSessionUtil snmp = new SNMPSessionUtil(ip,"161","public","2");
            ArrayList<String> index = snmp.snmpWalk2(IfIndex);
            ArrayList<String> phy = snmp.snmpWalk2(IfPhysAddress);
            ArrayList<String> net = snmp.snmpWalk2(IfNetAddress);
            for (int i =0;i<index.size();i++){
                atTable =new AtTable();
                atTable.setIp(ip);
                atTable.setAtIfIndex(index.get(i).substring(index.get(i).lastIndexOf("=")).replace("=","").trim());
                atTable.setAtPhysAddress(phy.get(i).substring(phy.get(i).lastIndexOf("=")).replace("=","").trim());
                atTable.setAtNetAddress(net.get(i).substring(net.get(i).lastIndexOf("=")).replace("=","").trim());
                tables.add(atTable);
            }
        }
        atTableMapper.insert(tables);
    }
}
