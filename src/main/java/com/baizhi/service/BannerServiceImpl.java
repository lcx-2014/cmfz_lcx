package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> findAll(Integer page, Integer rows) {
        int start = (page-1)*rows;
        Banner banner = new Banner();
        RowBounds rowBounds = new RowBounds(start,rows);
        bannerDao.selectByRowBounds(banner,rowBounds);
        return  bannerDao.selectByRowBounds(banner,rowBounds);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findTotalCounts() {
        Banner banner = new Banner();
        Integer i = bannerDao.selectCount(banner);

        return i;
    }

    @Override
    public String add(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreateDate(new Date());
        int i = bannerDao.insertSelective(banner);
        if(i == 0){
            throw new RuntimeException("添加失败");
        }
        return banner.getId();
    }

    @Override
    public void update(Banner banner) {
        if("".equals(banner.getCover())){
            banner.setCover(null);
        }
        try {
            bannerDao.updateByPrimaryKeySelective(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public void delete(Banner banners, HttpServletRequest request) {
        String id = banners.getId();
        Banner banner = bannerDao.selectByPrimaryKey(id);
        int i = bannerDao.deleteByPrimaryKey(id);
        if(i==1){
            String cover = banner.getCover();
            String realPath = request.getServletContext().getRealPath(cover);
            File file = new File(request.getServletContext().getRealPath("/back/banner/image/"), cover);
            boolean delete = file.delete();
            if(delete==false){
                throw new RuntimeException("删除文件失败");
            }
        }else {
            throw new RuntimeException("删除图片失败");
        }

    }


}
