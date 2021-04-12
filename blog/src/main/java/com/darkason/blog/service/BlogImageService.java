package com.darkason.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.darkason.blog.entity.BlogImage;

import java.util.List;

public interface BlogImageService extends IService<BlogImage> {

    String addBlogImageList(List<BlogImage> list);

}
