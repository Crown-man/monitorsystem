package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.AtTable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AtTableMapper {
    void insert(@Param("list") List<AtTable> list);
}
