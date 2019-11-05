package com.baizhi.controller;

import ch.qos.logback.core.rolling.helper.SizeAndTimeBasedArchiveRemover;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("star")
public class StarController {
    @Autowired
    private StarService starService;
    @RequestMapping("selectAll")
    @ResponseBody
    public Map<String,Object> selectAll(Integer page,Integer rows){
        Map<String, Object> map = starService.selectAll(page, rows);
        return map;
    }
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(String oper,Star star){
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)){
                String id = starService.edit(star);
                map.put("status",true);
                map.put("message",id);
            }
        }catch (Exception e){
            map.put("status",false);
        }

        return map;
    }
    @RequestMapping("upload")
    @ResponseBody
    public Map<String,Object> upload(String id, MultipartFile photo, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try{
            photo.transferTo(new File(request.getServletContext().getRealPath("/back/star/image"),photo.getOriginalFilename()));
            Star star = new Star();
            star.setId(id);
            star.setPhoto(photo.getOriginalFilename());
            starService.upload(star);
            map.put("status",true);
        }catch(Exception e){
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("findAll")
    public void  findAll(HttpServletResponse response) throws IOException {
        List<Star> list = starService.findAll();
        String str = "<select>";
        for (Star star : list) {
            str += "<option value="+star.getId()+">"+star.getNickname()+"</option>";

        }
        str += "</select>";
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(str);

    }


}
