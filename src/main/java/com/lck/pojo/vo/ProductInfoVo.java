package com.lck.pojo.vo;

/***
 #Create by LCK on 2022/1/18  22:16
 */
public class ProductInfoVo {
    //商品名称
    private String pname;
    //商品类型
    private Integer typeid;
    //最低价格
    private Integer lprice;
    //最高价格
    private Integer hprice;

    /*设置页码*/
    private Integer page = 1;

    public ProductInfoVo() {
    }

    public String getPname() {
        return pname;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "pname='" + pname + '\'' +
                ", typeid=" + typeid +
                ", lprice=" + lprice +
                ", hprice=" + hprice +
                ", page=" + page +
                '}';
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getLprice() {
        return lprice;
    }

    public void setLprice(Integer lprice) {
        this.lprice = lprice;
    }

    public Integer getHprice() {
        return hprice;
    }

    public void setHprice(Integer hprice) {
        this.hprice = hprice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ProductInfoVo(String pname, Integer typeid, Integer lprice, Integer hprice, Integer page) {
        this.pname = pname;
        this.typeid = typeid;
        this.lprice = lprice;
        this.hprice = hprice;
        this.page = page;
    }
}
