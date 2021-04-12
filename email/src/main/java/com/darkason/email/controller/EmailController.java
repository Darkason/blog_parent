package com.darkason.email.controller;

import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import com.darkason.email.entity.EmailEntity;
import com.darkason.email.service.impl.EmailServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("email/email")
public class EmailController {

    @Resource
    private EmailServiceImpl emailService;

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public Result sendEmail(@RequestBody EmailEntity emailEntity) {
        Result result = new Result(StatusEnum.SUCCESS);
        try {
            emailService.sendEmail(emailEntity);
        } catch (Exception e) {
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
