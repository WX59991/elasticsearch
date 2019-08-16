package com.elasticsearch.elasticsearch.Dao;

import com.elasticsearch.elasticsearch.entity.Article;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.List;
import java.util.Map;

/**
 * @author wangxia
 * @date 2019/8/12 17:38
 * @Description:
 */
public interface ElasticSearchDao {

    /**
     * 创建索引bong放入模板
     * @param index  索引
     * @param clazz  模板对应的类
     * @return
     */
    String createIndex(String index, Class clazz);

    /**
     * 删除索引
     * @param index  索引名
     * @return
     */
    String deleteIndex(String index);

    /**
     * 根据id值删除数据
     * @param index
     * @param type
     * @param id
     * @return
     */
    String deleteDataById(String index, String type, String id);

    /**
     * 通过查询语句删除
     * @param deleteQuery
     * @param clazz
     */
    public  void deleteDatasByQuery(DeleteQuery deleteQuery, Class clazz);


    public String putMapping(String index, String type, Object map);

    /**
     * @param query  添加别名
     * @return
     */
    public String addAlias(AliasQuery query);

    public void save(String index,Article object);

    /**
     * 批量添加数据
     * @param datas
     */
    public void bulkIndex(String index,List<Article> datas);


    /**
     * 批量更新
     * @param queries
     */
    public void bulkUpdate(List<UpdateQuery> queries);

    /**
     * 计数
     * @param criteriaQuery
     * @param clazz
     * @return
     */
    public long count(CriteriaQuery criteriaQuery, Class clazz);

    public long count(SearchQuery searchQuery, Class clazz);

    public long count(CriteriaQuery query);

    public long count(SearchQuery query);

    public String index(IndexQuery query);

    /**
     * 获取模板
     * @param clazz
     * @return
     */
    public Map getMapping(Class clazz);

    public Map getMapping(String index, String type);

    /**
     * 删除别名
     * @param query
     * @return
     */
    public Boolean removeAlias(AliasQuery query);




}
