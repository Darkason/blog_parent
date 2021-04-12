package com.darkason.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darkason.admin.client.BlogClient;
import com.darkason.admin.client.EmailClient;
import com.darkason.admin.client.ImgClient;
import com.darkason.admin.dao.UserDao;
import com.darkason.admin.entity.EmailEntity;
import com.darkason.admin.entity.User;
import com.darkason.admin.service.UserService;
import com.darkason.admin.utils.ShiroUtil;
import com.darkason.common.constants.Constant;
import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import com.darkason.common.utils.SnowflakeUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private EmailClient emailClient;

    @Resource
    private BlogClient blogClient;

    @Resource
    private ImgClient imgClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(User user) {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);

        //雪花算法
        newUser.setUserId(new SnowflakeUtil().nextId());

        //设置默认密码
        //加密密码串
        //org.apache.commons.lang3.RandomStringUtils
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String password = StringUtils.isEmpty(user.getPassword()) ? Constant.defaultPassword : user.getPassword();
        newUser.setPassword(ShiroUtil.sha256(password, salt));
        newUser.setSalt(salt);
        newUser.setStatus(1);
        newUser.setType(1);
        newUser.setCreateTime(new Date());
        newUser.setImgUrl("");
        boolean b = this.save(newUser);
        //TODO
        //把账号和邮箱存入Redis中（注册账号和邮箱不能重复）

        //方式一：注册成功后发送邮件(远程调用)
        //方式二：rabbitmq

        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setTo(new String[]{user.getEmail()});
        emailEntity.setSubject("注册成功通知");
        String text = "恭喜【" + user.getName() + "】注册成功,您的密码是：" + password;
        emailEntity.setText(text);
        Result result = emailClient.sendEmail(emailEntity);
        System.out.println(result.getCode());
        if (!result.getCode().equals(StatusEnum.SUCCESS.getCode())) {
            throw new RuntimeException("发送邮件失败");
        }
        return b;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setStatus(0);
        //物理删除
        //this.deleteById(userId);

        //逻辑删除
        // update sys_user set status=0 where user_id = #{userId}
        boolean b = this.updateById(user);

        //远程调用blog,删除该用户的博客，点赞，转发等。
        Result result = blogClient.deleteByUserId(userId);
        if (!result.getCode().equals(StatusEnum.SUCCESS.getCode())) {
            throw new RuntimeException("删除用户博客失败！");
        }

        // TODO
        //问题： 调用方发生异常回滚，服务方未发生回滚
        //解决方案： 分布式事务
        //效果： 调用方如果发生异常回滚，服务方也要同时发生回滚
        //int a = 1 / 0;
        return b;
    }

    //返回用户基本信息，博客数量，点赞数量，关注数量（远程调用）
    @Override
    public Map<String, Object> getByUserId(Long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", this.getById(userId));
        Result blogNumByUserId = blogClient.getBlogNumByUserId(userId);
        if (!blogNumByUserId.getCode().equals(StatusEnum.SUCCESS.getCode())) {
            throw new RuntimeException("查询用户博客数量失败！");
        }
        map.put("blogNum", blogNumByUserId.getData());
        return map;
    }

    @Override
    public Result uploadBlogPic(MultipartHttpServletRequest request) {
        Result result = imgClient.uploadBlogPic(request);
        if (!result.getCode().equals(StatusEnum.SUCCESS.getCode())) {
            throw new RuntimeException("上传博客图片失败！");
        }
        return result;
    }

    @Override
    public User login(String username, String password) {
        User loginUser = userDao.getByUsername(username);
        if (loginUser != null) {
            String salt = loginUser.getSalt();
            if (ShiroUtil.sha256(password, salt).equals(loginUser.getPassword())) {
                return loginUser;
            }
        }
        return loginUser;
    }
}
