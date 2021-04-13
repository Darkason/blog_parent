package com.darkason.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darkason.blog.dao.PraiseDao;
import com.darkason.blog.entity.Praise;
import com.darkason.blog.service.PraiseService;
import com.darkason.common.constants.RedisConstant;
import com.darkason.common.utils.SnowflakeUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PraiseServiceImpl extends ServiceImpl<PraiseDao, Praise> implements PraiseService {

    @Resource
    private PraiseDao praiseDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)

    public Boolean praiseBlog(Long userId, Long blogId) {
        List<Praise> praiseByUserIdAndBlogId = praiseDao.getPraiseByUserIdAndBlogId(userId, blogId);
        if (praiseByUserIdAndBlogId != null && praiseByUserIdAndBlogId.size() > 0) {
            return false;
        }
        Praise praise = new Praise();
        praise.setId(new SnowflakeUtil().nextId());
        praise.setUserId(userId);
        praise.setBlogId(blogId);
        praise.setPraiseTime(new Date());
        boolean b = this.save(praise);
        return b;
    }

//    分布式锁实现方式：1数据库乐观锁；2基于Redis的分布式锁；3基于redisson；4基于ZooKeeper
//    分布式锁同时满足以下四个条件：
//    互斥性。在任意时刻，只有一个客户端能持有锁。
//    不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
//    具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
//    解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean praiseBlog2(Long userId, Long blogId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String key = RedisConstant.STRING_BLOCK + userId;
        String value = UUID.randomUUID().toString() + System.nanoTime();
        //setIfAbsent方法，就是当key不存在的时候，创建key和value，并且该方法可以设置键的过期时间
        //至于设置多少的过期时间合适，这个是没有定论的，需要根据真是的业务场景来衡量。
        Boolean setIfAbsent = valueOperations.setIfAbsent(key, value);
        //valueOperations.setIfAbsent(key,value,10, TimeUnit.SECONDS);
        try {
            if (setIfAbsent) {
                System.out.println("分布式锁不存在");
                //分布式锁不存在
                List<Praise> praiseByUserIdAndBlogId = praiseDao.getPraiseByUserIdAndBlogId(userId, blogId);
                if (praiseByUserIdAndBlogId.size() > 0 && praiseByUserIdAndBlogId != null) {
                    return false;
                }
                Praise praise = new Praise();
                praise.setId(new SnowflakeUtil().nextId());
                praise.setUserId(userId);
                praise.setBlogId(blogId);
                praise.setPraiseTime(new Date());
                boolean b = this.save(praise);
                return b;
            } else {
                //锁已经存在
                System.out.println("锁已经存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //删除自己的锁
            if (value.equals(valueOperations.get(key))) {
                redisTemplate.delete(key);
            }
        }
        return true;
    }

}
