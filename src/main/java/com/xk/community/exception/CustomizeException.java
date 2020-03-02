package com.xk.community.exception;

/**
 * 自定义的异常类,继承运行时异常,拦截运行时的异常
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
