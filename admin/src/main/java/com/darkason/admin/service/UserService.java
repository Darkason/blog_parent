package com.darkason.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.darkason.admin.entity.User;
import com.darkason.common.response.Result;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

public interface UserService extends IService<User> {

    boolean add(User user);

    boolean deleteById(Long userId);

    Map<String,Object> getByUserId(Long userId);

    Map<String, Object> getUserById2(Long userId);

    Map<String, Object> getUserById4(Long userId);

    Result uploadBlogPic(MultipartHttpServletRequest request);

    User login(String username, String password);
}
