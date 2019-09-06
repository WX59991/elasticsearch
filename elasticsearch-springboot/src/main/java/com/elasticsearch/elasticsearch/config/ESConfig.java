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

//    @Bean("transportClient")
//    public TransportClient transportClient() throws UnknownHostException {
//        InetAddress inetAddress=InetAddress.getByName("132.46.118.17");
//        TransportAddress transportAddress=new TransportAddress(inetAddress,9300);
//        TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
//                .put("cluster.name", "elastic")
//                .put("xpack.security.user", "elastic:elk@2019")
//                .build()).addTransportAddresses();
//        return client;
//    }
    

    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchTemplate getElasticsearchTemplate(TransportClient transportClient){
        return new ElasticsearchTemplate(transportClient);
    }

}
