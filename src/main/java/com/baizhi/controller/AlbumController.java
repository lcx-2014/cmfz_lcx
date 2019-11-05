package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @RequestMapping("findAll")
    @ResponseBody
    public Map<String,Object> findAll(Integer page,Integer rows){
        Map<String, Object> map = albumService.findAll(page, rows);
        return  map;
    }
    @ResponseBody
    @RequestMapping("edit")
    public  Map<String,Object> edit(String oper, Album album){
        HashMap<String, Object> map = new HashMap<>();
        if("add".equals(oper)){
            try{

                String id = albumService.add(album);
                map.put("status",true);
                map.put("message",id);
            }catch (Exception e){
                map.put("status",false);
                map.put("message",e.getMessage());
            }
        }
        return map;
    }
    @RequestMapping("upload")
    @ResponseBody
    public Map<String,Object> upload(String id, MultipartFile cover, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try{
            cover.transferTo(new File(request.getServletContext().getRealPath("/back/album/image"),cover.getOriginalFilename()));
            Album album = new Album();
            album.setId(id);
            album.setCover(cover.getOriginalFilename());
            albumService.upload(album);
            map.put("status",true);
        }catch(Exception e){
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
}
