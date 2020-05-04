package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.Arp;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/215:18
 **/
public interface ArpMapper {

    Integer InsertArp(@Param("ip") String ip, @Param("mac") String mac);

    void insert(@Param("list") List<Arp> list3);

    void deleteAll();
}
