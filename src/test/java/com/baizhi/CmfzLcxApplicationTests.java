package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import com.baizhi.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.reactive.AbstractReactiveTransactionManager;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class CmfzLcxApplicationTests {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private StarService starService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        List<Users> users = userService.selectAllDroupBy();
        for (Users user : users) {
            System.out.println(user.getCounts());
        }
    }

}
