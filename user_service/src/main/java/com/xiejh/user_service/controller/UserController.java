package com.xiejh.user_service.controller;

import com.xiejh.common_service.response.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiejh
 * @Date 2020/10/10 11:15
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/get/{id}")
    public Result<String> getUser(@PathVariable Long id) {
        System.out.println("id:" + id);
        return Result.success("谢静华");
    }
}
