package com.cidp.monitorsystem.handler;

import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.snmp4j.PDU;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @description: 异步多线程 搜集每个在线设备的基本信息
 * @author: Zdde丶
 * @create: 2020/4/6 17:55
 **/
@Component
public class SysncSystemHandler {
    @Async(value = "taskExecutor")
    public CompletableFuture<List<String>> system (String ip,String[] oids) throws Exception {
        SNMPSessionUtil system  = new SNMPSessionUtil(ip,"161","public","2");
        ArrayList<String> snmpGet = null;
        if (!system.getSnmpGet(PDU.GET, oids).isEmpty()){
            snmpGet = system.getSnmpGet(PDU.GET, oids);
        }
        return CompletableFuture.completedFuture(snmpGet);
    }
}
