package com.xk.community.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long parent_id;
    private Integer type;
    private String content;
}
