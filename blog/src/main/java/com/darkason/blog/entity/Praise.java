package com.darkason.blog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class Praise implements Serializable {

    private Long id;

    private Long blogId;

    private Long userId;

    private Date praiseTime;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
