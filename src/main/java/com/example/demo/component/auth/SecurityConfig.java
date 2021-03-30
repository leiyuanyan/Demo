package com.example.demo.component.auth;

import com.alibaba.fastjson.JSON;
import com.example.demo.vo.GenericResponseBody;
import com.example.demo.vo.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;


/**
 * @author leiyuanyan
 * @Description
 * @since 2021/3/30 15:26
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    String contentType = "application/json;charset=utf-8";
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
       //重点代码
        http.authenticationProvider(authenticationProvider())
                .httpBasic()
                //未登录时，进行json格式的提示
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType(contentType);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    GenericResponseBody responseBody = new GenericResponseBody(HttpStatus.UNAUTHERIZED.getCode(), null, "未认证");
                    out.write(JSON.toJSONString(responseBody));
                    out.flush();
                    out.close();
                })
                .and()
                .rememberMe().rememberMeParameter("remember-me").key("OCP")
                .and()
                .authorizeRequests().antMatchers(
                "/r/permit/**",
                "/auth/*",
                "/oauth/*",
                "/login/**",
                "/logout",
                "/swagger-resources/**", "/webjars/**", "/v3/**", "/swagger-ui.html/**", "/swagger-ui/**"
        ).permitAll()
                .anyRequest().authenticated() //必须授权才能范围
                .and()
                .formLogin() //使用自带的登录
                .permitAll()
                //登录失败，返回json
                .failureHandler((request, response, ex) -> {
                    response.setContentType(contentType);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter out = response.getWriter();
                    GenericResponseBody responseBody = new GenericResponseBody(HttpStatus.UNAUTHERIZED.getCode());
                    if (ex instanceof UsernameNotFoundException) {
                        responseBody.setMessage("用户名/密码错误");
                    } else if (ex instanceof DisabledException) {
                        responseBody.setMessage("账户被禁用");
                    } else if (ex instanceof LockedException) {
                        responseBody.setMessage("账户被锁定");
                    } else if (ex instanceof CredentialsExpiredException) {
                        responseBody.setMessage("密码过期");
                    } else if (ex instanceof AccountExpiredException) {
                        responseBody.setMessage("账户过期");
                    } else if (ex instanceof BadCredentialsException) {
                        responseBody.setMessage("用户名/密码错误");//登录时，用户名或者密码输入错误，给一个模糊的提示
                    } else {
                        responseBody.setMessage("登录失败，" + ex.getMessage());
                    }
                    out.write(JSON.toJSONString(responseBody));
                    out.flush();
                    out.close();
                })
                //登录成功，返回json
                .successHandler((request, response, authentication) -> {
                    GenericResponseBody responseBody = new GenericResponseBody(HttpStatus.OK.getCode(), "登录成功",
                            authentication.getPrincipal());
                    response.setContentType(contentType);
                    PrintWriter out = response.getWriter();
                    out.write(JSON.toJSONString(responseBody));
                    out.flush();
                    out.close();
                })
                .and()
                .exceptionHandling()
                //未认证
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType(contentType);
                    PrintWriter out = response.getWriter();
                    GenericResponseBody responseBody = new GenericResponseBody(HttpStatus.UNAUTHERIZED.getCode(), "尚未登录，请先登录", null);
                    out.write(JSON.toJSONString(responseBody));
                    out.flush();
                    out.close();
                })
                //没有权限，返回json
                .accessDeniedHandler((request, response, ex) -> {
                    response.setContentType(contentType);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    GenericResponseBody responseBody = new GenericResponseBody(HttpStatus.UNAUTHORIZED.getCode(), "权限不足", null);
                    out.write(JSON.toJSONString(responseBody));
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                .deleteCookies().clearAuthentication(true).invalidateHttpSession(true)
                //退出成功，返回json
                .logoutSuccessHandler((request, response, authentication) -> {
                    GenericResponseBody responseBody = new GenericResponseBody(HttpStatus.OK.getCode(), "退出成功", null);
                    response.setContentType(contentType);
                    PrintWriter out = response.getWriter();
                    out.write(JSON.toJSONString(responseBody));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1);//最大会话数为 1
        //开启跨域访问
        http.cors();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) {
        //对于在header里面增加token等类似情况，放行所有OPTIONS请求。
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        ProviderManager manager = new ProviderManager(Arrays.asList(authenticationProvider()));
        return manager;
    }

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }


}
