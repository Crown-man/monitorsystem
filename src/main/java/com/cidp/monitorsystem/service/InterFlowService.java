package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.handler.SysncInterFlow;
import com.cidp.monitorsystem.mapper.DeviceTypeMapper;
import com.cidp.monitorsystem.mapper.InterFlowMapper;
import com.cidp.monitorsystem.model.InterFlow;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/8 14:08
 **/
@Service
public class InterFlowService {
    @Autowired
    SysncInterFlow sysncInterFlow;
    @Autowired
    DeviceTypeMapper deviceTypeMapper;
    @Autowired
    InterFlowMapper interFlowMapper;
    public void getFlow() throws Exception {
        List<String> allDevice = deviceTypeMapper.getAllDevice();
        String[] oids ={".1.3.6.1.2.1.2.2.1.1"};//接口index
        String[] oids1 ={".1.3.6.1.2.1.2.2.1.2"};//接口描述
        List<InterFlow> interFlows = new ArrayList<>();
        InterFlow inter;
        for (String ip : allDevice) {
            SNMPSessionUtil snmp = new SNMPSessionUtil(ip,"161","public","2");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            ArrayList<String> indexs = snmp.snmpWalk2(oids);
            ArrayList<String> descs = snmp.snmpWalk2(oids1);
            CompletableFuture<List<List<String>>> interFlow = sysncInterFlow.getInterFlow(ip, 3000);//接口出入流量
            CompletableFuture<List<List<String>>> interFlow1 = sysncInterFlow.getinRate(ip, 3000);//接口出入利用率
            CompletableFuture<List<List<String>>> interFlow2 = sysncInterFlow.getUpLoss(ip, 3000);//接口出入丢包率
            CompletableFuture<List<List<String>>> interFlow3 = sysncInterFlow.getErrorRate(ip, 3000);//接口出入包错误率
            for (int i=0;i<interFlow.get().get(0).size();i++){
                inter = new InterFlow();
                inter.setIp(ip);
                inter.setInFlow(interFlow.get().get(0).get(i));
                inter.setOutFlow(interFlow.get().get(1).get(i));
                inter.setInRate(interFlow1.get().get(0).get(i));
                inter.setOutRate(interFlow1.get().get(1).get(i));
                inter.setInLoss(interFlow2.get().get(0).get(i));
                inter.setOutLoss(interFlow2.get().get(1).get(i));
                inter.setInErrorRate(interFlow3.get().get(0).get(i));
                inter.setOutErrorRate(interFlow3.get().get(1).get(i));
                inter.setIfindex(indexs.get(i).substring(indexs.get(i).lastIndexOf("=")).replace("=","").trim());
                inter.setInterDescr(descs.get(i).substring(descs.get(i).lastIndexOf("=")).replace("=","").trim());
                inter.setTime(df.format(new Date()));
                interFlows.add(inter);
            }
        }
        interFlowMapper.insert(interFlows);
    }

    public List<InterFlow> getFlowByIp(String ip) {
        return interFlowMapper.getFlowByIp(ip);
    }
}
