package com.elasticsearch.elasticsearch.service.impl;

import com.elasticsearch.elasticsearch.Dao.ArticleDao;
import com.elasticsearch.elasticsearch.Dao.ElasticSearchDao;
import com.elasticsearch.elasticsearch.entity.Article;
import com.elasticsearch.elasticsearch.response.ArticleRepository;
import com.elasticsearch.elasticsearch.search.MyPage;
import com.elasticsearch.elasticsearch.search.StringSearchParam;
import com.elasticsearch.elasticsearch.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/12 17:22
 * @Description:
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ElasticSearchDao elasticSearchDao;

    @Autowired
    private ArticleDao articleDao;


    @Override
    public void save(Article article) {
        //自带保存
//        articleRepository.save(article);
        //ElasticsearchTemplate的保存
        elasticSearchDao.save("blog3",article);
    }

    public String createIndex(){
        return elasticSearchDao.createIndex(Article.class.getSimpleName().toLowerCase(),Article.class);
    }

    @Override
    public MyPage findDataByStringQuery(StringSearchParam stringSearchParam){
        try {
            return articleDao.findDataByStringQuery(stringSearchParam);
        }catch (Exception e){
            log.error("查询出错",e);
            return  null;
        }

    }

    @Override
    public Page<Article> findQrticleByBoolQuery(Article article) {
        return  articleDao.findQrticleByBoolQuery(article);
    }

    @Override
    public List<Article> findByTitle(Article article){
        return articleRepository.findByTitle(article);
    }
}
