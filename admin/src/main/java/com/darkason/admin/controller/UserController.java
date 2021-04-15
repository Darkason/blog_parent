package com.darkason.admin.controller;

import com.darkason.admin.entity.User;
import com.darkason.admin.service.UserService;
import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin/user")
@RefreshScope //刷新配置
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Value("${test.name}")
    private String name;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        Result result = new Result(StatusEnum.SUCCESS);
        try {
            if (!userService.add(user)) {
                result = new Result(StatusEnum.FAIL);
            }
        } catch (Exception e) {
            logger.error("添加用户发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    public Result deleteById(Long userId) {
        System.out.println("配置文件中的test.name = " + name);
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            if (!userService.deleteById(userId)) {
                result = new Result(StatusEnum.FAIL);
            }
        } catch (Exception e) {
            logger.error("删除用户发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    //返回用户基本信息，博客数量，点赞数量，关注数量（远程调用）
    @RequestMapping(value = "getByUserId", method = RequestMethod.POST)
    public Result getByUserId(Long userId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            result.setData(userService.getByUserId(userId));
        } catch (Exception e) {
            logger.error("获取用户详情发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    //缓存穿透（缓存和数据库中都必不存在的数据，如userId=-1的数据）
    @ApiOperation("获取用户详情2")
    @RequestMapping(value = "getById2", method = RequestMethod.POST)
    public Result getById2(Long userId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            result.setData(userService.getUserById2(userId));
        } catch (Exception e) {
            logger.error("获取用户详情2发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    //缓存击穿是指某个热点key在失效的瞬间（一般是缓存时间到期），持续的大并发请求穿破缓存，直接打到数据库
    @ApiOperation("获取用户详情4")
    @RequestMapping(value = "getById4", method = RequestMethod.POST)
    public Result getById4(Long userId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            result.setData(userService.getUserById4(userId));
        } catch (Exception e) {
            logger.error("获取用户详情4发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
