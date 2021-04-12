package com.darkason.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@TableName("blog")
public class Blog implements Serializable {

    @TableId
    private Integer id;

    @NotBlank(message = "博客内容不能为空")
    private String content;

    private Long userId;

    private Integer type;

    private Integer blogId;

    private Date createTime;

    private Integer isActive;

    private Integer isDelete;

    private Date deleteTime;

    private Date updateTime;

    private String memoA;

    //用户名
    @TableField(exist = false)
    private String userName;

    //昵称
    @TableField(exist = false)
    private String nickName;

    //用户头像url
    @TableField(exist = false)
    private String headerImg;

    @TableField(exist = false)
    private List<String> imgUrls;

    //转发的微博的存储
    @TableField(exist = false)
    private Map<String, Object> otherMap;

    //后端管理微博列表时需要使用到
    @TableField(exist = false)
    private Integer commentTotal = 0;

    @TableField(exist = false)
    private Integer praiseTotal = 0;

    @TableField(exist = false)
    private Integer collectTotal = 0;

    //临时高亮显示的内容
    @TableField(exist = false)
    private String highContent;

    @TableField(exist = false)
    private String blogImageIds;

}
