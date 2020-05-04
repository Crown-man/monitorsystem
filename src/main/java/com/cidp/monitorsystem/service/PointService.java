package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.PointMapper;
import com.cidp.monitorsystem.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {
    @Autowired
    PointMapper pointMapper;
    public List<Point> getAll() {
        return pointMapper.getAll();
    }
}
