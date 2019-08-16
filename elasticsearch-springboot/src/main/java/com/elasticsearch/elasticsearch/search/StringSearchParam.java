package com.elasticsearch.elasticsearch.search;

import lombok.Data;

import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/14 9:45
 * @Description:
 */
@Data
public class StringSearchParam {

    //每页大小
    private Integer pageSize;

    //当前页
    private Integer pageNum;

    //查询条件
    private String query;

    //数据库索引
    private List<String> indexs;

    //类型
    private String type;


    //sort
    private List<SortRole> sortRoles;


    //是否开启查询缓存
    private boolean requestCache;

    //聚合规则
    private List<AggRole> aggRoles;

    //是否需要进行类型转化  为空的话则不进行类型转化
    private Class clazz;

}
