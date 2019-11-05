package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BannerService {
    public List<Banner> findAll(Integer page,Integer rows);
    public Integer  findTotalCounts();
    //添加
    public String add(Banner banner);
    //修改
    public void update(Banner banner);
    //删除
    public void delete(Banner banners, HttpServletRequest request);
}
