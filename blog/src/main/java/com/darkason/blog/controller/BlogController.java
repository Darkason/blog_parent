package com.darkason.blog.controller;

import com.darkason.blog.service.BlogService;
import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("blog/blog")
public class BlogController {
    Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Resource
    private BlogService blogService;

    @RequestMapping(value = "deleteByUserId", method = RequestMethod.POST)
    public Result deleteByUserId(Long userId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            blogService.deleteByUserId(userId);
        } catch (Exception e) {
            logger.error("删除用户博客发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    //根据userId获取博客数量
    @RequestMapping(value = "getBlogNumByUserId", method = RequestMethod.POST)
    public Result getBlogNumByUserId(Long userId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            result.setData(blogService.getBlogNumByUserId(userId));
        } catch (Exception e) {
            logger.error("获取用户博客数量发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
