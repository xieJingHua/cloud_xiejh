package com.xiejh.feignservice.service;

import com.xiejh.common_service.response.Result;
import com.xiejh.feignservice.service.impl.UserFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiejh
 * @Date 2020/10/12 11:45
 **/
@FeignClient(value = "user-service",fallback = UserFallbackService.class)
public interface UserService {

    @GetMapping("/user/get/{id}")
    Result<String> getUser(@PathVariable Long id);
}
