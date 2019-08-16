package com.elasticsearch.elasticsearch.response;

import com.elasticsearch.elasticsearch.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/12 17:18
 * @Description:
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, Integer> {

    List<Article> findByTitle(Article article);

}
