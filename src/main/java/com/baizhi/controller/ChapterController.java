package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.Star;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.ChainedPersistenceExceptionTranslator;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("selectAll")
    @ResponseBody
    public Map<String,Object> selectAll(String albumId,Integer page,Integer rows){
        Map<String, Object> map = chapterService.selectAll(albumId, page, rows);
        return map;
    }
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(String oper, String albumId, Chapter chapter){
       Map<String, Object> map = new HashMap<>();
        if("add".equals(oper)){
            try{

                String id = chapterService.add(albumId, chapter);

                map.put("status",true);
                map.put("message",id);
                System.out.println(id);
            }catch(Exception e){
                e.printStackTrace();
                map.put("status",false);
                map.put("message",e.getMessage());
            }
        }

        return map;

    }
    @RequestMapping("upload")
    @ResponseBody
    public Map<String,Object> upload(String id, MultipartFile name, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
//        处理文件上传
            File file = new File(request.getServletContext().getRealPath("/back/album/music"), name.getOriginalFilename());
            name.transferTo(file);
//        修改文件名称
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setName(name.getOriginalFilename());
//            文件大小
            BigDecimal size = new BigDecimal(name.getSize());
            BigDecimal mod = new BigDecimal(1024);
            BigDecimal realSize = size.divide(mod).divide(mod).setScale(2, BigDecimal.ROUND_HALF_UP);
            chapter.setSize(realSize+"MB");

            Encoder encoder = new Encoder();
            long duration = encoder.getInfo(file).getDuration();
            chapter.setDuration(duration/1000/60+":"+duration/1000%60);
            chapterService.update(chapter);
//        修改专辑中的数量

            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
