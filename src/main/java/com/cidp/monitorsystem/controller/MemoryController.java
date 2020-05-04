package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.Memory;
import com.cidp.monitorsystem.service.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/memory")
public class MemoryController {
    @Autowired
    MemoryService memoryService;
    @GetMapping("/info")
    public Memory GetMemory() throws Exception {
        return memoryService.GetInfo("123.56.16.15");
    }

    @GetMapping("/five/{ip}")
    public List<Memory> GetMemWithFive(@PathVariable String ip){
        return memoryService.GetMemWithFive(ip);
    }

    @GetMapping("/one/{ip}")
    public Memory GetMemWithOne(@PathVariable String ip){
        return memoryService.GetMemWithOne(ip);
    }
}
