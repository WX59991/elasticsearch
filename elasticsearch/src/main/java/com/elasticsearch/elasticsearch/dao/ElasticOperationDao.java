package com.elasticsearch.elasticsearch.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.elasticsearch.elasticsearch.entity.User;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.*;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Field;
import java.util.List;

/**
 *
 * 在最新版本的es中取消了type
 *
 * ES java 操作公共服务
 * 相比于http的RestApi会更快
 * @author wangxia
 * @date 2019/8/9 16:28
 * @Description:
 */
@Slf4j
@Component
public class ElasticOperationDao {

    @Autowired
    private Client client;

    /**
     * 从单次的实时操作改为批量操作，这样做的好处有：
     * 1.减少网路开销，
     * 2.从消息大小，时间，消息数量三个维度来衡量 批量操作的维度，如果数据不是要求非常实时的操作
     */
    private BulkProcessor bulkProcessor;

    /**
     * 初始化批量操作过程
     */
    //@PostConstruct 注释用于在依赖关系注入完成之后需要执行的方法上
    @PostConstruct
    public void initBulkProcessor(){
        bulkProcessor=BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                log.info("序号：{} 开始执行{} 条记录保存",executionId,request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                log.info("序号：{} 执行{}条记录保存成功,耗时：{}毫秒,",executionId,request.numberOfActions(),response.getIngestTookInMillis());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                log.error(String.format("序号：%s 执行失败; 总记录数：%s",executionId,request.numberOfActions()),failure);
            }
        }).setBulkActions(1000)   //Sets when to flush a new bulk request based on the number of actions currently added.  一次批量的最多纪录数
                .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))  //Sets when to flush a new bulk request based on the size of actions currently added.  一次批量的最多大小
                .setConcurrentRequests(4)  //Sets the number of concurrent requests allowed to be executed.
                .setFlushInterval(TimeValue.timeValueSeconds(5))   //Sets a flush interval flushing *any* bulk actions pending if the interval passes.
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(500),3))  //失败后等待时间及重试次数
                 .build();
    }

    @PreDestroy
    public void closeBulk(){
        if(bulkProcessor!=null){
            bulkProcessor.close();
        }
    }

    /**
     * 批量添加
     * @param index
     * @param type
     * @param object
     */
    public void addDocumentToBulkProcessor(String index,String type,Object object){
        bulkProcessor.add(client.prepareIndex(index,type).setSource(JSON.toJSONString(object), XContentType.JSON).request());
    }

    /**
     * 添加单个
     * @param indices
     * @param type
     * @param object
     */
    public void addDocument(String indices, String type, Object object) {
        //新版的API中使用setSource时，参数的个数必须是偶数，否则需要加上 XContentType.JSON
        IndexResponse resp = client.prepareIndex(indices, type).setSource(JSON.toJSONString(object), XContentType.JSON).get();
        log.info("添加结果：{}",resp.toString());
    }

    /**
     * 按id删除
     */
    public void deleteDocumentById(String index, String type, String id) {
        // new DeleteByQueryRequest(search);
        DeleteResponse resp = client.prepareDelete(index, type, id).get();
        log.info("删除结果：{}",resp.toString());
    }

    /**
     * 按条件删除
     */
    public void deleteDocumentByQuery(String index, String type, User param) {

        //参数 ：客户端 删除的action
        DeleteByQueryRequestBuilder builder = new DeleteByQueryRequestBuilder(client,DeleteByQueryAction.INSTANCE);

        //builder.filter(convertParam(param));
        builder.source().setIndices(index).setTypes(type).setQuery(param.convertParam());
        BulkByScrollResponse resp = builder.get();
        log.info("删除结果：{}",resp.toString());
    }

    /**
     * 按ID更新
     *
     */
    public void updateDocument(String indices, String type,String id,Object object) {
        UpdateResponse resp = client.prepareUpdate(indices, type, id).setDoc(JSON.toJSONString(object)).get();
        log.info("更新结果：{}",resp.toString());
    }


    /**
     * 按条件更新
     *
     */
    public void updateDocumentByQuery(String indices, String type, Object object, User param) {
        UpdateByQueryRequestBuilder builder = new UpdateByQueryRequestBuilder(client,UpdateByQueryAction.INSTANCE);
        builder.source().setIndices(indices).setTypes(type).setQuery(param.convertParam());
    }


    public <T> List<T> queryDocumentByParam(String indices, String type, User param, Class<T> clazz) {
        SearchRequestBuilder builder = buildRequest(indices,type);
        //添加排序
//        builder.addSort("birthday", SortOrder.ASC);
        builder.setQuery(param.convertParam());
        builder.setFrom(0).setSize(10);
        SearchResponse resp = builder.get();
        return convertResponse(resp,clazz);
    }

    /**
     * 通用的装换返回结果
     *
     */
    public <T> List<T> convertResponse(SearchResponse response,Class<T> clazz) {
        List<T> list = Lists.newArrayList();
        if(response != null && response.getHits() != null) {
            String result = null;
            T e = null;
            Field idField = ReflectionUtils.findField(clazz, "id");
            if (idField != null) {
                ReflectionUtils.makeAccessible(idField);
            }
            for(SearchHit hit : response.getHits()) {
                result = hit.getSourceAsString();
                if (StringUtils.hasText(result)) {
                    e = JSONObject.parseObject(result, clazz);
                }
                if (e != null) {
                    if (idField != null) {
                        ReflectionUtils.setField(idField, e, hit.getId());
                    }
                    list.add(e);
                }
            }
        }
        return list;
    }

    public SearchRequestBuilder buildRequest(String indices, String type) {
        return client.prepareSearch(indices).setTypes(type);
    }

    /**
     * 不存在就创建索引
     *
     */
    public boolean createIndexIfNotExist(String index, String type) {
        IndicesAdminClient adminClient = client.admin().indices();
        IndicesExistsRequest request = new IndicesExistsRequest(index);
        IndicesExistsResponse response = adminClient.exists(request).actionGet();
        if (!response.isExists()) {
            return createIndex(index, type);
        }
        return true;
    }

    /**
     * 创建索引
     *
     */
    public boolean createIndex(String index, String type) {
        XContentBuilder mappingBuilder;
        try {
            mappingBuilder = this.getMapping(type);
        } catch (Exception e) {
            log.error(String.format("创建Mapping 异常；index:%s type:%s,", index, type), e);
            return false;
        }
        Settings settings = Settings.builder().put("index.number_of_shards", 2)
                .put("index.number_of_replicas", 1)
                .put("index.refresh_interval", "5s").build();
        IndicesAdminClient adminClient = client.admin().indices();
        CreateIndexRequestBuilder builder = adminClient.prepareCreate(index);
        builder.setSettings(settings);
        CreateIndexResponse response = builder.addMapping(type, mappingBuilder).get();
        log.info("创建索引：{} 类型:{} 是否成功：{}", index, type, response.isAcknowledged());
        return response.isAcknowledged();
    }

    /***
     * 创建模板信息
     * 创建索引的Mapping信息  注意声明的roles为nested类型
     */
    private XContentBuilder getMapping(String type) throws Exception {
        XContentBuilder mappingBuilder = XContentFactory.jsonBuilder().startObject()
                .startObject(type)
                .startObject("_all").field("enabled", false).endObject()
                .startObject("properties")
                .startObject("userName").field("type", "keyword").endObject()
                .startObject("age").field("type", "integer").endObject()
                .startObject("birthday").field("type", "text").endObject()
                .startObject("description").field("type", "text")
                .field("analyzer", "ik_smart").endObject();   //粗粒度检查索引
        return mappingBuilder;
    }

}
