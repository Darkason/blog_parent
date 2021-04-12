package com.darkason.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darkason.blog.dao.BlogDao;
import com.darkason.blog.entity.Blog;
import com.darkason.blog.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {

    @Resource
    private BlogDao blogDao;

    @Override
    public void deleteByUserId(Long userId) {
        blogDao.deleteByUserId(userId);
    }

    @Override
    public int getBlogNumByUserId(Long userId) {
        return blogDao.getBlogByUserId(userId);
    }
}
