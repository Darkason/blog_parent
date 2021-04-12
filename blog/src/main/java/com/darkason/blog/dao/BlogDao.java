package com.darkason.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.darkason.blog.entity.Blog;
import org.apache.ibatis.annotations.Param;

public interface BlogDao extends BaseMapper<Blog> {
    void deleteByUserId(@Param("userId") Long userId);

    int getBlogByUserId(@Param("userId") Long userId);
}
