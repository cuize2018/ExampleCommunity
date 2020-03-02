package com.xk.community.enums;

//评论类型，类型1是对问题的回复，类型2是对回复的回复
public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType().equals(type)){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
