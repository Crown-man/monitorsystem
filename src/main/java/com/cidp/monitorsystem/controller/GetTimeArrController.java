package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.Memory;
import com.cidp.monitorsystem.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/time")
public class GetTimeArrController {
    @GetMapping("/h")
    public List<String> GetTimeArray(){
        return TimeUtil.GetArrayTimeH(10,5);
    }

    @GetMapping("/memory")
    public List<Memory> GetMemoryArray() throws Exception {
        return TimeUtil.GetArrayMemory(5);
    }
}
