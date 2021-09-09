package com.shancaolv.controller;

import com.alibaba.fastjson.JSONObject;
import com.shancaolv.entities.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description 测试接口
 * @Author duhn
 * @Date 2021/9/9
 **/
@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
public class TestController {

    @GetMapping("hello")
    @ApiOperation(value = "你好世界", response = JSONObject.class)
    public CommonResult userInfo() {
        JSONObject result = new JSONObject();
        result.put("name", "duhn");
        return new CommonResult(0, "success", result);
    }
}
