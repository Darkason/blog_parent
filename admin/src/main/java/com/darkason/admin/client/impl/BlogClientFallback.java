package com.darkason.admin.client.impl;

import com.darkason.admin.client.BlogClient;
import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BlogClientFallback implements BlogClient {

    Logger logger = LoggerFactory.getLogger(BlogClientFallback.class);

    @Override
    public Result deleteByUserId(Long userId) {
        logger.error("远程调用Blog根据用户Id删除服务发生熔断！");
        return new Result(StatusEnum.FAIL);
    }

    @Override
    public Result getBlogNumByUserId(Long userId) {
        logger.error("远程调用Blog根据用户Id查询博客数量服务发生熔断！");
        return null;
    }
}
