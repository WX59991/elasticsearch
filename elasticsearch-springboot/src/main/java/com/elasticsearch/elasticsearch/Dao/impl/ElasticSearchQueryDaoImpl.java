package com.elasticsearch.elasticsearch.Dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.elasticsearch.elasticsearch.Dao.ElasticSearchQueryDao;
import com.elasticsearch.elasticsearch.search.AggRole;
import com.elasticsearch.elasticsearch.search.MyPage;
import com.elasticsearch.elasticsearch.search.SortRole;
import com.elasticsearch.elasticsearch.search.StringSearchParam;
import com.elasticsearch.elasticsearch.util.CollectionUtil;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/13 15:36
 * @Description:
 */
@Component
public class ElasticSearchQueryDaoImpl implements ElasticSearchQueryDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public MyPage searchByStringQuery(StringSearchParam stringSearchParam) throws Exception {
        if(CollectionUtil.isEmpty(stringSearchParam.getIndexs())){
            throw  new Exception("索引不能为空");
        }
        SearchRequestBuilder builder = elasticsearchTemplate.getClient().prepareSearch(stringSearchParam.getIndexs().toArray(new String[stringSearchParam.getIndexs().size()]));
        if(!StringUtils.isEmpty(stringSearchParam.getType())){
            builder.setSearchType(stringSearchParam.getType());
        }
        builder.setFrom(stringSearchParam.getPageNum()*stringSearchParam.getPageSize());
        builder.setSize(stringSearchParam.getPageSize());
        //添加query
        if(!StringUtils.isEmpty(stringSearchParam.getQuery())){
            builder.setQuery(QueryBuilders.queryStringQuery(stringSearchParam.getQuery()));
        }
        //添加排序
       if(!CollectionUtil.isEmpty(stringSearchParam.getSortRoles())){
            for(SortRole sortRole:stringSearchParam.getSortRoles()){
                if(!StringUtils.isEmpty(sortRole.getSortFields()) && sortRole.getSortOrder()!=null){
                    builder.addSort(sortRole.getSortFields(),sortRole.getSortOrder());
                }
            }

        }

        //添加分类统计
        if(!CollectionUtil.isEmpty(stringSearchParam.getAggRoles())){
            addAggRole(builder,stringSearchParam.getAggRoles());
        }
        //开启查询缓存
        builder.setRequestCache(stringSearchParam.isRequestCache());
        SearchResponse response=builder.get();
        MyPage myPage=new MyPage();
        myPage.setPageNo(stringSearchParam.getPageNum());
        myPage.setPageSize(stringSearchParam.getPageSize());
        return getPageResult(myPage,response,stringSearchParam.getClazz());
    }


    public void addAggRole(SearchRequestBuilder builder,List<AggRole> aggRoles){
        for(AggRole aggRole:aggRoles){
            switch (aggRole.getAvgType()){
                case "sum":
                    builder.addAggregation(AggregationBuilders.sum(aggRole.getField()));
                    break;
                case "max":
                    builder.addAggregation(AggregationBuilders.max(aggRole.getField()));
                    break;
                case "min":
                    builder.addAggregation(AggregationBuilders.min(aggRole.getField()));
                    break;
                case "count":
                    builder.addAggregation(AggregationBuilders.count(aggRole.getField()));
                    break;
                default:
                    break;
            }
        }
    }

    public MyPage getPageResult(MyPage page, SearchResponse response, Class clazz){
        if(page==null){
            page=new MyPage();
        }
        SearchHits searchHits=response.getHits();
        page.setTotal(searchHits.getTotalHits().value);
        Long total=searchHits.getTotalHits().value;
        SearchHit[] searchHits2= searchHits.getHits();
        List<Object> queryDatas=getHitsResult(clazz,searchHits2);
        page.setData(queryDatas);

        Aggregations aggregations=response.getAggregations();
        if(aggregations!=null){
            List<Aggregation> aggregationsList=aggregations.asList();
            for(Aggregation temp:aggregationsList){

            }
        }

        return  page;
    }

    public List<Object> getHitsResult(Class clazz, SearchHit[] searchHits){
        List result=new ArrayList();
        for(int i=0;i<searchHits.length;i++){
            SearchHit searchHit=searchHits[i];
            Object temp=JSONObject.parseObject(searchHit.getSourceAsString());
            if(clazz!=null){
                result.add(JSON.parseObject(searchHit.getSourceAsString(),clazz));
                continue;
            }
            result.add(temp);
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(CollectionUtil.isEmpty(null));
    }

}
