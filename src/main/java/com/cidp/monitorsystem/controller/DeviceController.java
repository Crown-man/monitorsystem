package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.DeviceType;
import com.cidp.monitorsystem.model.RespBean;
import com.cidp.monitorsystem.model.SystemInfo;
import com.cidp.monitorsystem.service.SystemService;
import com.cidp.monitorsystem.service.dispservice.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    DeviceTypeService deviceTypeService;
    @Autowired
    SystemService systemService;
    @GetMapping("/disp")
    public List<SystemInfo> getDispInfo(){
        return systemService.getAllDevice();
    }

    @PutMapping("/disp")
    public RespBean updateDevice(@RequestBody SystemInfo deviceType){
        if ( systemService.updateDevice(deviceType)>0){
            return RespBean.ok("更新成功！");
        }else {
            return RespBean.error("更新失败");
        }
    }
}
