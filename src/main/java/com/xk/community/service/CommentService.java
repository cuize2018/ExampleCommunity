package com.xk.community.service;

import com.xk.community.enums.CommentTypeEnum;
import com.xk.community.exception.CustomizeErrorCode;
import com.xk.community.exception.CustomizeException;
import com.xk.community.mapper.CommentMapper;
import com.xk.community.mapper.QuestionExtMapper;
import com.xk.community.mapper.QuestionMapper;
import com.xk.community.model.Comment;
import com.xk.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
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
}
