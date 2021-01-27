package com.example.demo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leiyuanyan
 * @Description  测试接口
 * @since 2021/1/19 16:28
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class testApi {

    @RequestMapping("/hello")
    public String helloSpringBoot(){
        return "你好SringpBoot";
    }
}
