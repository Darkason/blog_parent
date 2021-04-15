package com.darkason.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "blogindex")
public class EsBlog implements Serializable {

    @Id
    private Long id;

//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
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

}
