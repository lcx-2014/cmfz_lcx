package com.baizhi.service;

import com.baizhi.entity.Star;

import java.util.List;
import java.util.Map;

public interface StarService {
    public Map<String,Object> selectAll(Integer page,Integer rows);
    public String edit(Star star);
    public void upload(Star star);
    public List<Star> findAll();
}
