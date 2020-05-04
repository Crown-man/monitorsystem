package com.cidp.monitorsystem.topology;

import com.cidp.monitorsystem.mapper.EdgeMapper;
import com.cidp.monitorsystem.mapper.IpAddrTableMapper;
import com.cidp.monitorsystem.mapper.IpRouteTableMapper;
import com.cidp.monitorsystem.mapper.NodeMapper;
import com.cidp.monitorsystem.service.InterfaceService;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.snmp4j.PDU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description: 用于拓扑发现的主要类
 * @author: Zdde丶
 * @create: 2020/4/1012:31
 **/
@Component
public class TopologyDiscovery {
    @Autowired
    IpRouteTableMapper ipRouteTableMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    IpAddrTableMapper ipAddrTableMapper;
    @Autowired
    InterfaceService interfaceService;
    @Autowired
    EdgeMapper edgeMapper;

    public Node findNode(String ip) throws Exception {
        SNMPSessionUtil issnmp = new SNMPSessionUtil(ip, "161", "public", "2");
        ArrayList<String> isSnmpGet = issnmp.getIsSnmpGet(PDU.GET, ".1.3.6.1.2.1.1.3");
        ipRouteTableMapper.deleteAll();
        nodeMapper.deleteAll();
        ipAddrTableMapper.deleteAll();
        if ("-1".equals(isSnmpGet.get(0))) {
            System.out.println(ip + "该设备未开启snmp服务，或者snmp服务配置错误");
            return null;
        } else {
            List<String> hasSearchList = new ArrayList<String>();
            String[] oids1 = {".1.3.6.1.2.1.4.20.1.1"};//ipAdEntAddr
            String[] oids2 = {".1.3.6.1.2.1.4.20.1.2"};//ipAdEntIfIndex
            String[] oids3 = {".1.3.6.1.2.1.4.20.1.3"};//ipAdEntNetMask
            String[] router1 = {".1.3.6.1.2.1.4.21.1.1"};//ipRouteDest
            String[] router2 = {".1.3.6.1.2.1.4.21.1.2"};//ipRouteIfIndex
            String[] router3 = {".1.3.6.1.2.1.4.21.1.7"};//ipRouteNextHop
            String[] router4 = {".1.3.6.1.2.1.4.21.1.8"};//ipRouteType
            String[] router5 = {".1.3.6.1.2.1.4.21.1.11"};//ipRouteMask
            Node node = new Node();
            node.setIp(ip);
            node.setParentId(0);
            LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<>();
            queue.offer(node);
            List<Edge> edgeList = new ArrayList<>();
            Edge edge;
            while (!queue.isEmpty()) {
                Node node1 = queue.poll();
                SNMPSessionUtil issnmp2 = new SNMPSessionUtil(node1.getIp(), "161", "public", "2");
                ArrayList<String> isSnmpGet2 = issnmp2.getIsSnmpGet(PDU.GET, ".1.3.6.1.2.1.1.3");
                if ("-1".equals(isSnmpGet2.get(0))) continue;
                if (hasSearchList.contains(node1.getIp())) continue;


                System.out.println("弹出队列:" + node1.getIp());


                if (!node1.getIp().isEmpty()) interfaceService.GetInterInfo(node1.getIp());
                SNMPSessionUtil aPublic = new SNMPSessionUtil(node1.getIp(), "161", "public", "2");
                ArrayList<String> ipAdEntAddr = aPublic.snmpWalk2(oids1);
                ArrayList<String> ipAdEntIfIndex = aPublic.snmpWalk2(oids2);
                ArrayList<String> ipAdEntNetMask = aPublic.snmpWalk2(oids3);
                IpAddrTable ipAddrTable;
                List<IpAddrTable> ipAddrTables = new ArrayList<>();
                for (int i = 0; i < ipAdEntAddr.size(); i++) {
                    ipAddrTable = new IpAddrTable();
                    ipAddrTable.setIp(node1.getIp());
                    ipAddrTable.setIpAdEntAddr(ipAdEntAddr.get(i).substring(ipAdEntAddr.get(i).lastIndexOf("=")).replace("=", "").trim());
                    ipAddrTable.setIpAdEntIfIndex(ipAdEntIfIndex.get(i).substring(ipAdEntIfIndex.get(i).lastIndexOf("=")).replace("=", "").trim());
                    ipAddrTable.setIpAdEntNetMask(ipAdEntNetMask.get(i).substring(ipAdEntNetMask.get(i).lastIndexOf("=")).replace("=", "").trim());
                    ipAddrTables.add(ipAddrTable);
                }
                if (!ipAddrTables.isEmpty()) {
                    ipAddrTableMapper.insert(ipAddrTables);
                } else {
                    ipAddrTableMapper.insertError(node1.getIp());
                }
                nodeMapper.insert(node1.getIp(), node1.getParentId());
                String ip1 = node1.getIp();
                IpRouteTable ipRouteTable;
                SNMPSessionUtil nextip = new SNMPSessionUtil(ip1, "161", "public", "2");
                ArrayList<String> rlist1 = nextip.snmpWalk2(router1);
                ArrayList<String> rlist2 = nextip.snmpWalk2(router2);
                ArrayList<String> rlist3 = nextip.snmpWalk2(router3);
                ArrayList<String> rlist4 = nextip.snmpWalk2(router4);
                ArrayList<String> rlist5 = nextip.snmpWalk2(router5);
                List<IpRouteTable> list = new ArrayList<>();
                for (int j = 0; j < rlist1.size(); j++) {
                    ipRouteTable = new IpRouteTable();
                    ipRouteTable.setIp(ip1);
                    ipRouteTable.setIpRouteDest(rlist1.get(j).substring(rlist1.get(j).lastIndexOf("=")).replace("=", "").trim());
                    ipRouteTable.setIpRouteIfIndex(rlist2.get(j).substring(rlist2.get(j).lastIndexOf("=")).replace("=", "").trim());
                    ipRouteTable.setIpRouteNextHop(rlist3.get(j).substring(rlist3.get(j).lastIndexOf("=")).replace("=", "").trim());
                    ipRouteTable.setIpRouteType(rlist4.get(j).substring(rlist4.get(j).lastIndexOf("=")).replace("=", "").trim());
                    ipRouteTable.setIpRouteMask(rlist5.get(j).substring(rlist5.get(j).lastIndexOf("=")).replace("=", "").trim());
                    list.add(ipRouteTable);
                }
                if (list.isEmpty()) {
                    ipRouteTableMapper.insertError(ip1);
                } else {
                    ipRouteTableMapper.insert(list);
                }

                hasSearchList.add(ip1);
                List<String> nextRoute = ipRouteTableMapper.selectNextRoute(ip1);
                List<String> addr = ipAddrTableMapper.selectAddr(ip1);
                if (!addr.isEmpty()) {
                    hasSearchList.addAll(addr);
                }
                Node n;
                for (String s : nextRoute) {
                    if (hasSearchList.contains(s)) continue;
                    edge = new Edge();
                    edge.setSource(ip1);
                    edge.setTarget(s);
                    edgeList.add(edge);
                    n = new Node();
                    n.setParentId(nodeMapper.getParentIp(node1.getIp()));
                    n.setIp(s);
                    queue.offer(n);
                    System.out.println("压入队列:" + n.getIp());
                }
            }
            edgeMapper.insert(edgeList);
            return node;
        }

    }
}
