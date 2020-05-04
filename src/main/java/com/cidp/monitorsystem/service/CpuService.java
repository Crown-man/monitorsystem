package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.CpuMapper;
import com.cidp.monitorsystem.mapper.DeviceTypeMapper;
import com.cidp.monitorsystem.mapper.NodeMapper;
import com.cidp.monitorsystem.model.Cpu;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import com.cidp.monitorsystem.util.getSnmpf.GetInformation;
import org.snmp4j.PDU;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.TableEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CpuService {
    @Autowired
    CpuMapper cpuMapper;
    @Autowired
    DeviceTypeMapper deviceTypeMapper;
    @Autowired
    NodeMapper nodeMapper;
    public Cpu GetInfo(String ip) throws Exception {
        SNMPSessionUtil sessionUtil = new SNMPSessionUtil(ip, "161", "public", "2");
        String[] isSnmp = {".1.3.6.1.2.1.1.3"};
        ArrayList<String> isSnmpGet = sessionUtil.getIsSnmpGet(PDU.GET, isSnmp);
        String[] oids = {".1.3.6.1.2.1.25.3.3.1.2"};
        String[] oids2 = {".1.3.6.1.2.1.4.1.0"};
        String[] ciscoCpu = {".1.3.6.1.4.1.9.2.1.58"};
        String[] ruijieCpu = {".1.3.6.1.4.1.4881.1.1.10.2.36.1.1.3.0"};
        String[] oids3 = {".1.3.6.1.2.1.1.1.0"};
        ArrayList<String> type = sessionUtil.getSnmpGet(PDU.GET, oids3);
        ArrayList<String> dtype = sessionUtil.snmpWalk2(oids);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> forwording = sessionUtil.getSnmpGet(PDU.GET, oids2);
        if ("-1".equals(isSnmpGet.get(0))) {
            Cpu c = new Cpu();
            c.setIp(ip);
            c.setCpuRate(0.0);
            c.setTime(df.format(new Date()));
            cpuMapper.insert(c);
            return c;
        } else {
            if (!dtype.isEmpty() && "2".equals(forwording.get(0))) {
                Cpu CpuInfo = new Cpu();
                CpuInfo.setIp(ip);
                List<TableEvent> list = GetInformation.getCpuInfo(ip);
                if (list.size() == 1 && list.get(0).getColumns() == null) {
                    throw new Exception("未获取到信息！");
                } else {
                    int percentage = 0;
                    for (TableEvent event : list) {
                        VariableBinding[] values = event.getColumns();
                        System.out.println(values[0].getVariable());
                        percentage += Integer.parseInt(values[0].getVariable().toString());
                    }
                    CpuInfo.setCpuRate((double) (percentage / list.size()));
                }
                CpuInfo.setTime(df.format(new Date()));
                cpuMapper.insert(CpuInfo);
                return CpuInfo;
            } else if (type.get(0).contains("Cisco")) {// cisco设备
                Cpu cisco = new Cpu();
                Integer cp = 0;
                ArrayList<String> cpu = sessionUtil.snmpWalk2(ciscoCpu); //五分钟平均利用率
                List<Integer> c = new ArrayList<>();
                for (String s : cpu) {
                    c.add(Integer.valueOf(s.substring(s.lastIndexOf("=")).replace("=", "").trim()));
                }
                for (Integer integer : c) {
                    cp += integer;
                }
                cisco.setIp(ip);
                if (c.size()==0){
                    cisco.setCpuRate((double) cp );
                }else {
                    cisco.setCpuRate((double) cp/c.size() );
                }
                cisco.setTime(df.format(new Date()));
                cpuMapper.insert(cisco);
                return cisco;
            } else if (type.get(0).contains("Ruijie")) {
                Cpu ruijie = new Cpu();
                ArrayList<String> cpu = sessionUtil.getSnmpGet(PDU.GET, ruijieCpu); //五分钟平均利用率
                ruijie.setIp(ip);
                ruijie.setCpuRate(Double.parseDouble(cpu.get(0)));
                ruijie.setTime(df.format(new Date()));
                cpuMapper.insert(ruijie);
                return ruijie;
            }
        }
        Cpu c = new Cpu();
        c.setIp(ip);
        c.setCpuRate(0.0);
        c.setTime(df.format(new Date()));
        return c;
    }

    //    @Async(value = "taskExecutor")
    public void getAllDeviceCpu() throws Exception {
        List<String> allDevice = deviceTypeMapper.getAllDevice();
        for (String ip : allDevice) {
            GetInfo(ip);
        }
    }

    public List<Cpu> GetInfoInSql(String ip) {
        return cpuMapper.GetInfoInSql(ip);
    }

    public Cpu GetCpuInfoOne(String ip) {
        return cpuMapper.GetCpuInfoOne(ip);
    }
}
