package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectUsersByStarId(String starId, Integer page, Integer rows) {
        User user = new User();
        user.setStarId(starId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        User user = new User();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public List<User> export() {
        return userDao.selectAll();
    }

    @Override
    public void update(User user) {
        int i = userDao.updateByPrimaryKeySelective(user);
        if(i==0){
            throw new RuntimeException("更新失败");
        }

    }

    @Override
    public void del(String id) {
        int i = userDao.deleteByPrimaryKey(id);
        if(i==0){
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public List<Users> findAllGroupBy() {
        List<Users> allGroupBy = userDao.findAllGroupBy();
        return allGroupBy;
    }

    @Override
    public List<Users> selectAllDroupBy() {
        List<Users> allGroup = userDao.findAllGroup();
        return allGroup;
    }

    @Override
    public String add(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreateDate(new Date());
        int i = userDao.insertSelective(user);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return user.getId();
    }

}
