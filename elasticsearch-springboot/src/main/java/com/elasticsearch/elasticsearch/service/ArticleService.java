package com.elasticsearch.elasticsearch.service;

import com.elasticsearch.elasticsearch.entity.Article;
import com.elasticsearch.elasticsearch.search.MyPage;
import com.elasticsearch.elasticsearch.search.StringSearchParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/12 17:21
 * @Description:
 */
public interface ArticleService {

    void save(Article article);


    String createIndex();

    MyPage findDataByStringQuery(StringSearchParam stringSearchParam);

    Page<Article> findQrticleByBoolQuery(Article article);

    List<Article> findByTitle(Article article);
}
