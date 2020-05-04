package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.*;
import com.sun.xml.internal.ws.wsdl.writer.document.Fault;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @date 2020/5/2 -- 11:16
 **/
public interface FaultOverviewMapper {

    List<FaultNums> selectAllFaultNums();

    List<FaultNums> selectAllFaultNumsByTime(@Param("start") String start,@Param("end") String end);

    List<IpFault> selectIpFaultTop10();

    List<IpFault> selectIpFaultTop10ByTime(@Param("start") String start,@Param("end") String end);

    List<Equipment> selectAllEquipment();

    List<Pie> selectSpecifiedDeviceFailuresByIp(@Param("ip") String ip);

    List<EquipmentFailuresNums> selectSpecifiedDeviceFailuresByIpAccordingtoTime(@Param("ip") String ip,@Param("start") String s,@Param("end") String s1);
}
