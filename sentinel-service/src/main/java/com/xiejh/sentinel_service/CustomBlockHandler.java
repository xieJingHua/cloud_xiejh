package com.xiejh.sentinel_service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xiejh.common_service.response.Result;

/**
 * @author xiejh
 * @Date 2020/10/21 14:20
 **/
public class CustomBlockHandler {

    public Result handleException(BlockException exception){
        return new Result("自定义限流信息",200);
    }
}