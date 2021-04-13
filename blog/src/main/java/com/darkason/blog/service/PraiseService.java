package com.darkason.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.darkason.blog.entity.Praise;

public interface PraiseService extends IService<Praise> {

    Boolean praiseBlog(Long userId, Long blogId);

    Boolean praiseBlog2(Long userId, Long blogId);

}
