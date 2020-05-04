package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.ArpMapper;
import com.cidp.monitorsystem.model.Arp;
import com.cidp.monitorsystem.util.GetLocalIp;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.snmp4j.PDU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/213:52
 **/
@Service
public class ArpService {
    @Autowired
    ArpMapper arpMapper;

    public void getArp(List<String> ips) throws Exception {
        arpMapper.deleteAll();
        ArrayList<Arp> list3 = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Arp arpModel;
        for (String ip : ips) {
            SNMPSessionUtil arp = new SNMPSessionUtil(ip, "161", "public", "2");
            String[] arpIp = {"1.3.6.1.2.1.4.22.1.3"};
            String[] arpMac = {"1.3.6.1.2.1.4.22.1.2"};
            ArrayList<String> arpIplist = arp.snmpWalk2(arpIp);
            ArrayList<String> arpMaclist = arp.snmpWalk2(arpMac);
            for (int i = 0; i < arpIplist.size(); i++) {
                arpModel = new Arp();
                arpModel.setLocalIp(ip);
                arpModel.setIp(arpIplist.get(i).substring(arpIplist.get(i).lastIndexOf("=")).replace("=", ""));
                arpModel.setMac(arpMaclist.get(i).substring(arpMaclist.get(i).lastIndexOf("=")).replace("=", ""));
                arpModel.setTime(df.format(new Date()));
                list3.add(arpModel);
            }
        }
        arpMapper.insert(list3);
    }
}
