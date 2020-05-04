package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.RespBean;
import com.cidp.monitorsystem.model.User;
import com.cidp.monitorsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitor")
public class LoginController {
    @Autowired
    UserService userService;
    @PostMapping("/doLogin")
    public RespBean login(@RequestBody User user){
        if (userService.verification(user)){
            return RespBean.ok("登录成功！");
        }else {
            return RespBean.error("登录失败，用户名和密码错误！");
        }
    }
}
