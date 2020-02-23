package com.xk.community.service;

import com.xk.community.dto.QuestionDto;
import com.xk.community.mapper.QuestionMapper;
import com.xk.community.mapper.UserMapper;
import com.xk.community.model.Question;
import com.xk.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 当一个请求同时需要组装Question和User的时候，
 * 我们需要一个中间层来组装Question和User，习惯上把它称为Service
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    //查询所有的问题，使用QuestionDto列表存储
    public List<QuestionDto> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();

            //拷贝Question类中的属性到QuestionDto中
            BeanUtils.copyProperties(question, questionDto);
            //添加QuestionDto中的user属性
            questionDto.setUser(user);

            questionDtos.add(questionDto);
        }
        return questionDtos;
    }
}
