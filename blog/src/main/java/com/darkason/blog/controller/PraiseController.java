package com.darkason.blog.controller;

import com.darkason.blog.service.PraiseService;
import com.darkason.blog.threads.PraiseThread;
import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("blog/praise")
public class PraiseController {
    Logger logger = LoggerFactory.getLogger(PraiseController.class);

    @Resource
    private PraiseService praiseService;

    @RequestMapping(value = "praiseBlog", method = RequestMethod.POST)
    public Result praiseBlog(Long userId, Long blogId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(blogId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            praiseService.praiseBlog(userId, blogId);
        } catch (Exception e) {
            logger.error("点赞用户博客发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    //博客点赞
    //模拟高并发：方式一CountDownLatch
    @RequestMapping(value = "praiseBlog2", method = RequestMethod.POST)
    public Result praiseBlog2(Long userId, Long blogId) {
        Result result = new Result(StatusEnum.SUCCESS);

        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 1000; i++) {
            PraiseThread praiseThread = new PraiseThread(countDownLatch, praiseService, userId, blogId);
            Thread thread = new Thread(praiseThread);
            thread.start();
        }
        countDownLatch.countDown();
        return result;
    }

}
