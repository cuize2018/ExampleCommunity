package com.xk.community.controller;

import com.xk.community.dto.CommentCreateDto;
import com.xk.community.dto.CommentDto;
import com.xk.community.dto.ResultDto;
import com.xk.community.enums.CommentTypeEnum;
import com.xk.community.exception.CustomizeErrorCode;
import com.xk.community.model.Comment;
import com.xk.community.model.User;
import com.xk.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    //@ResponseBody,自动把对象序列化为json，发给前端
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    //@RequestBody注解，可以通过前端传来的json数据反序列化出对象
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest httpServletRequest) {

        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorOf(CustomizeErrorCode.USER_NOT_LOGIN);
        }

        if (commentCreateDto == null || StringUtils.isBlank(commentCreateDto.getContent())) {
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParent_id(commentCreateDto.getParent_id());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(comment.getGmt_create());
        comment.setCommentator(user.getId());
        comment.setLike_count(0L);
        comment.setComment_count(0);
        commentService.insert(comment, user);

        return ResultDto.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDto> commentDtos = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDto.okOf(commentDtos);
    }
}
