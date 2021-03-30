package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.reponsitory.IUserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;


/**
 * @author leiyuanyan
 * @Description
 * @since 2021/3/30 15:11
 */
@Service
public class UserService {
@Autowired
    IUserReponsitory reponsitory;

    /**
     * 新增用户测试
     * @param user
     * @return
     */
    @Transactional
   public User create(User user){
        user.setId(Long.valueOf(new Random().nextInt()));
    return reponsitory.save(user);
}

    /**
     * 根据账号查找用户
     * @param account
     * @return
     */
    public User findByAccount(String account){
    return reponsitory.findByAccount(account);
}
}
