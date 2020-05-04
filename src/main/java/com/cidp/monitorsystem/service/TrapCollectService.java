package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.TrapCollectMapper;
import com.cidp.monitorsystem.model.TrapCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrapCollectService {
    @Autowired
    TrapCollectMapper trapCollectMapper;
    public List<TrapCollect> getIpAndVal() {
        return trapCollectMapper.getall();
    }
}
