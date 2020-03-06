package com.xk.community.dto;

import com.xk.community.model.User;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long parent_id;
    private Integer type;
    private Long commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private String content;
    private User user;
    private Integer comment_count;
}
