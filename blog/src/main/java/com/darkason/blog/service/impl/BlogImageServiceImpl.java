package com.darkason.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darkason.blog.dao.BlogImageDao;
import com.darkason.blog.entity.BlogImage;
import com.darkason.blog.service.BlogImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogImageServiceImpl extends ServiceImpl<BlogImageDao, BlogImage> implements BlogImageService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addBlogImageList(List<BlogImage> list) {
        this.saveBatch(list);

        StringBuffer blogImageIds = new StringBuffer();
        for (BlogImage blogImage : list) {
            blogImageIds.append(blogImage.getId() + ",");
        }
        return blogImageIds.substring(0, blogImageIds.length() - 1);
    }
}
