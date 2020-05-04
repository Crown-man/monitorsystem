package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.Memory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemoryMapper {
    void insert(Memory memory);

    List<Memory> GetMemWithFive(@Param("ip") String ip);

    Memory GetMemWithOne(@Param("ip") String ip);
}
