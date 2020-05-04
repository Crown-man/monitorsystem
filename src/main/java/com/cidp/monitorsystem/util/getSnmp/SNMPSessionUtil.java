package com.cidp.monitorsystem.util.getSnmp;


import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class SNMPSessionUtil {
    private Snmp snmp;
    private Address targetAddress;
    private String hostComputer;
    private String port;
    private String community;
    private int version;
    private CommunityTarget communityTarget;

    public SNMPSessionUtil(String hostComputer, String port, String community, String version) {
        this.hostComputer = hostComputer;
        this.community = community;
        this.port = port;
        if (version.equals("2")) {
            this.version = SnmpConstants.version2c;
        } else {
            System.out.println("版本不对");
        }
        init();
    }

    // 初始化
    public void init() {
        String target = "udp:" + hostComputer + "/" + port;
        targetAddress = GenericAddress.parse(target);
        try {
            MessageDispatcher messageDispatcher = new MessageDispatcherImpl();
            TransportMapping transportMapping = new DefaultUdpTransportMapping();
            snmp = new Snmp(transportMapping);
            snmp.listen();
            // 设置权限
            communityTarget = new CommunityTarget();
            communityTarget.setCommunity(new OctetString(community));
            communityTarget.setAddress(targetAddress);
            // 通信不成功重复次数
            communityTarget.setRetries(1);
            // 超时时间
            communityTarget.setTimeout(2 * 1000);
            // 设置版本
            communityTarget.setVersion(version);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 单个获取方式
    public ArrayList<String> getSnmpGet(Integer type, String... oid) throws Exception {
        ResponseEvent event = null;
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < oid.length; i++) {
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(oid[i])));
            // 设置请求方式
            pdu.setType(type);
            event = snmp.send(pdu, communityTarget);
            if (null != event && event.getResponse() != null) {
                Vector<VariableBinding> vector = (Vector<VariableBinding>) event.getResponse().getVariableBindings();
                VariableBinding vec = vector.elementAt(0);
                list.add(i,vector.elementAt(0).getVariable().toString());
            }
        }
        return list;
    }

    // 单个获取方式
    public ArrayList<String> getIsSnmpGet(Integer type, String... oid) throws Exception {
        ResponseEvent event = null;
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < oid.length; i++) {
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(oid[i])));
            // 设置请求方式
            pdu.setType(type);
            event = snmp.send(pdu, communityTarget);
            if (null == event || event.getResponse() == null) {
                list.add(0,"-1");
            }else {
                list.add(0,"success connection!");
            }
        }
        return list;
    }

    // 对结果进行解析
    public Vector<VariableBinding> readResponse(ResponseEvent event) {
        if (null != event && event.getResponse() != null) {
            System.out.println("收到回复，正在解析");
            Vector<VariableBinding> vector = (Vector<VariableBinding>) event.getResponse().getVariableBindings();

            for (int i = 0; i < vector.size(); i++) {
                VariableBinding vec = vector.elementAt(i);
                System.out.println(vec.getVariable());
            }
            return vector;
        } else
            System.out.println("没有收到回复");
        return null;
    }

    // 遍历请求
    public ArrayList<String> snmpWalk2(String oids[]) {
        ArrayList<String> mapList = new ArrayList<>();
        // 设置TableUtil的工具
        TableUtils utils = new TableUtils(snmp, new DefaultPDUFactory(PDU.GETBULK));
        utils.setMaxNumRowsPerPDU(2);
        OID[] clounmOid = new OID[oids.length];
        for (int i = 0; i < oids.length; i++) {
            clounmOid[i] = new OID(oids[i]);
        }
        // 获取查询结果list,new OID("0"),new OID("40")设置输出的端口数量
//        List<TableEvent> list=utils.getTable(communityTarget,clounmOid,new OID("0"),new OID("40"));
        List<TableEvent> list = utils.getTable(communityTarget, clounmOid, null, null);
        for (int i = 0; i < list.size(); i++) {
            // 取list中的一行
            TableEvent te = (TableEvent) list.get(i);
            // 对每一行结果进行再次拆分
            VariableBinding[] vb = te.getColumns();
            if (vb != null) {
                for (int j = 0; j < vb.length; j++) {
//                    SystemInfo.out.println(vb[j].toString().substring(vb[j].toString().lastIndexOf("=")).replace("=",""));
                    mapList.add(j,vb[j].toString());
                }
            } else {
                System.out.println(this.hostComputer+"网络不通或者配置有误！");
//                throw new NullPointerException("被监控系统的网络不通或IP或其它相关配置错识！");
            }
        }
        return mapList;
    }
    // 遍历请求
    public List<TableEvent> snmpWalk(String oids[]) {
        // 设置TableUtil的工具
        TableUtils utils = new TableUtils(snmp, new DefaultPDUFactory(PDU.GETBULK));
        utils.setMaxNumRowsPerPDU(2);
        OID[] clounmOid = new OID[oids.length];
        for (int i = 0; i < oids.length; i++) {
            clounmOid[i] = new OID(oids[i]);
        }
        List<TableEvent> list = utils.getTable(communityTarget, clounmOid, null, null);

        return list;
    }

}
