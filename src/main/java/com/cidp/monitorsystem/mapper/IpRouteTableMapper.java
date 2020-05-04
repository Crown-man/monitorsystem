package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.topology.IpRouteTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/1112:06
 **/
public interface IpRouteTableMapper {
    void insert(@Param("list") List<IpRouteTable> list);

    List<String> selectNextRoute(@Param("ip") String ip);

    Integer getParentIp(@Param("ip") String ip);

    void deleteAll();
    void insertError(@Param("ip") String ip);
}
