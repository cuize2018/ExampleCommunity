package com.xk.community.exception;

/**
 * 自定义异常枚举
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "没有此问题，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    USER_NOT_LOGIN(2003, "用户未登录，无法评论，请先登录"),
    SYSTEM_ERROR(2004, "服务器冒烟了，请稍后再试!"),
    TYPE_PARAM_ERROR(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "该评论不见了"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
