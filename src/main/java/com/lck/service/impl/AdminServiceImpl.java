package com.lck.service.impl;

import com.lck.mapper.AdminMapper;
import com.lck.pojo.Admin;
import com.lck.pojo.AdminExample;
import com.lck.service.AdminService;
import com.lck.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/***
 #Create by LCK on 2022/1/17  16:30
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户名到数据库中查询相应用户对象
        //如果有条件，一定要创建AdminExample的对象，用来封装条件
        AdminExample adminExample = new AdminExample();
        /*如何添加条件
        * select * from admin where a_name = 'admin'*/
        //添加用户名a_name条件
        adminExample.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(adminExample);
        if (list.size()>0){
            Admin admin = list.get(0);
            //如果查询到用户对象，再匹配密码 密码是密文
            String miPwd = MD5Util.getMD5(pwd);
            if (miPwd.equals(admin.getaPass())){
                return admin;
            }
        }
        //如果
        return null;
    }

    @Override
    public int regist(Admin admin) {
        admin.setaPass(MD5Util.getMD5(admin.getaPass()));
        return adminMapper.insert(admin);
    }


}
