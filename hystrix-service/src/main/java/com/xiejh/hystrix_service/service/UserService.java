package com.xiejh.hystrix_service.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.xiejh.common_service.response.Result;
import com.xiejh.hystrix_service.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author xiejh
 * @Date 2020/10/10 16:42
 **/
@Service
@Slf4j
public class UserService {

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @HystrixCommand(fallbackMethod = "getDefaultUser")
    public Result getUser(Long id) {
        return restTemplate.getForObject(userServiceUrl + "/user/get/{1}", Result.class, id);
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser",
            commandKey = "getUserCommand",
            groupKey = "getUserGroup",
            threadPoolKey = "getUserThreadPool")
    public Result getUserCommand(@PathVariable Long id) {
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", Result.class, id);
    }

    public Result getDefaultUser(@PathVariable Long id) {
        User defaultUser = new User(-1, "defaultUser", "123456");
        return Result.success(defaultUser);
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser2", ignoreExceptions = {NullPointerException.class})
    public Result getUserException(Long id) {
        if (id == 1) {
            throw new IndexOutOfBoundsException();
        } else if (id == 2) {
            throw new NullPointerException();
        }
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", Result.class, id);
    }

    public Result getDefaultUser2(@PathVariable Long id, Throwable e) {
        log.error("getDefaultUser2 id:{},throwable class:{}", id, e.getClass());
        User defaultUser = new User(-2, "defaultUser2", "123456");
        Result result = Result.fail(null);
        result.setData(defaultUser);
        return result;
    }


    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "getUserCache")
    public Result getUserCache(Long id) {
        log.info("getUserCache id:{}", id);
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", Result.class, id);
    }

    /**
     * 为缓存生成key的方法
     */
    public String getCacheKey(Long id) {
        return String.valueOf(id);
    }
}
