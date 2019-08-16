package com.elasticsearch.elasticsearch.Dao;

import com.elasticsearch.elasticsearch.search.MyPage;
import com.elasticsearch.elasticsearch.search.StringSearchParam;

/**
 * @author wangxia
 * @date 2019/8/13 11:32
 * @Description: ES查询
 */
public interface ElasticSearchQueryDao {

    MyPage searchByStringQuery(StringSearchParam stringSearchParam)  throws Exception ;

}
