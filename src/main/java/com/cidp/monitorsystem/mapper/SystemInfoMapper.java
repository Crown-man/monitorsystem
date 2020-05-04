package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.SystemInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/7 19:51
 **/
public interface SystemInfoMapper {
    void insert(@Param("list") List<SystemInfo> list);

    SystemInfo getBaseInfo(@Param("ip") String ip);
    List<String> getIps();

    List<SystemInfo> getAllDevice();

    Integer updateDevice(SystemInfo deviceType);
}
