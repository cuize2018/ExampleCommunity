package com.xk.community.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论"),
    ;
    private int type;
    private String info;

    NotificationTypeEnum(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public static String nameOfType(int type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type){
                return notificationTypeEnum.getInfo();
            }
        }
        return "";
    }
}
