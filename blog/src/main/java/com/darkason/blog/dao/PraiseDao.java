package com.darkason.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.darkason.blog.entity.Praise;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PraiseDao extends BaseMapper<Praise> {
    List<Praise> getPraiseByUserIdAndBlogId(@Param("userId") Long userId,@Param("blogId") Long blogId);
}
