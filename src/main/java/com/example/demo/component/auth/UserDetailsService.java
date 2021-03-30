package com.example.demo.component.auth;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author leiyuanyan
 * @Description
 * @since 2021/3/30 15:10
 */
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        //在这里可以自己调用数据库，对username进行查询，看看在数据库中是否存在
        User loginUser = userService.findByAccount(account);
        if (null != loginUser) {
           UserDetails userDetail = new UserDetails();
            userDetail.setUsername(loginUser.getAccount());
            userDetail.setPassword(loginUser.getPassword());
            userDetail.setStatus(loginUser.getStatus());
            //userDetail.setRole(loginUser.getRole());
            return userDetail;
        }
        throw new UsernameNotFoundException(account + "未找到");
    }
}
