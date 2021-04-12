package com.darkason.admin.controller;

import com.darkason.admin.entity.User;
import com.darkason.admin.service.UserService;
import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import com.darkason.common.utils.JwtUtil;
import com.darkason.common.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/login")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    //登录
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(String username, String password) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            User loginUser = userService.login(username, password);
            if (loginUser == null) {
                return new Result(StatusEnum.LOGIN_EXCEPTION);
            }
            //利用jwt生成token,并返回给前端
            String token = JwtUtil.createJWT(loginUser.getUserId().toString(), loginUser.getEmail());

            Map returnMap = new HashMap();
            returnMap.put("token", token);

            result.setData(returnMap);
        } catch (Exception e) {
            logger.error("登录错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;

    }

}
