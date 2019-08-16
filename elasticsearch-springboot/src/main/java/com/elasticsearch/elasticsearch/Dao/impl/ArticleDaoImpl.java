package com.elasticsearch.elasticsearch.Dao.impl;

import com.elasticsearch.elasticsearch.Dao.ArticleDao;
import com.elasticsearch.elasticsearch.Dao.ElasticSearchQueryDao;
import com.elasticsearch.elasticsearch.entity.Article;
import com.elasticsearch.elasticsearch.search.MyPage;
import com.elasticsearch.elasticsearch.search.StringSearchParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author wangxia
 * @date 2019/8/13 17:35
 * @Description:
 */
@Slf4j
@Component
public class ArticleDaoImpl  implements ArticleDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticSearchQueryDao elasticSearchQueryDao;

    @Override
    public Page<Article> findQrticleByBoolQuery(Article article) {
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery();
        if(!StringUtils.isEmpty(article.getTitle())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("title",article.getTitle()));
        }
        if(!StringUtils.isEmpty(article.getContent())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("content",article.getTitle()));
        }
        return elasticsearchTemplate.queryForPage(new NativeSearchQuery(boolQueryBuilder),Article.class);
    }

    @Override
    public MyPage findDataByStringQuery(StringSearchParam stringSearchParam){
        try {
            return elasticSearchQueryDao.searchByStringQuery(stringSearchParam);
        }catch (Exception e){
            log.error("查询出错",e);
            return  null;
        }

    }
}
