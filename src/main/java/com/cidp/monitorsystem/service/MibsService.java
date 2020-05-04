package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.MibsMapper;
import com.cidp.monitorsystem.model.Mibs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MibsService {
    @Autowired
    MibsMapper mibsMapper;
    public List<Mibs> getPid() {
        return mibsMapper.getPid();
    }
}
