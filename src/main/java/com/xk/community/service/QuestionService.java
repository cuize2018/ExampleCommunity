package com.xk.community.service;

import com.xk.community.dto.PageDto;
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

    //根据指定页面和范围查询问题，使用PageDto页面信息存储
    public PageDto list(Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        Integer totalCount = questionMapper.count();
        pageDto.setPagination(page, size, totalCount);

        page = pageDto.getPage();
        //offset = size*(page -1)
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.list(offset, size);
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

        pageDto.setQuestions(questionDtos);
        return pageDto;
    }
}
