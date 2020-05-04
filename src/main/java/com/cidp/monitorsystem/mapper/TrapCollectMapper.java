package com.cidp.monitorsystem.mapper;


import com.cidp.monitorsystem.model.TrapCollect;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/219:08
 **/
public interface TrapCollectMapper {
    List<TrapCollect> getall();
}
