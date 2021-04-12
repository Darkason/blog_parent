package com.darkason.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

//    Hibernate Validator提供的校验注解：
//    @Null 被注释的元素必须为 null
//    @NotNull 被注释的元素必须不为 null
//    @AssertTrue 被注释的元素必须为 true
//    @AssertFalse 被注释的元素必须为 false
//    @Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
//    @Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
//    @DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
//    @DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
//    @Size(max=, min=) 被注释的元素的大小必须在指定的范围内
//    @Digits (integer, fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
//    @Past 被注释的元素必须是一个过去的日期
//    @Future 被注释的元素必须是一个将来的日期
//    @Pattern(regex=,flag=) 被注释的元素必须符合指定的正则表达式
//    @NotBlank(message =) 验证字符串非null，且长度必须大于0
//    @Email 被注释的元素必须是电子邮箱地址
//    @Length(min=,max=) 被注释的字符串的大小必须在指定的范围内
//    @NotEmpty 被注释的字符串的必须非空
//    @Range(min=,max=,message=) 被注释的元素必须在合适的范围内

    @TableId
    private Long userId;

    @NotBlank(message = "账号不能为空")
    private String username;

    @NotBlank(message = "昵称不能为空")
    private String name;

    private String password;

    private String salt;

    @NotBlank
    @Email(message = "请输入正确的邮箱地址")
    private String email;

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    private Integer status;

    //身份类型-0为前端注册上来的普通大众;1-为后端管理平台新增的人员
    private Integer type = 0;

    private Date createTime;

    private Long deptId;

    private String imgUrl;

    //@TableField：字段属性不为数据库表字段，但又是必须使用的
    @TableField(exist = false)
    private List<Long> roleIdList;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private List<Long> postIdList;

    @TableField(exist = false)
    private String postName;
}
