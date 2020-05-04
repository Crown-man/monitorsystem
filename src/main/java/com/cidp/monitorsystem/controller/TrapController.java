package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.TrapCollect;
import com.cidp.monitorsystem.service.TrapCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trap")
public class TrapController {
    @Autowired
    TrapCollectService trapCollectService;
    @GetMapping("/")
    public List<TrapCollect> getAllTrap(){
        return trapCollectService.getIpAndVal();
    }
}
