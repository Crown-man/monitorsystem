package com.cidp.monitorsystem.service.dispservice;


import com.cidp.monitorsystem.mapper.FaultOverviewMapper;
import com.cidp.monitorsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @date 2020/5/2 -- 11:14
 **/
@Service
public class FaultOverviewService {
    @Autowired
    private FaultOverviewMapper faultOverviewMapper;

    public FaultOverviewTop failuresNums(String time) {
        String start,end=null;
        FaultOverviewTop faultOverviewTop=new FaultOverviewTop();
        if ("".equals(time) || time == null){
            faultOverviewTop.setTop10IpFaults(faultOverviewMapper.selectIpFaultTop10());
            faultOverviewTop.setFaultNums(faultOverviewMapper.selectAllFaultNums());
        }else{
            String[] split = time.split(",");
            start=split[0].trim();
            end=split[1].trim()+" 59:59:59";
            faultOverviewTop.setTop10IpFaults(faultOverviewMapper.selectIpFaultTop10ByTime(start,end));
            faultOverviewTop.setFaultNums(faultOverviewMapper.selectAllFaultNumsByTime(start,end));
        }
        return faultOverviewTop;
    }



    public FaultOverviewUnder specifiedDeviceFailures(String ip) {
        FaultOverviewUnder result =new FaultOverviewUnder();
//        result.setEquipmentFailuresNumList(faultOverviewMapper.selectSpecifiedDeviceFailuresByIp(ip));
        SixTime sixTime = new SixTime();
        List<SixTime> timeList = new ArrayList<>();
        sixTime.setOne(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip," 08:00"," 12:00"));
        sixTime.setTwo(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip," 12:00"," 16:00"));
        sixTime.setThree(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip," 16:00"," 20:00"));
        sixTime.setFour(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip," 20:00"," 24:00"));
        sixTime.setFive(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip," 24:00"," 04:00"));
        sixTime.setSix(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip," 04:00"," 08:00"));
        timeList.add(sixTime);
        result.setSixTimes(timeList);
        return result;
    }


    public List<Equipment> getSelectList() {
        return faultOverviewMapper.selectAllEquipment();
    }

    public List<Pie> getPieData(String ip) {
        return faultOverviewMapper.selectSpecifiedDeviceFailuresByIp(ip);
    }
}
