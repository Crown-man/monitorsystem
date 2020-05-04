package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.dot1dTpFdbTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/515:47
 **/
public interface dot1dTpFdbTableMapper {
    void insert(@Param("list") List<dot1dTpFdbTable> list);
}
