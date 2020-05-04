package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.Cpu;
import com.cidp.monitorsystem.service.CpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/monitor/cpu")
public class CpuController {
    @Autowired
    CpuService cpuService;
    @GetMapping("/{ip}")
    public List<Cpu> GetCpuInfo(@PathVariable String ip) throws Exception {
        System.out.println(ip);
        return cpuService.GetInfoInSql(ip);
    }

    @GetMapping("/one/{ip}")
    public Cpu GetCpuInfoOne(@PathVariable String ip) throws Exception {
        return cpuService.GetCpuInfoOne(ip);
    }
}
