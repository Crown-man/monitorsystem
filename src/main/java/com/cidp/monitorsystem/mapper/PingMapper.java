package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.PingSuccess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/416:46
 **/
public interface PingMapper {
    void InsertPing(@Param("list") List<PingSuccess> list);

    List<PingSuccess> selectIsSnmp();
}
