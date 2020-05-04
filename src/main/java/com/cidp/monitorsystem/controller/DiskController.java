package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.Disk;
import com.cidp.monitorsystem.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/monitor/disk")
public class DiskController {
    @Autowired
    DiskService diskService;
    @GetMapping("/")
    public List<Disk> GetDiskInfo() throws IOException {
        return diskService.GetDiskInfo();
    }
}
