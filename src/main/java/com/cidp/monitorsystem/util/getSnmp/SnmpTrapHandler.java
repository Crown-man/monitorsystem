package com.cidp.monitorsystem.util.getSnmp;

import org.snmp4j.*;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.*;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import java.io.IOException;


/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/3/31 19:09
 **/
public class SnmpTrapHandler implements CommandResponder {
    private static final int threadNum = 200;
    private Snmp snmp = null;
    private String address;

    public SnmpTrapHandler(String ipAddress, String port) {
        this.address = "udp:" + ipAddress + "/" + port;
    }

    public void init() {
        ThreadPool snmpTrap = ThreadPool.create("SnmpTrap", threadNum);
        MessageDispatcher messageDispatcher = new MultiThreadedMessageDispatcher(snmpTrap, new MessageDispatcherImpl());
        messageDispatcher.addMessageProcessingModel(new MPv1());
        messageDispatcher.addMessageProcessingModel(new MPv2c());
        OctetString localEnglineId = new OctetString(MPv3.createLocalEngineID());
        USM usm = new USM(SecurityProtocols.getInstance().addDefaultProtocols(), localEnglineId, 0);
        UsmUser usmUser = new UsmUser(new OctetString("SNMPV3"), AuthSHA.ID, new OctetString("authPassword"), PrivAES128.ID, new OctetString("privPassword"));
        usm.addUser(usmUser.getSecurityName(), usmUser);
        messageDispatcher.addMessageProcessingModel(new MPv3(usm));
        TransportMapping<?> transportMapping = null;
        UdpAddress parse = (UdpAddress) GenericAddress.parse(System.getProperty("snmp4j.listenAddress", address));
        try {
            transportMapping = new DefaultUdpTransportMapping(parse);
            //3、正式创建snmp
            snmp = new Snmp(messageDispatcher, transportMapping);
            //开启监听
            snmp.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {

        init();
        //一定要将当前对象添加至commandResponderListeners中
        snmp.addCommandResponder(this);
        System.out.println("开始监听trap信息：");
    }

    @Override
    public void processPdu(CommandResponderEvent event) {
        String version = null;
        String community = null;
        if (event.getPDU().getType() == PDU.V1TRAP) {
            version = "v1";
            community = new String(event.getSecurityName());
        } else if (event.getPDU().getType() == PDU.TRAP) {
            if (event.getSecurityModel() == 2) {
                version = "v2";
                community = new String(event.getSecurityName());
            } else {
                version = "v3";
            }
        }
        System.out.println("接收到的trap信息：[发送来源=" + event.getPeerAddress() + ",snmp版本=" + version + ",团体名=" + community + ", 携带的变量=" + event.getPDU().getVariableBindings() + "]");
    }

    public static void main(String[] args) {
        SnmpTrapHandler handler = new SnmpTrapHandler("172.17.136.33","162");
        handler.start();
    }

}
