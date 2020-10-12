package com.xiejh.hystrix_service.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.xiejh.common_service.response.Result;
import com.xiejh.hystrix_service.entity.User;
import com.xiejh.hystrix_service.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xiejh
 * @Date 2020/10/10 16:39
 **/
@RestController
@RequestMapping("/user")
public class UserHystrixController {

    @Resource
    private UserService userService;

    @GetMapping("/get/{id}")
    public Result testFallback(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/testCommand/{id}")
    public Result testCommand(@PathVariable Long id) {
        return userService.getUserCommand(id);
    }

    @GetMapping("/testException/{id}")
    public Result testException(@PathVariable Long id) {
        return userService.getUserException(id);
    }

    @GetMapping("/testCache/{id}")
    public Result testCache(@PathVariable Long id) {
        HystrixRequestContext.initializeContext();//初始化请求上下文
        userService.getUserCache(id);
        userService.getUserCache(id);
        userService.getUserCache(id);
        return Result.success(null);
    }

    @GetMapping("/testCollapser")
    public Result testCollapser() throws ExecutionException, InterruptedException {
        Future<User> future1 = userService.getUserFuture(1L);
        Future<User> future2 = userService.getUserFuture(2L);
        System.out.println("future1.get():" + future1.get());
        System.out.println("future2.get():" + future2.get());
        Thread.sleep(200);
        Future<User> future3 = userService.getUserFuture(3L);
        System.out.println(" future3.get():" + future3.get());
        return Result.success(null);
    }
}
