package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    public Map<String,Object> selectAll(String albumId,Integer page,Integer rows);
    public String add(String albumId, Chapter chapter);
    public void update(Chapter chapter);
}
