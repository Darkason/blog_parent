package com.darkason.elasticsearch.repository;

import com.darkason.elasticsearch.entity.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,String> {
}
