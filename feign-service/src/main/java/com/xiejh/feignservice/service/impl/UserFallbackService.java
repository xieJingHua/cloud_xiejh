package com.xiejh.feignservice.service.impl;

import com.xiejh.common_service.response.Result;
import com.xiejh.feignservice.service.UserService;
import org.springframework.stereotype.Component;

/**
 * 服务降级类
 * @author xiejh
 * @Date 2020/10/12 13:41
 **/
@Component
public class UserFallbackService implements UserService {

    @Override
    public Result<String> getUser(Long id) {
        return Result.success("服务降级：xiejh");
    }
}
