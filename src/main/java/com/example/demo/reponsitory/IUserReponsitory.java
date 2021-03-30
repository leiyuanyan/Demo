package com.example.demo.reponsitory;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author leiyuanyan
 * @Description
 * @since 2021/3/30 15:12
 */
public interface IUserReponsitory  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByAccount(String account);
}
