package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    public Map<String,Object> selectAll(Integer page,Integer rows);
    //添加
    public String add(Article article);
    //更新
    public void edit(Article article);
    //删除
    public void del(String id);
}
