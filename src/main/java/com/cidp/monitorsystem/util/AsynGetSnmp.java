package com.cidp.monitorsystem.util;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AsynGetSnmp {

    public static final int DEFAULT_VERSION = SnmpConstants.version2c;
    public static final String DEFAULT_PROTOCOL = "udp";
    public static final int DEFAULT_PORT = 161;
    public static final long DEFAULT_TIMEOUT = 3 * 1000L;
    public static final int DEFAULT_RETRY = 3;


    /**
     * 创建对象communityTarget
     *
     * @param
     * @param community
     * @param
     * @param
     * @param
     * @return CommunityTarget
     */
    public static CommunityTarget createDefault(String ip, String community) {
        Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip + "/" + DEFAULT_PORT);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(address);
        target.setVersion(DEFAULT_VERSION);
        target.setTimeout(DEFAULT_TIMEOUT); // milliseconds
        target.setRetries(DEFAULT_RETRY);
        return target;
    }


    /**
     * 异步采集信息
     *
     * @param ip
     * @param community
     * @param oid
     */
    public static void snmpAsynWalk(String ip, String community, String oid) {
        final CommunityTarget target = createDefault(ip, community);
        Snmp snmp = null;
        try {
            System.out.println("----> demo start <----");


            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();


            final PDU pdu = new PDU();
            final OID targetOID = new OID(oid);
            final CountDownLatch latch = new CountDownLatch(1);
            pdu.add(new VariableBinding(targetOID));
            pdu.setType(PDU.GETBULK);
            pdu.setMaxRepetitions(Integer.MAX_VALUE);
            pdu.setNonRepeaters(0);

            ResponseListener listener = new ResponseListener() {
                public void onResponse(ResponseEvent event) {
                    ((Snmp) event.getSource()).cancel(event.getRequest(), this);


                    try {
                        PDU response = event.getResponse();
// PDU request = event.getRequest();
// SystemInfo.out.println("[request]:" + request);
                        if (response == null) {
                            System.out.println("[ERROR]: response is null");
                        } else if (response.getErrorStatus() != 0) {
                            System.out.println("[ERROR]: response status" + response.getErrorStatus() + " Text:" + response.getErrorStatusText());
                        } else {
                            System.out.println("Received Walk response value :");
                            VariableBinding vb = response.get(0);


                            boolean finished = checkWalkFinished(targetOID, pdu, vb);
                            if (!finished) {
                                System.out.println(vb.getOid() + " = " + vb.getVariable());
                                pdu.setRequestID(new Integer32(0));
                                pdu.set(0, vb);
                                ((Snmp) event.getSource()).getNext(pdu, target, null, this);
                            } else {
                                System.out.println("SNMP Asyn walk OID value success !");
                                latch.countDown();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        latch.countDown();
                    }


                }
            };


            snmp.getNext(pdu, target, null, listener);
            System.out.println("pdu 已发送,等到异步处理结果...");


            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =:" + wait);
            snmp.close();


            System.out.println("----> demo end <----");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SNMP Asyn Walk Exception:" + e);
        }


    }


    private static boolean checkWalkFinished(OID walkOID, PDU pdu, VariableBinding vb) {
        boolean finished = false;
        if (pdu.getErrorStatus() != 0) {
            System.out.println("[true] pdu.getErrorStatus() != 0 ");
            System.out.println(pdu.getErrorStatusText());
            finished = true;
        } else if (vb.getOid() == null) {
            System.out.println("[true] vb.getOid() == null");
            finished = true;
        } else if (vb.getOid().size() < walkOID.size()) {
            System.out.println("[true] vb.getOid().size() < targetOID.size()");
            finished = true;
        } else if (walkOID.leftMostCompare(walkOID.size(), vb.getOid()) != 0) {
            System.out.println("[true] targetOID.leftMostCompare() != 0");
            finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
            System.out.println("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished = true;
        } else if (vb.getOid().compareTo(walkOID) <= 0) {
            System.out.println("[true] vb.getOid().compareTo(walkOID) <= 0 ");
            finished = true;
        }
        return finished;


    }


    /**
     *
     * @param args
     */
    public static void main(String[] args) {


        String ip = "172.17.137.115";
        String community = "public";


        List<String> oidList = new ArrayList<String>();
        oidList.add(".1.3.6.1.2.1.1.1.0");
        oidList.add(".1.3.6.1.2.1.1.3.0");
        oidList.add(".1.3.6.1.2.1.1.5.0");
// 异步采集数据
        AsynGetSnmp.snmpAsynWalk(ip, community, ".1.3.6.1.2.1.4.21.1.1");
    }

}
