package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.PingMapper;
import com.cidp.monitorsystem.model.PingSuccess;
import com.cidp.monitorsystem.util.ListUtil;
import com.cidp.monitorsystem.util.ip.Ping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/416:32
 **/
@Service
public class PingService {
    @Autowired
    PingMapper pingMapper;
    @Async(value = "taskExecutor")
    public void ping() throws Exception {
        //后期改进监听器监听信息以每条增加到数据库
        List<String> ip = Ping.GetPingSuccess("172.17.137.115", 25);
        PingSuccess pingSuccess ;
        ArrayList<PingSuccess> list = new ArrayList<>();
        for (String s : ip) {
            pingSuccess = new PingSuccess();
            pingSuccess.setIp(s);
            pingSuccess.setSegment("172.17.137.0");
            pingSuccess.setNetmask(25);
            list.add(pingSuccess);
        }
        pingMapper.InsertPing(list);
    }
}
