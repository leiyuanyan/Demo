package com.example.demo.vo;

/**
 * @author leiyuanyan
 * @Description
 * @since 2021/3/30 15:46
 */
public enum HttpStatus {
    OK(200, "OK"),
    UNAUTHERIZED(401, "未认证"),
    UNAUTHORIZED(403, "未授权"),
    NOTFOUND(404, "未找到请求资源"),
    EXCEPTION(501, "API网关异常"),
    ERROR(500, "系统内部错误，请联系系统管理员！"),
    REFRESH_TOKEN(600, "刷新TOKEN"),
    ERROR_INEXISTENCE(1004, "记录不存在"),
    ERROR_PARM(1008, "参数异常，请使用规范参数");

    // 成员变量
    private String message;
    private int code;

    // 构造方法
    HttpStatus(int code, String message) {
        this.message = message;
        this.code = code;
    }

    // get set 方法
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
