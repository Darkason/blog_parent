package com.darkason.elasticsearch.service.impl;

import com.darkason.common.utils.SnowflakeUtil;
import com.darkason.elasticsearch.dao.EsBlogDao;
import com.darkason.elasticsearch.entity.EsBlog;
import com.darkason.elasticsearch.repository.EsBlogRepository;
import com.darkason.elasticsearch.service.EsBlogService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EsBlogServiceImpl implements EsBlogService {

    @Resource
    private EsBlogDao esBlogDao;

    @Resource
    private EsBlogRepository repository;

    @Override
    public List<EsBlog> listBlog(String content, Integer page) {
        List<EsBlog> blogList = new ArrayList<>();
//        Iterable<EsBlog> esBlogIterable = esBlogDao.findAll();
//        Iterator<EsBlog> esBlogIterator = esBlogIterable.iterator();
//        while (esBlogIterator.hasNext()) {
//            blogList.add(esBlogIterator.next());
//        }
        //模糊查询（巨坑，非空校验）
//        QueryBuilder qb1 = QueryBuilders.queryStringQuery(content).field("content");
//        Iterable<EsBlog> aIterable = repository.search(qb1);
//        for (EsBlog esBlog : aIterable) {
//            blogList.add(esBlog);
//        }

        //模糊查询方法二
        // Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //创建Java查询 Api
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //创建filter，使用filter将所有条件串联起来
        List<QueryBuilder> filter = builder.filter();

        if (StringUtils.isNotBlank(content)) {
            filter.add(QueryBuilders.termQuery("content", content));
        }

        nativeSearchQueryBuilder.withQuery(builder);
        //分页查询（SpringDataJpa是从0开始的）
        page = page == null ? 1 : page;
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page-1, 2));
        //排序
        nativeSearchQueryBuilder.withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC));

        Page<EsBlog> pageResult = this.esBlogDao.search(nativeSearchQueryBuilder.build());

        for (EsBlog esBlog : pageResult) {
            blogList.add(esBlog);
        }

        return blogList;
    }

    @Override
    public void add(EsBlog esBlog) {
        esBlog.setId(new SnowflakeUtil().nextId());
        esBlog.setCreateTime(new Date());
        esBlog.setUserId(1L);
        esBlog.setIsActive(1);
        esBlog.setIsDelete(0);
        esBlogDao.save(esBlog);
    }
}
