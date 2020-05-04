package com.cidp.monitorsystem.handler;

import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/620:37
 **/
@Component
public class SysncInterfaceHandler {
    @Async(value = "taskExecutor")
    public CompletableFuture<List<String>> system (String ip,String[] oids) throws Exception {
        SNMPSessionUtil inter  = new SNMPSessionUtil(ip,"161","public","2");
        ArrayList<String> snmpGet = inter.snmpWalk2(oids);
        return CompletableFuture.completedFuture(snmpGet);
    }
}
