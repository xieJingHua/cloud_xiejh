package com.xiejh.hystrix_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiejh
 * @Date 2020/10/10 15:15
 **/
@Configuration
public class HttpConfig {

    /**
     * httpRestTemplate
     */
    @LoadBalanced
    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        //httpClient
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(60000);
        return new RestTemplate(requestFactory);
    }
}
