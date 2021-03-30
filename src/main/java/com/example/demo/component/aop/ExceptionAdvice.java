package com.example.demo.component.aop;

import com.example.demo.vo.GenericResponseBody;
import com.example.demo.vo.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leiyuanyan
 * @Description 错误处理
 * @since 2021/3/30 15:48
 */
@Slf4j
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public GenericResponseBody errorHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
        log.error("", e);
        if (e instanceof Exception) {
            return new GenericResponseBody(HttpStatus.ERROR_INEXISTENCE.getCode(), e.getLocalizedMessage(), null);
        }
        return new GenericResponseBody(HttpStatus.ERROR.getCode(), "系统异常", null);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public GenericResponseBody handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        return new GenericResponseBody(HttpStatus.ERROR_PARM.getCode(), msg, null);
    }}
