package com.example.demo.api;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.vo.GenericResponseBody;
import com.example.demo.vo.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author leiyuanyan
 * @Description
 * @since 2021/3/30 16:20
 */
@Slf4j
@RequestMapping("/r")
public class UserAPi {
@Autowired
    UserService service;
    @PostMapping("/create")
    public GenericResponseBody create(@RequestBody User user) {
        log.info("新增用户信息：{}", JSON.toJSONString(user));
        return new GenericResponseBody(HttpStatus.OK.getCode(), service.create(user));
    }
}
