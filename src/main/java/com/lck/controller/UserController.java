package com.lck.controller;

import com.lck.pojo.Users;
import com.lck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/***
 #Create by LCK on 2022/1/19  17:41
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getAll")
    public String getALl(){
        List<Users> all = userService.getAll();
        all.forEach(System.out::println);
        return "users";
    }
}
