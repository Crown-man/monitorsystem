package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.BaseInfo;
import com.cidp.monitorsystem.model.SystemInfo;
import com.cidp.monitorsystem.service.InfoService;
import com.cidp.monitorsystem.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/monitor")
public class InfoController {
    @Autowired
    SystemService systemService;
    @GetMapping("/dev/{ip}")
    public SystemInfo getBaseInfo(@PathVariable String ip) {
        return systemService.getBaseInfo(ip);
    }
}
