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
 * @create: 2020/4/611:20
 **/
@Component
public class SysncPingHandler {

    @Async(value = "taskExecutor")
    public CompletableFuture<List<String>> ping (List<String> list) throws Exception {
        ArrayList<String> list1 = new ArrayList<>();
        for (String ip : list) {
            System.out.println("正在ping：" + ip);
            if (Ping.ping(ip)) {
                list1.add(ip);
            }
        }
        return CompletableFuture.completedFuture(list1);
    }
}
