package com.xk.community.controller;

import com.xk.community.dto.CommentDto;
import com.xk.community.dto.ResultDto;
import com.xk.community.exception.CustomizeErrorCode;
import com.xk.community.model.Comment;
import com.xk.community.model.User;
import com.xk.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    //@ResponseBody,自动把对象序列化为json，发给前端
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    //@RequestBody注解，可以通过前端传来的json数据反序列化出对象
    public Object post(@RequestBody CommentDto commentDto,
                       HttpServletRequest httpServletRequest){

        User user = (User)httpServletRequest.getSession().getAttribute("user");
        if (user == null){
           return ResultDto.errorOf(CustomizeErrorCode.USER_NOT_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParent_id(commentDto.getParent_id());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(comment.getGmt_create());
        comment.setCommentator(user.getId());
        comment.setLike_count(0L);
        commentService.insert(comment);

        return ResultDto.okOf();
    }
}
