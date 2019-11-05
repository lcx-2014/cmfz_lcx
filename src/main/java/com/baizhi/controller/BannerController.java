package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("findAll")
    @ResponseBody
    //分页查询{"page"当前页,"total"总页数,"records":总条数,"rows":[当前页查询数据]}
    public Map<String,Object> findAll(Integer page, Integer rows){
        List<Banner> list = bannerService.findAll(page, rows);
        Integer totalCounts = bannerService.findTotalCounts();
        HashMap<String, Object> result = new HashMap<>();
        result.put("rows",list);//当前页集合
        result.put("page",page);
        Integer totalPage = totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        result.put("total",totalPage);
        result.put("records",totalCounts);
        return result;
    }
    //轮播图的 修改 删除 和 保存操作
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(String oper,Banner banner,HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try{
            if("add".equals(oper)){
                String id = bannerService.add(banner);
                map.put("status",true);
                map.put("message",id);
            }
            if("edit".equals(oper)){
                bannerService.update(banner);
                map.put("status",true);
            }
            if("del".equals(oper)){
                bannerService.delete(banner,request);
                map.put("status",true);

            }
        }catch(Exception e){
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;

    }
    @ResponseBody
    @RequestMapping("upload")
    public Map<String,Object> upload(String id,MultipartFile cover,HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();

        try {
//            文件上传
            cover.transferTo(new File(request.getServletContext().getRealPath("/back/banner/image"),cover.getOriginalFilename()));
//            修改banner对象中cover属性
            Banner banner = new Banner();
            banner.setId(id);
            banner.setCover(cover.getOriginalFilename());
            bannerService.update(banner);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }

}
