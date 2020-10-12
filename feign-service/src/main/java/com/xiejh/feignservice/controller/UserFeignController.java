package com.xiejh.feignservice.controller;

import com.xiejh.common_service.response.Result;
import com.xiejh.feignservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xiejh
 * @Date 2020/10/12 11:50
 **/
@RestController
@RequestMapping("/user")
public class UserFeignController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    Result<String> getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
}
