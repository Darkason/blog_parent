package com.darkason.blog.controller;

import com.darkason.blog.entity.BlogImage;
import com.darkason.blog.service.BlogImageService;
import com.darkason.blog.service.BlogService;
import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("blog/image")
public class BlogImageController {
    Logger logger = LoggerFactory.getLogger(BlogImageController.class);

    @Resource
    private BlogImageService blogImageService;

    @RequestMapping(value = "addBlogImageList", method = RequestMethod.POST)
    public Result addBlogImageList(@RequestBody List<BlogImage> list) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(list)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            result.setData(blogImageService.addBlogImageList(list));
        } catch (Exception e) {
            logger.error("添加博客图片发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
