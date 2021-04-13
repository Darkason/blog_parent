package com.darkason.admin.client.impl;

import com.darkason.admin.client.ImgClient;
import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component
public class ImgClientFallback implements ImgClient {

    Logger logger = LoggerFactory.getLogger(ImgClientFallback.class);


    @Override
    public Result uploadBlogPic(MultipartHttpServletRequest request) {
        logger.error("远程调用Img上传博客图片服务发生熔断！");
        return new Result(StatusEnum.FAIL);
    }
}
