package com.darkason.blog.threads;

import com.darkason.blog.service.PraiseService;

import java.util.concurrent.CountDownLatch;

public class PraiseThread implements Runnable {

    private CountDownLatch countDownLatch;

    private PraiseService praiseService;

    //业务代码
    private Long userId;
    private Long blogId;

    @Override
    public void run() {
        try {
            countDownLatch.await();
            praiseService.praiseBlog2(userId,blogId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public PraiseThread() {
    }

    public PraiseThread(CountDownLatch countDownLatch, PraiseService praiseService, Long userId, Long blogId) {
        this.countDownLatch = countDownLatch;
        this.praiseService = praiseService;
        this.userId = userId;
        this.blogId = blogId;
    }
}
