package com.elasticsearch.elasticsearch.entity;

import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * @author wangxia
 * @date 2019/8/12 14:21
 * @Description:
 */
public interface CommonEntity {

    BoolQueryBuilder convertParam();

}
