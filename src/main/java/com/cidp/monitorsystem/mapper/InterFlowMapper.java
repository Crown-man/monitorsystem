package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.InterFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterFlowMapper {
    void insert(@Param("list") List<InterFlow> list);

    List<InterFlow> getFlowByIp(@Param("ip") String ip);
}
