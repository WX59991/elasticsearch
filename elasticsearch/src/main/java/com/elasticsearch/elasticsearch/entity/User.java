package com.elasticsearch.elasticsearch.entity;

import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.util.StringUtils;

/**
 * @author wangxia
 * @date 2019/8/9 16:24
 * @Description:
 */
@Data
public class User implements CommonEntity {

    private String id;


    private String userName;


    private Integer age;


    private String birthday;


    private String description;

    @Override
    public BoolQueryBuilder convertParam() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.hasText(this.getUserName())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("userName", this.getUserName()));
        }
        if (this.getAge() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("age").gt(this.getAge()));
        }
        if (StringUtils.hasText(this.getDescription())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("description", this.getDescription()));
        }

        return boolQueryBuilder;
    }
}
