package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.Cpu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CpuMapper {
    void insert(Cpu cpuInfo); //传入实体类时 不需要 @Param 不然会报错

    String getNextIp(@Param("ip") String ip);

    String getMaxIp();

    String getMinIp();

    List<Cpu> GetInfoInSql(@Param("ip") String ip);

    Cpu GetCpuInfoOne(@Param("ip") String ip);
}
