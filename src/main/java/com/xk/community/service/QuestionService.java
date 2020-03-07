package com.xk.community.service;

import com.xk.community.dto.PageDto;
import com.xk.community.dto.QuestionDto;
import com.xk.community.exception.CustomizeErrorCode;
import com.xk.community.exception.CustomizeException;
import com.xk.community.mapper.QuestionExtMapper;
import com.xk.community.mapper.QuestionMapper;
import com.xk.community.mapper.UserMapper;
import com.xk.community.model.Question;
import com.xk.community.model.QuestionExample;
import com.xk.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 当一个请求同时需要组装Question和User的时候，
 * 我们需要一个中间层来组装Question和User，习惯上把它称为Service
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    //根据指定页面和范围查询问题，使用PageDto页面信息存储
    public PageDto list(Integer page, Integer size) {
        PageDto pageDto = new PageDto();

        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());

        //计算总页数
        int totalPages = totalCount / size;
        if (totalCount % size != 0) {
            totalPages += 1;
        }
        //控制页码范围，1 <= page <= totalPages
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        pageDto.setPagination(page, totalPages);

        //offset = size*(page -1)
        int offset = size * (page - 1);

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_modified desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDto> questionDtos = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

    public PageDto list(Long userId, Integer page, Integer size) {
        PageDto pageDto = new PageDto();

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);

        Integer totalCount = (int) questionMapper.countByExample(example);

        //计算总页数
        int totalPages = totalCount / size;
        if (totalCount % size != 0) {
            totalPages += 1;
        }
        //控制页码范围，1 <= page <= totalPages
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        pageDto.setPagination(page, totalPages);

        //offset = size*(page -1)
        int offset = page < 1 ? 0 : size * (page - 1);

        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));

        List<QuestionDto> questionDtos = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

    public QuestionDto getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDto questionDto = new QuestionDto();
        //拷贝Question类中的属性到QuestionDto中
        BeanUtils.copyProperties(question, questionDto);
        //添加QuestionDto中的user属性
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建问题
            question.setComment_count(0);
            question.setLike_count(0);
            question.setView_count(0);
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());

            questionMapper.insert(question);
        } else {
            //更新问题
            Question updateQuestion = new Question();
            updateQuestion.setGmt_modified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());

            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setView_count(1);

        questionExtMapper.incView(question);
    }

    public List<QuestionDto> selectRelated(QuestionDto questionDto) {
        if (StringUtils.isBlank(questionDto.getTag())) {
            return new ArrayList<>();
        }

        String[] tags = StringUtils.split(questionDto.getTag(), ",");
        String regexpTags = String.join("|", tags);
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTag(regexpTags);

        List<Question> relatedQuestions = questionExtMapper.selectRelated(question);
        List<QuestionDto> questionDtoList = relatedQuestions.stream().map(question1 -> {
            QuestionDto questionDtoTemp = new QuestionDto();
            BeanUtils.copyProperties(question1,questionDtoTemp);

            return questionDtoTemp;
        }).collect(Collectors.toList());

        return questionDtoList;
    }
}
