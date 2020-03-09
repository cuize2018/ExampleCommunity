package com.xk.community.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private Long id;
    private Long gmt_create;
    private Integer status;
    private Long notifier;
    private String notifier_name;
    private String outer_title;
    private String typeName;
    private Long outer_id;
    private Integer type;
}
