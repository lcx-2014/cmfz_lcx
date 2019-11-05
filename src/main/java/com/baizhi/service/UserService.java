package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.Users;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String,Object> selectUsersByStarId(String starId,Integer page,Integer rows);
    //查询所有的用户\
    public Map<String,Object> selectAll(Integer page,Integer rows);
    public List<User> export();
    //更新
    public void  update(User user);
    //删除
    public void del(String id);
    //根据月份分组查询女生
    public List<Users> findAllGroupBy();
    //根据月份分组查询男生
    public List<Users> selectAllDroupBy();
    //添加用户
    public String add(User user);
}
