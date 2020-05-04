package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.handler.SysncPingHandler;
import com.cidp.monitorsystem.handler.SysncSnmphandler;
import com.cidp.monitorsystem.mapper.PingMapper;
import com.cidp.monitorsystem.model.Interface;
import com.cidp.monitorsystem.model.PingSuccess;
import com.cidp.monitorsystem.util.ListUtil;
import com.cidp.monitorsystem.util.ip.GetRangeIpUtil;
import com.cidp.monitorsystem.util.ip.Ping;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @description: 利用多线程 PING远端设备 收集在线设备
 * @author: Zdde丶
 * @create: 2020/4/6 10:21
 **/
@Component
public class ThreadPingSuccess {
    @Autowired
    SysncPingHandler sysncPingHandler;
    public List<String> receiveConnectSuccess(List<String> ips) throws Exception {
        List<CompletableFuture<List<String>>> completableFutures = new ArrayList<>();
        List<List<String>> lists = ListUtil.averageAssign(ips, 10);
        for (int i = 0; i < lists.size(); i++) {
            completableFutures.add(sysncPingHandler.ping(lists.get(i)));
        }
        ArrayList<PingSuccess> pingsuccess = new ArrayList<>();
        ArrayList<String> isPing = new ArrayList<>();
        PingSuccess pingSuccess;
        for (int i = 0; i < completableFutures.size(); i++) {
            if (!completableFutures.get(i).get().isEmpty()) {
                isPing.addAll(completableFutures.get(i).get());
            }
        }
//        for (int i = 0; i < isPing.size(); i++) {
//            pingSuccess = new PingSuccess();
//            pingSuccess.setIp(isPing.get(i));
//            pingSuccess.setSegment("172.17.137.0");
//            pingSuccess.setNetmask(25);
//            pingSuccess.setTime(df.format(new Date()));
//            pingsuccess.add(pingSuccess);
//        }
        return isPing;

       // pingMapper.InsertPing(pingsuccess);
    }

}
