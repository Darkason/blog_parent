package com.darkason.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.darkason.admin.entity.User;
import feign.Param;

public interface UserDao extends BaseMapper<User> {

    User getByUsername(@Param("username") String username);

}
