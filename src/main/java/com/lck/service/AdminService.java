package com.lck.service;

import com.lck.pojo.Admin;

/***
 #Create by LCK on 2022/1/17  16:29
 */
public interface AdminService {

    //完成登录判断
    Admin login(String name, String pwd);
    //注册
    int regist(Admin admin);
}
