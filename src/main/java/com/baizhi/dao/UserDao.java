package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserDao extends Mapper<User> {
    //根据月份分组查询女生
    public List<Users> findAllGroupBy();
    public List<Users> findAllGroup();
}
