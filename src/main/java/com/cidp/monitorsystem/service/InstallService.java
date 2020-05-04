package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.model.InstalledName;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InstallService {
    public InstalledName GetInfo(){
        InstalledName installedName = new InstalledName();
        SNMPSessionUtil snmpSessionUtil = new SNMPSessionUtil("123.56.16.15","161","public","2");
        installedName.setId("123.56.16.15");
        String[] oid={".1.3.6.1.2.1.25.4.2.1.2"};
        ArrayList<String> list = snmpSessionUtil.snmpWalk2(oid);
        ArrayList<String> newlist = new ArrayList<>();
        for (int i=0;i<list.size();i++) {
            newlist.add(i,list.get(i).substring(list.get(i).lastIndexOf("=")).replace("=", ""));
        }
        installedName.setHrSWInstalledName(newlist);
        return installedName;
    }
}
