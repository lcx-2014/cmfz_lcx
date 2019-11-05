package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void login(Admin admin, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String code1 = (String)session.getAttribute("code");
        if(code1.equalsIgnoreCase(code)){
            Example example = new Example(Admin.class);
            example.createCriteria().andEqualTo("username",admin.getUsername());
            Admin loginadmin = adminDao.selectOneByExample(example);
            if(loginadmin != null){
                if(loginadmin.getPassword().equals(admin.getPassword())){
                    session.setAttribute("admin",loginadmin);
                }else{
                    throw new RuntimeException("密码错误");
                }
            }else{
                throw new RuntimeException("用户名不存在");
            }
        }else{
            throw new RuntimeException("验证码错误");
        }

    }
}
