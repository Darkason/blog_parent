package com.darkason.admin.client.impl;

import com.darkason.admin.client.EmailClient;
import com.darkason.admin.entity.EmailEntity;
import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailClientFallback implements EmailClient {

    Logger logger = LoggerFactory.getLogger(EmailClientFallback.class);


    @Override
    public Result sendEmail(EmailEntity emailEntity) {
        logger.error("远程调用Email发送邮件服务发生熔断！");
        return new Result(StatusEnum.FAIL);
    }
}
