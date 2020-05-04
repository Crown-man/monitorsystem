package com.cidp.monitorsystem.handler;

import com.cidp.monitorsystem.util.ip.Ping;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/710:54
 **/
@Component
public class SysncSnmphandler {

    @Async(value = "taskExecutor")
    public CompletableFuture<List<String>> IsSnmp(List<String> list) throws Exception {
        List<String> list1 = new ArrayList<>();
        for (String ip : list) {
            if (Ping.GetSnmpSuccess(ip)) {
                list1.add("1");
            } else {
                list1.add("0");
            }
        }
        return CompletableFuture.completedFuture(list1);
    }
}
