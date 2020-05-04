package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.DeviceType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceTypeMapper {
    void insert(@Param("list") List<DeviceType> list);
    List<String> getIpWithLinkS(@Param("type") String type);
    List<DeviceType> getAllDeviceShow();
    Integer updateDevice(DeviceType deviceType);
    List<String> getAllDevice();
}
