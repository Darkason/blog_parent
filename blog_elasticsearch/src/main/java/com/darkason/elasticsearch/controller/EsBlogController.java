package com.darkason.elasticsearch.controller;

import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import com.darkason.common.utils.ValidatorUtil;
import com.darkason.elasticsearch.entity.EsBlog;
import com.darkason.elasticsearch.service.EsBlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("elasticsearch/blog")
public class EsBlogController {
    Logger logger = LoggerFactory.getLogger(EsBlogController.class);

    @Resource
    private EsBlogService esBlogService;

    //查询博客（elasticsearch）
    @RequestMapping(value = "listBlog", method = RequestMethod.GET)
    public Result listBlog(String content, Integer page) {
        Result result = new Result(StatusEnum.SUCCESS);
        page = page == null ? 0 : page;
        try {
            //普通查询
//            result.setData(esBlogService.listBlog(content,page));
            //高亮查询
            result.setData(esBlogService.listBlog2(content, page));
        } catch (Exception e) {
            logger.error("查询博客发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    //添加博客
    @RequestMapping(value = "addBlog", method = RequestMethod.POST)
    public Result addBlog(@RequestBody @Validated EsBlog esBlog, BindingResult bindingResult) {
        Result result = new Result(StatusEnum.SUCCESS);
        String res = ValidatorUtil.checkResult(bindingResult);
        if (!StringUtils.isEmpty(res)) {
            return new Result(StatusEnum.PARAM_EXCEPTION, res);
        }
        try {
            //第一步：添加到mysql
            //第二步：添加到elasticsearch
            esBlogService.add(esBlog);
        } catch (Exception e) {
            logger.error("添加博客发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
