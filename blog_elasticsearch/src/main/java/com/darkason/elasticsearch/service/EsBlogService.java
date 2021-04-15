package com.darkason.elasticsearch.service;

import com.darkason.elasticsearch.entity.EsBlog;

import java.util.List;

public interface EsBlogService {
    List<EsBlog> listBlog(String content,Integer page);

    List<EsBlog> listBlog2(String content,Integer page);

    void add(EsBlog esBlog);
}
