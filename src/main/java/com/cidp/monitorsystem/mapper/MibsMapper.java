package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.Mibs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MibsMapper {
    List<Mibs> getPid();
}
