package com.lck.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lck.mapper.ProductInfoMapper;
import com.lck.pojo.ProductInfo;
import com.lck.pojo.vo.ProductInfoVo;
import com.lck.service.ProductInfoService;
import com.lck.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/***
 #Create by LCK on 2022/1/17  17:24
 */
@Controller
@RequestMapping("/prod")
public class ProduceInfoController {
    //异步上传文件图片名称
    String saveFileName = "";
    //每页显示的记录数
    public static final int PAGE_SIZE = 5;

    @Autowired
    private ProductInfoService productInfoService;

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(Model model) {

        List<ProductInfo> list = productInfoService.getAll();

        model.addAttribute("list", list);

        return "product";
    }

    //显示第一页5条记录
    @RequestMapping("/split")
    public String split(HttpServletRequest request) {
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if (vo != null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
        }else {
            info = productInfoService.splitPage(1, PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }
        //得到第一页的数据
        request.setAttribute("info", info);
        return "product";
    }

    //ajax分页翻页处理
    @RequestMapping("/ajaxsplit")
    @ResponseBody
    public void ajaxSplit(ProductInfoVo vo, HttpSession session) {
        //取得当前参数page参数的页面的数据
        PageInfo info = productInfoService.splitPageVo(vo,PAGE_SIZE);
        System.out.println(info);
        //放在session域中，当你翻页的时候，取得值是session中的数据
        session.setAttribute("info", info);
    }

    //异步ajax处理文件上传
    @ResponseBody
    @RequestMapping("/ajaxImg")    //MultipartFile 用于文件上传的接受
    public Object jajaxImg(MultipartFile pimage, HttpServletRequest request) {  //pimage 是前端上传按钮的name
        //提取生成文件名UUID+上传文件后缀。                                                获取文件上传后缀名
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储的路径。
        String path = request.getServletContext().getRealPath("/image_big");
        //转存
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //以上为上传功能
        //返回客户端json对象，封装图片的路径，为了在页面实现立即回显。 添加 org.json依赖
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);
        return object.toString();
    }

    //增加功能
    @RequestMapping("/save")
    public String save(ProductInfo info, Model model) {
        //保存功能需要获取图片名称，但是图片名称在上传图片的方法里面
        info.setpImage(saveFileName);
        info.setpDate(new Date()); //上传的日期
        //info对下个你中有表单提交上来的五个数据，有异步ajax上来的图片名称数据，
        int num = -1;
        try {
            num = productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            model.addAttribute("msg", "增加成功");
        } else {
            model.addAttribute("msg", "增加失败");
        }
        //增加成功后 ，应该重新访问数据库，所以跳转到分页显示
        //清空saveFileName内容，为了下次增加或修改异步ajax  比如说你先点击添加操作，添加了图片，此时退出，取点击更新，里面有新增的图片的名称，但是此时与
        //更新操作的图片内容以不一致
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(Integer pid,ProductInfoVo vo, Model model,HttpSession session) {
        ProductInfo info = productInfoService.getById(pid);
        model.addAttribute("prod", info);
        //将多条件以及页码放入session中，更新处理结束后读取条件和页码进行处理
        session.setAttribute("prodVo", vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info, Model model) {
        //处理图片
        /*因为异步ajax的异步图片 如果有上传过， 则saveFileName李有上传过来的图片名称（比如先点击新增 退出 点击更新）
         * 如果没有使用异步ajax上传过图片，则saveFilName =  “” ，
         * 实体类info使用隐藏表单域提供的pImage图片，继续使用以前的图片，即不更新图潘*/
        if (!saveFileName.equals("")) {
            info.setpImage(saveFileName);
        }
        //完成更新操作
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            model.addAttribute("msg", "更新成功");
        } else {
            model.addAttribute("msg", "更新失败");
        }
        //处理完更新，saveFileName里面有可能有数据，而下一次更新还会使用这次的图片，所以需要清空图片
        saveFileName = "";
        //跳转到分页
        return "forward:/prod/split.action";
    }

    //删除
    @RequestMapping("delete")
    public String delete(Integer pid,ProductInfoVo vo, HttpServletRequest request) {
        int num = -1;
        try {
            num = productInfoService.delte(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg", "删除成功");
            request.getSession().setAttribute("deleteprodVo", vo);
        } else {
            request.setAttribute("msg", "删除失败");
        }
        //删除结束后跳到分页显示。
        return "forward:/prod/deleteAjaxSplit.action";
    }

    //删除的分页显示
    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjasSplit(HttpServletRequest request) {
        //取得第一个数据
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteprodVo");
        if (vo!=null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
        }else {
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info", info);
        return request.getAttribute("msg");
    }


    //批量删除商品
    @RequestMapping("/deleteBatch")
    //批量删除 pids=“1，4，5” 等
    public String deleteBatch(String pids,Model model){
        //将上传的数组字符串 形成id数组
        String[] ps = pids.split(",");


        try {
            int num = productInfoService.deleterBatch(ps);
            if (num>0){
                model.addAttribute("msg", "批量删除成功");
            }else {
                model.addAttribute("msg", "批量删除失败");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "商品不可删除");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    /*多条件查询*/
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list", list);

    }
}
