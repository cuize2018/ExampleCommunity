package com.xk.community.mapper;

import com.xk.community.model.Comment;
import com.xk.community.model.CommentExample;
import com.xk.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}