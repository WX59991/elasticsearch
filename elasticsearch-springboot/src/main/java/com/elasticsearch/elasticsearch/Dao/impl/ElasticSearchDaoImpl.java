package com.elasticsearch.elasticsearch.Dao.impl;

import com.elasticsearch.elasticsearch.Dao.ElasticSearchDao;
import com.elasticsearch.elasticsearch.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangxia
 * @date 2019/8/12 17:40
 * @Description:
 */
@Component
public class ElasticSearchDaoImpl implements ElasticSearchDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public String createIndex(String index,Class clazz) {
        if(elasticsearchTemplate.indexExists(index))
            return "索引已存在";
        if(!elasticsearchTemplate.createIndex(index)){
            return "索引创建失败";
        }
        if(clazz!=null && !elasticsearchTemplate.putMapping(clazz)){
            return "模板放入失败";
        }
        return "创建成功";
    }

    @Override
    public String deleteIndex(String index) {
        if(elasticsearchTemplate.deleteIndex(index)){
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public String deleteDataById(String index, String type, String id) {
        return elasticsearchTemplate.delete(index,type,id);
    }

    @Override
    public void deleteDatasByQuery(DeleteQuery deleteQuery, Class clazz) {
        elasticsearchTemplate.delete(deleteQuery,clazz);
    }

    @Override
    public String putMapping(String index, String type, Object map) {
        if(elasticsearchTemplate.putMapping(index,type,map)){
            return "添加成功";
        }
        return "添加失败";
    }

    @Override
    public String addAlias(AliasQuery query) {
        if(elasticsearchTemplate.addAlias(query)){
            return "添加别名成功";
        }
        return "添加别名失败";
    }

    @Override
    public void save(String index, Article object) {
        IndexQuery indexQuery=new IndexQueryBuilder()
                .withIndexName(index)
                .withObject(object)
                .build();
        if(!StringUtils.isEmpty(object.getId())){
            indexQuery.setId(""+object.getId());
        }
        elasticsearchTemplate.index(indexQuery);
    }

    @Override
    public void bulkIndex(String index,List<Article> datas) {
        List<IndexQuery> newDatas=new ArrayList<>();
        for(Article object:datas){
            IndexQuery indexQuery=new IndexQueryBuilder()
                    .withIndexName(index)
                    .withObject(object)
                    .build();
            if(!StringUtils.isEmpty(object.getId())){
                indexQuery.setId(""+object.getId());
            }
            newDatas.add(indexQuery);
        }
        elasticsearchTemplate.bulkIndex(newDatas);
    }

    @Override
    public void bulkUpdate(List<UpdateQuery> queries) {
        elasticsearchTemplate.bulkUpdate(queries);
    }

    @Override
    public long count(CriteriaQuery criteriaQuery, Class clazz) {
        return elasticsearchTemplate.count(criteriaQuery,clazz);
    }

    @Override
    public long count(SearchQuery searchQuery, Class clazz) {
        return elasticsearchTemplate.count(searchQuery,clazz);
    }

    @Override
    public long count(CriteriaQuery query) {
        return elasticsearchTemplate.count(query);
    }

    @Override
    public long count(SearchQuery query) {
        return elasticsearchTemplate.count(query);
    }

    @Override
    public String index(IndexQuery query) {
        return elasticsearchTemplate.index(query);
    }

    @Override
    public Map getMapping(Class clazz) {
        return elasticsearchTemplate.getMapping(clazz);
    }

    @Override
    public Map getMapping(String index, String type) {
        return elasticsearchTemplate.getMapping(index,type);
    }

    @Override
    public Boolean removeAlias(AliasQuery query) {
        return elasticsearchTemplate.removeAlias(query);
    }


}
