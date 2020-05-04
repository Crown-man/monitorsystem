package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.DeviceTypeMapper;
import com.cidp.monitorsystem.mapper.MemoryMapper;
import com.cidp.monitorsystem.mapper.NodeMapper;
import com.cidp.monitorsystem.model.Cpu;
import com.cidp.monitorsystem.model.Memory;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import com.cidp.monitorsystem.util.getSnmpf.GetInformation;
import org.snmp4j.PDU;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.TableEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemoryService {
    @Autowired
    MemoryMapper memoryMapper;
    @Autowired
    DeviceTypeMapper deviceTypeMapper;
    @Autowired
    NodeMapper nodeMapper;

    public Memory GetInfo(String ip) throws Exception {
        SNMPSessionUtil sessionUtil = new SNMPSessionUtil(ip, "161", "public", "2");
        String[] isSnmp = {".1.3.6.1.2.1.1.3"};
        ArrayList<String> isSnmpGet = sessionUtil.getIsSnmpGet(PDU.GET, isSnmp);
        String[] oids = {".1.3.6.1.2.1.25.3.3.1.2"};
        String[] oids2 = {".1.3.6.1.2.1.4.1.0"};
        String[] oids3 = {".1.3.6.1.2.1.1.1.0"};
        ArrayList<String> type = sessionUtil.getSnmpGet(PDU.GET, oids3);
        DecimalFormat df = new DecimalFormat("#.00");
        String[] pcM = {
                ".1.3.6.1.4.1.2021.4.5.0",//总的物理内存kb
                ".1.3.6.1.4.1.2021.4.6.0",//可以使用的内存kb
        };
        String[] ciscoMem = {"1.3.6.1.4.1.9.9.48.1.1.1.6.1"};//未使用
        String[] ciscoUsed = {"1.3.6.1.4.1.9.9.48.1.1.1.5.1"};//已使用
        ArrayList<String> dtype = sessionUtil.snmpWalk2(oids);
        SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> forwording = sessionUtil.getSnmpGet(PDU.GET, oids2);
        if ("-1".equals(isSnmpGet.get(0))) {
            Memory weiz = new Memory();
            weiz.setIp(ip);
            weiz.setMemRate(0.0);
            weiz.setMemTotalReal(0.0);
            weiz.setMemAvailReal(0.0);
            weiz.setTime(dftime.format(new Date()));
            memoryMapper.insert(weiz);
            return weiz;
        } else {
            if (!dtype.isEmpty() && "2".equals(forwording.get(0))) { //服务器
                Memory memory = new Memory();
                memory.setIp(ip);
                ArrayList<String> list = sessionUtil.getSnmpGet(PDU.GET, pcM);
                memory.setMemTotalReal(Double.parseDouble(df.format(Float.parseFloat(list.get(0)) / 1024)));//得到MB
                memory.setMemAvailReal(Double.parseDouble(df.format(Float.parseFloat(list.get(1)) / 1024)));//同上
                Double d = ((Double.parseDouble(list.get(0)) / 1024) - (Double.parseDouble(list.get(1)) / 1024)) / (Double.parseDouble(list.get(0)) / 1024);
                memory.setMemRate(Double.parseDouble(df.format(d)) * 100);
                memory.setTime(dftime.format(new Date()));
                memoryMapper.insert(memory);
                return memory;
            } else if (type.get(0).contains("Cisco")) {// cisco设备
                Memory cisco = new Memory();
                ArrayList<String> memfree = sessionUtil.getSnmpGet(PDU.GET, ciscoMem);
                ArrayList<String> memused = sessionUtil.getSnmpGet(PDU.GET, ciscoUsed);
                String free = df.format(Double.valueOf(memfree.get(0)) / 1024 / 1024); //Mb
                String used = df.format(Double.valueOf(memused.get(0)) / 1024 / 1024);//Mb
                Double total = (Double.parseDouble(used) / 1024 / 1024) + (Double.parseDouble(free) / 1024 / 1024);
                double usedRate = Double.valueOf(used) / total;
                String ur = df.format(usedRate * 100);
                cisco.setIp(ip);
                cisco.setMemAvailReal(Double.parseDouble(free));
                cisco.setMemTotalReal(Double.parseDouble(df.format(total)));
                cisco.setMemRate(Double.parseDouble(ur));
                cisco.setTime(dftime.format(new Date()));
                memoryMapper.insert(cisco);
                return cisco;
            } else if (type.get(0).contains("Ruijie")) {
                String[] m = {"1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.3.1"};//内存使用率
                Memory ruijie = new Memory();
                ArrayList<String> mr = sessionUtil.getSnmpGet(PDU.GET, m);
                ruijie.setIp(ip);
                ruijie.setMemAvailReal(0.0);
                ruijie.setMemTotalReal(0.0);
                ruijie.setMemRate(Double.parseDouble(mr.get(0)));
                ruijie.setTime(dftime.format(new Date()));
                memoryMapper.insert(ruijie);
                return ruijie;
            }

        }
        Memory weiz = new Memory();
        weiz.setIp(ip);
        weiz.setMemRate(0.0);
        weiz.setMemTotalReal(0.0);
        weiz.setMemAvailReal(0.0);
        weiz.setTime(dftime.format(new Date()));
        return weiz;

    }

    //    @Async(value = "taskExecutor")
    public void getAllDeviceMem() throws Exception {
        List<String> allDevice = deviceTypeMapper.getAllDevice();
        for (String ip : allDevice) {
            GetInfo(ip);
        }
    }

    public List<Memory> GetMemWithFive(String ip) {
        return memoryMapper.GetMemWithFive(ip);
    }

    public Memory GetMemWithOne(String ip) {
        return memoryMapper.GetMemWithOne(ip);
    }
}
