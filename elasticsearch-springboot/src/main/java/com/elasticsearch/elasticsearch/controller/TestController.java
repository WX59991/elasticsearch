package com.elasticsearch.elasticsearch.controller;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangxia
 * @date 2019/9/5 17:25
 * @Description:
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TransportClient transportClient;

    @Bean("restTemplate")
    RestTemplate getResTTemplate(){
        return new RestTemplate();
    }

    @GetMapping("/test")
    public String getAll(){
        SearchRequest searchRequest=new SearchRequest();
        searchRequest.indices("test");
        ActionFuture<SearchResponse> future=transportClient.search(searchRequest);
        return future.actionGet().getHits().getHits().toString();
//        return restTemplate.getForEntity("http://132.46.118.17:9200/test/_search",String.class).getBody();
    }
}
