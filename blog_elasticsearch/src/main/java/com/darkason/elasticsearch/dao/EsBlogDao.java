package com.darkason.elasticsearch.dao;

import com.darkason.elasticsearch.entity.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

//ElasticsearchRepository<T , ID> 对应实体和ID的数据类型
@Repository
public interface EsBlogDao extends ElasticsearchRepository<EsBlog, Long> {

}
