package com.elasticsearch.elasticsearch.Dao;

import com.elasticsearch.elasticsearch.entity.Article;
import com.elasticsearch.elasticsearch.search.MyPage;
import com.elasticsearch.elasticsearch.search.StringSearchParam;
import org.springframework.data.domain.Page;

/**
 * @author wangxia
 * @date 2019/8/13 17:34
 * @Description:
 */
public interface ArticleDao {

    Page<Article> findQrticleByBoolQuery(Article article);

    MyPage findDataByStringQuery(StringSearchParam stringSearchParam);

}
