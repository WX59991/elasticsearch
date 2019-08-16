package com.elasticsearch.elasticsearch.config;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * @author wangxia
 * @date 2019/8/12 17:25
 * @Description:
 */
@Configuration
public class ESConfig {

    

    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchTemplate getElasticsearchTemplate(TransportClient client){
        return new ElasticsearchTemplate(client);
    }

}
