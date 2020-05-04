package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.InterFlow;
import com.cidp.monitorsystem.model.Interface;
import com.cidp.monitorsystem.service.InterFlowService;
import com.cidp.monitorsystem.service.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/monitor")
public class InterController {
    @Autowired
    InterfaceService interfaceService;
    @Autowired
    InterFlowService flowService;
    @GetMapping("/base/{ip}")
    public List<Interface> getBaseInfo(@PathVariable String ip){
        return interfaceService.getBaseInfo(ip);
    }
    @GetMapping("/flow/{ip}")
    public List<InterFlow> getFlowInfo(@PathVariable String ip){
        return flowService.getFlowByIp(ip);
    }
}
