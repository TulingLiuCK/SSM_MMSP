package com.lck.test;

import com.lck.mapper.ProductInfoMapper;
import com.lck.pojo.ProductInfo;
import com.lck.pojo.vo.ProductInfoVo;
import com.lck.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/***
 #Create by LCK on 2022/1/17  16:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MyTest {
    @Autowired
    ProductInfoMapper mapper;
    @Test
    public void test(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setPname("cao");
        List<ProductInfo> list = mapper.selectCondition(vo);
        list.forEach(productInfo -> System.out.println(productInfo));
    }
}
