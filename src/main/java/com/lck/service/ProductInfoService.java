package com.lck.service;

import com.github.pagehelper.PageInfo;
import com.lck.pojo.ProductInfo;
import com.lck.pojo.vo.ProductInfoVo;

import java.util.List;

/***
 #Create by LCK on 2022/1/17  17:21
 */
public interface ProductInfoService {
    //显示全部商品
    List<ProductInfo> getAll();

    //分页功能
    PageInfo splitPage(int pageNum,int pageSize);

    int save(ProductInfo info);

    /**
     * @return: com.lck.pojo.ProductInfo 按主键id查询商品
      * @param pid 主键id
     */
    ProductInfo getById(Integer pid);

    /**
     * @return: null 更新商品
      * @param info
     */
    int update(ProductInfo info);

    int delte(Integer pid);

    /*批量删除商品*/
    int deleterBatch(String[] ids);

    /*多条件查询*/
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件查询带分页
    PageInfo splitPageVo(ProductInfoVo vo, int pageSize);
}
