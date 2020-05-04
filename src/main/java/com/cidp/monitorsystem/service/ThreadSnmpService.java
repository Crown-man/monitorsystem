package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.handler.SysncSnmphandler;
import com.cidp.monitorsystem.mapper.PingMapper;
import com.cidp.monitorsystem.model.PingSuccess;
import com.cidp.monitorsystem.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.SimpleFormatter;

/**
 * @description: 异步检测snmp 是否开启
 * @author: Zdde丶
 * @create: 2020/4/711:52
 **/
@Service
public class ThreadSnmpService {
    @Autowired
    SysncSnmphandler sysncSnmphandler;
    public List<String> IsCkSnmp(List<String> ip) throws Exception {
        List<CompletableFuture<List<String>>> addlist = new ArrayList<>();
        List<List<String>> lists = ListUtil.averageAssign(ip, 12);
        List<String> canSnmp = new ArrayList<>();
        for (List<String> list : lists) {
            addlist.add(sysncSnmphandler.IsSnmp(list));
        }
        for (int i = 0; i < addlist.size(); i++) {
            canSnmp.addAll(addlist.get(i).get());
        }
        return canSnmp;
    }
}
