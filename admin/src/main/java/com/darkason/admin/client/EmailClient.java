package com.darkason.admin.client;

import com.darkason.admin.entity.EmailEntity;
import com.darkason.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "email")
public interface EmailClient {

    @RequestMapping(value = "email/email/sendEmail", method = RequestMethod.POST)
    Result sendEmail(@RequestBody EmailEntity emailEntity);
}
