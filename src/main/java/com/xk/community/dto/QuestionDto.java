package com.xk.community.dto;

import com.xk.community.model.User;
import lombok.Data;

/**
 * 用于组床User和Question的类
 */
@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Long creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private String tag;
    private User user;
}
