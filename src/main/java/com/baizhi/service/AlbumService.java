package com.baizhi.service;

import com.baizhi.entity.Album;
import com.sun.org.apache.bcel.internal.generic.ALOAD;

import java.util.Map;

public interface AlbumService {
    public Map<String,Object> findAll(Integer page,Integer rows);
    //添加
    public String add(Album album);
    //更新
    public void upload(Album album);
}
