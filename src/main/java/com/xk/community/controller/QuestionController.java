package com.xk.community.controller;

import com.xk.community.dto.CommentDto;
import com.xk.community.dto.QuestionDto;
import com.xk.community.enums.CommentTypeEnum;
import com.xk.community.service.CommentService;
import com.xk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDto questionDto = questionService.getById(id);
        List<CommentDto> commentDtos = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);

        model.addAttribute("questionInfo", questionDto);
        model.addAttribute("commentInfo", commentDtos);
        return "question";
    }


}
