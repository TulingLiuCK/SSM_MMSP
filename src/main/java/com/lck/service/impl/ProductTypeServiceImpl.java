package com.lck.service.impl;

import com.lck.mapper.ProductTypeMapper;
import com.lck.pojo.ProductType;
import com.lck.pojo.ProductTypeExample;
import com.lck.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.spec.ECField;
import java.util.List;

/***
 #Create by LCK on 2022/1/17  22:10
 */
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    //在业务逻辑层一定有数据访问层的对象
    @Autowired
    private ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
