package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.service.ArpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/214:45
 **/
@RestController
public class TestController {
    @Autowired
    ArpService arpService;

}
