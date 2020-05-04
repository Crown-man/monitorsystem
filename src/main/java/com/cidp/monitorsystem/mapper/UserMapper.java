package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User getPassword(@Param("username") String username);
}
