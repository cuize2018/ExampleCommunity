package com.xk.community.controller;

import com.xk.community.dto.QuestionDto;
import com.xk.community.mapper.QuestionMapper;
import com.xk.community.model.Question;
import com.xk.community.model.User;
import com.xk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    //若是GET方法则渲染页面
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,Model model){
        QuestionDto question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());

        return "publish";
    }

    //若是POST方法则提交表单
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam(value = "id", required = false) Integer id,
                            HttpServletRequest httpServletRequest,
                            Model model){

        //将信息添加到model中用于错误之后回写表单
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        //验证信息是否为空，若为空则返回到publish页面，同时显示error信息
        if (title==null||title.equals("")){
            model.addAttribute("error","标题不可为空");
            return "publish";
        }  if (description==null||description.equals("")){
            model.addAttribute("error","问题描述不可为空");
            return "publish";
        }  if (tag==null||tag.equals("")){
            model.addAttribute("error","标签不可为空");
            return "publish";
        }

        //验证用户是否登录
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        //若用户未登录，则返回publish同时显示错误信息
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        //把信息添加到Question对象
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        //将表单信息添加进数据库

        questionService.createOrUpdate(question);

        return "redirect:/";
    }

}
