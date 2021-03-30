package com.example.demo.component.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author leiyuanyan
 * @Description
 * @since 2021/3/30 15:24
 */
public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return bCryptPasswordEncoder.matches(charSequence, s);
    }
}
