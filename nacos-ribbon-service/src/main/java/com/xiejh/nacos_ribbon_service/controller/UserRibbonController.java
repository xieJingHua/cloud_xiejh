package com.xiejh.nacos_ribbon_service.controller;

import com.xiejh.common_service.response.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author xiejh
 * @Date 2020/10/10 15:17
 **/
@RestController
@RequestMapping("/user")
public class UserRibbonController {

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable Long id) {
        String url = userServiceUrl + "/user/get/{1}";
        Result result = null;
        long start = System.currentTimeMillis();
        try {
            result = restTemplate.getForObject(url, Result.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("======超时时间:"+(System.currentTimeMillis()-start)/1000);
        }
        return result;
    }

}
