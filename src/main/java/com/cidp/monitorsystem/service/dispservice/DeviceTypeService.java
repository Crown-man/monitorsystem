package com.cidp.monitorsystem.service.dispservice;

import com.cidp.monitorsystem.mapper.DeviceTypeMapper;
import com.cidp.monitorsystem.model.DeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTypeService {
    @Autowired
    DeviceTypeMapper deviceTypeMapper;
    public List<DeviceType> getAllDevice() {
        return deviceTypeMapper.getAllDeviceShow();
    }


    public Integer updateDevice(DeviceType deviceType) {
        return deviceTypeMapper.updateDevice(deviceType);
    }
}
