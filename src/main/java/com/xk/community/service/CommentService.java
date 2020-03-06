package com.xk.community.service;

import com.xk.community.dto.CommentDto;
import com.xk.community.enums.CommentTypeEnum;
import com.xk.community.exception.CustomizeErrorCode;
import com.xk.community.exception.CustomizeException;
import com.xk.community.mapper.*;
import com.xk.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;

    //事务注解，以下整个方法为事务
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParent_id() == null || comment.getParent_id() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_ERROR);
        }

        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParent_id());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

//            增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParent_id());
            parentComment.setComment_count(1);
            commentExtMapper.incCommentCount(parentComment);

        }
        else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParent_id());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);

            Question question1 = new Question();
            question1.setId(question.getId());
            question1.setComment_count(1);
            questionExtMapper.incCommentCount(question1);

        }
    }

    public List<CommentDto> listByTargetId(Long id, CommentTypeEnum type){
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParent_idEqualTo(id)
                .andTypeEqualTo(type.getType());
        //按修改时间逆序排列
        commentExample.setOrderByClause("gmt_modified desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.isEmpty())return new ArrayList<>();
        //lambada使用, 以获取去重的评论人id集合
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>(commentators);

        //获取评论人并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);

        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));

        //转换comment为commentDto
        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());

        return commentDtos;
    }
}
