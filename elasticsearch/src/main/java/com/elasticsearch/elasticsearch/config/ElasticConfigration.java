package com.elasticsearch.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;

/**
 * @author wangxia
 * @date 2019/8/9 16:04
 * @Description:  ES配置
 */
@Slf4j
@Configuration
public class ElasticConfigration {

    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.clusterName}")
    private String esClusterName;


    private TransportClient client;

    @PostConstruct
    public void initialize() throws Exception{
        Settings esSetting=Settings.builder()
                .put("cluster.name", esClusterName)
                .put("","")
                .put("client.transport.sniff", true).build();  //增加嗅探功能
        client = new PreBuiltTransportClient(esSetting);
        String[] esHosts = esHost.trim().split(",");
        for(String host:esHosts){
            client.addTransportAddress(new TransportAddress(InetAddress.getByName(host),esPort));
            log.info("{}加入集群{}",host,esClusterName);
        }
    }

    @Bean
    public Client client() {
        return client;
    }


    @PreDestroy
    public void destroy() {
        if (client != null) {
            client.close();
        }
    }


}
