package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.Interface;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/915:24
 **/
public interface InterfaceMapper {
    void insert(@Param("list") List<Interface> list);
    void deleteAll(@Param("ip") String ip);

    List<Interface> getBaseInfo(@Param("ip") String ip);
}
