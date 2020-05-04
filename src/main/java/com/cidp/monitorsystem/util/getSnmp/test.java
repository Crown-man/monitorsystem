package com.cidp.monitorsystem.util.getSnmp;

import org.snmp4j.PDU;
import org.snmp4j.util.TableEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) throws Exception {
        SNMPSessionUtil snmpSessionUtil=new SNMPSessionUtil("172.17.137.126","161","public", "2");
        String[] oid = {"1.3.6.1.4.1.4881.1.1.10.2.36.1.1.3.0"};
        ArrayList<String> snmpGet = snmpSessionUtil.getSnmpGet(PDU.GET, oid);
        String [] oids= {"1.3.6.1.4.1.9.1.951"};
//        String [] oids= {".1.3.6.1.2.1.4.1"};
        ArrayList<String> tableEvents = snmpSessionUtil.snmpWalk2(oid);
        System.out.println(tableEvents);
        System.out.println(snmpGet);

    }
}
