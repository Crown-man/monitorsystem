package com.cidp.monitorsystem.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/1112:26
 **/
public interface NodeMapper {

    void insert(@Param("ip") String ip, @Param("parentId") Integer parentId);

    Integer getParentIp(@Param("ip") String ip);
    void deleteAll();
    List<String> getAllIp();
}
