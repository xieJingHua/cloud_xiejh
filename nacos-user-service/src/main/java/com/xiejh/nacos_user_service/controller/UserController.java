package com.xiejh.nacos_user_service.controller;

import com.xiejh.common_service.response.Result;
import entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/getUserByIds/{ids}")
    public Result<List<User>> getUserByIds(@PathVariable String ids) {
        System.out.println("ids:" + ids);
        List<User> userList = new ArrayList<>(2);
        userList.add(new User(1, "001", "张三"));
        userList.add(new User(2, "002", "李四"));
        return Result.success(userList);
    }

}
