package com.cidp.monitorsystem;
import com.cidp.monitorsystem.handler.SysncInterFlow;
import com.cidp.monitorsystem.mapper.DeviceTypeMapper;
import com.cidp.monitorsystem.mapper.NodeMapper;
import com.cidp.monitorsystem.service.*;
import com.cidp.monitorsystem.topology.Node;
import com.cidp.monitorsystem.topology.SubNet;
import com.cidp.monitorsystem.topology.TopologyDiscovery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@SpringBootTest
@EnableAsync
class MonitorsystemApplicationTests {
    @Autowired
    PingService pingService;
    @Autowired
    dot1dTpFdbTableService dot1dTpFdbTableService;
    @Autowired
    ThreadPingSuccess threadPingSuccess;
    @Autowired
    ArpService arpService;
    @Autowired
    SystemService systemService;
    @Autowired
    ThreadSnmpService threadSnmpService;
    @Autowired
    InterfaceService interfaceService;
    @Autowired
    TopologyDiscovery topologyDiscovery;
    @Autowired
    SubNet subNet;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    DeviceTypeMapper deviceTypeMapper;
    @Autowired
    AtTableService atTableService;
    @Autowired
    SysncInterFlow sysncInterFlow;
    @Autowired
    InterFlowService interFlowService;
    @Autowired
    CpuService cpuService;
    @Autowired
    MemoryService memoryService;
    @Test
    void contextLoads() throws Exception {
//        arpService.getArp("172.17.137.115");
//        arpService.getArp(nodeMapper.getAllIp());

//        dot1dTpFdbTableService.getdot1dTpFdbTable();
//        deviceTypeMapper.getIpWithLinkS("linkSwitch");

        List<String> allIp = nodeMapper.getAllIp();
//        dot1dTpFdbTableService.getdot1dTpFdbTable(a);
//        for (String s : allIp) {
//            threadPingSuccess.receiveConnectSuccess(s);
//        }
//        CompletableFuture<List<List<String>>> upLoss = sysncInterFlow.getinRate("172.17.137.126", 3000);
//        System.out.println(upLoss.get());
//        CompletableFuture<List<List<String>>> errorRate = sysncInterFlow.getErrorRate("172.17.137.126", 3000);
//        System.out.println(errorRate.get());
//        interFlowService.getFlow();
//        cpuService.GetInfo("172.17.137.115");
        memoryService.getAllDeviceMem();
        cpuService.getAllDeviceCpu();
    }

}
