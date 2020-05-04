package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.topology.Edge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EdgeMapper {
    void insert(@Param("list")List<Edge> list);
}
