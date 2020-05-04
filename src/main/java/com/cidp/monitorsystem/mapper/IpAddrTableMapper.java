package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.topology.IpAddrTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/1112:34
 **/
public interface IpAddrTableMapper {
    void insert(@Param("list") List<IpAddrTable> list);
    void deleteAll();
    void insertError(@Param("ip") String ip);
    List<String> selectAddr(@Param("ip") String ip);
}
