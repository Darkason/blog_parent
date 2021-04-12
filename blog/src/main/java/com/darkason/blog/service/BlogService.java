package com.darkason.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.darkason.blog.entity.Blog;

public interface BlogService extends IService<Blog> {
    void deleteByUserId(Long userId);

    int getBlogNumByUserId(Long userId);
}
