package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.*;
import com.cidp.monitorsystem.service.dispservice.FaultOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @date 2020/5/2 -- 11:11
 **/
@RestController
@RequestMapping("/faultoverview")
public class FaultOverviewController {
    @Autowired
    FaultOverviewService faultOverviewService;

    @GetMapping("/one")
    public FaultOverviewTop failuresNums(String time){
        return faultOverviewService.failuresNums(time);
    }

    @GetMapping("/two")
    public FaultOverviewUnder specifiedDeviceFailures(String ip){
        return faultOverviewService.specifiedDeviceFailures(ip);
    }

    @GetMapping("/select")
    public List<Equipment> getList(){
        return faultOverviewService.getSelectList();
    }
    @GetMapping("/pie")
    public List<Pie> getPieData(String ip){
        return faultOverviewService.getPieData(ip);
    }
}
