package com.cidp.monitorsystem.service.dispservice;

import com.cidp.monitorsystem.mapper.DiagnosisMapper;
import com.cidp.monitorsystem.model.Diagnosis;
import com.cidp.monitorsystem.model.ExportDiagnosis;
import com.cidp.monitorsystem.model.TrapCollect;
import com.cidp.monitorsystem.service.*;
import com.cidp.monitorsystem.util.ListUtil;
import com.cidp.monitorsystem.util.ip.Ping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 故障管理
 */
@Service
public class DiagnosisService {
    @Autowired
    SystemService systemService;
    @Autowired
    ThreadPingSuccess ping;
    @Autowired
    DiagnosisMapper diagnosisMapper;
    @Autowired
    ThreadSnmpService snmpService;
    @Autowired
    TrapCollectService trapService;
    @Autowired
    PointService pointService;
    @Autowired
    MibsService mibsService;

    /**
     * ping连通性监测
     */
    public void checkPing() throws Exception {
        List<String> ips = systemService.getIps();
        List<String> list = ping.receiveConnectSuccess(ips);
        List<String> noping = ListUtil.getDiffrent(ips, list);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String ip : noping) {
            Diagnosis d = new Diagnosis();
            String sping = Ping.sping(ip, 2, 1000);
            d.setIp(ip);
            d.setStatus(0);//未处理
            d.setPid(5);//ping
            d.setRank("故障");
            d.setTime(df.format(new Date()));
            d.setCause(sping.substring(sping.indexOf("。")).replace("。", "").trim());
            diagnosisMapper.insertByDia(d);
        }


    }

    /**
     * 能ping通下
     * 监测snmp连通性
     */

    public void checkSnmp() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> ips = systemService.getIps();
        List<String> list = ping.receiveConnectSuccess(ips);
        List<String> ckSnmp = snmpService.IsCkSnmp(list);
        for (String s : ckSnmp) {
            Diagnosis d = new Diagnosis();
            d.setIp(s);
            d.setStatus(0);//未处理
            d.setPid(6);//snmp
            d.setTime(df.format(new Date()));
            d.setRank("故障");
            d.setCause("Ping Success,SNMP connect failure！");
            diagnosisMapper.insertByDia(d);
        }
    }

    /**
     * 解析trap消息
     */
    public void checkTrap() {
        List<TrapCollect> ipAndVal = trapService.getIpAndVal();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (TrapCollect i : ipAndVal) {
            Diagnosis d = new Diagnosis();
            d.setIp(i.getIp());
            d.setStatus(0);
            d.setPid(13);
            d.setCause(i.getValue());
            d.setTime(df.format(new Date()));
            diagnosisMapper.insertByDia(d);
        }
    }
    public List<Diagnosis> getAllInfo() {
        List<Diagnosis> info = diagnosisMapper.getAllInfo();

        for (Diagnosis diagnosis : info) {
            List<Diagnosis> lists = diagnosisMapper.selectDiagnosisByIPAndzhtype(diagnosis.getIp(),diagnosis.getCheck().getZhtype());
            diagnosis.setNewTime(lists.get(0).getTime());
            diagnosis.setFrequency(lists.size());
        }
        return  info;
    }
    public Integer updateRemark( String desc, String id) {
        return diagnosisMapper.updateRemark(desc,id);
    }

    public String getRemark(String id) {
        return diagnosisMapper.getRemark(id);
    }



    @Transactional
    public int updateCheckremark(String desc, String[] ids) {
        Integer result=-1;
        for (String id : ids) {
            result=diagnosisMapper.updateRemark(desc,id);
        }
        return result;
    }

    public Integer updateIgnore( String id) {
        return diagnosisMapper.updateIgnore(id);
    }
    @Transactional
    public Integer updateCheckIgnore(String[] ids) {
        Integer result=-1;
        for (String id : ids) {
            result=diagnosisMapper.updateIgnore(id);
        }
        return result;
    }

    public Integer deleteById(String id) {
        return diagnosisMapper.deleteById(id);
    }

    public List<ExportDiagnosis> selectUnhandledDiagnosisInfo() {
        return diagnosisMapper.selectUnhandledDiagnosisInfo();
    }

    public List<ExportDiagnosis> selectProcessedDiagnosisInfo() {
        return diagnosisMapper.selectProcessedDiagnosisInfo();
    }

    public List<ExportDiagnosis> selectIgnoreDiagnosisInfo() {
        return diagnosisMapper.selectIgnoreDiagnosisInfo();
    }

    @Transactional
    public Integer checkDeleteById(String[] ids) {
        Integer result=-1;
        for (String id : ids) {
            result=diagnosisMapper.deleteById(id);
        }
        return result;
    }

    @Transactional
    public Integer updateSelectRemark(String desc, String[] ids) {
        Integer result=-1;
        for (String id : ids) {
            result=diagnosisMapper.updateRemark(desc,id);
        }
        return result;
    }
}
