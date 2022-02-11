package com.lck.controller;

import com.lck.pojo.Admin;
import com.lck.service.AdminService;
import com.lck.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

/***
 #Create by LCK on 2022/1/17  16:51
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    //实现登录判断
    @RequestMapping("/login")
    public String login(String name, String pwd, Model model){
        Admin admin = adminService.login(name, pwd);
        System.out.println(admin);
        if (admin != null){
            //登录成功
            model.addAttribute("admin", admin);

            return "main";
        }else{
            //登录失败
            model.addAttribute("errmsg", "用户名或者密码不正确");
            return "login";
        }

    }

    @RequestMapping("/regist")
    public String regist(Admin admin){
        adminService.regist(admin);
        return "login";
    }
}
