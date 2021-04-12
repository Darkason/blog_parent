package com.darkason.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BlogImage implements Serializable {

    private Long id;

    private Long blogId;

    private String name;

    private String size;

    private String suffix;

    private String fileUrl;

    private Integer isActive = 1;

    private Date createTime;

}
