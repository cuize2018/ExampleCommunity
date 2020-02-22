package com.xk.community.controller;

import com.xk.community.mapper.QuestionMapper;
import com.xk.community.mapper.UserMapper;
import com.xk.community.model.Question;
import com.xk.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.Cookie;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    //若是GET方法则渲染页面
    @GetMapping("/publish")
    public String publish(){


        return "publish";
    }

    //若是POST方法则提交表单
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
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
        User user = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie: cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                //数据库中是否能根据此token查询到user信息
                user = userMapper.findByToken(token);

                if (user != null){
                    //若数据库中存在user信息，则把user信息写入session
                    httpServletRequest.getSession().setAttribute("user",user);
                }
                break;
            }
        }
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
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        //将表单信息添加进数据库
        questionMapper.create(question);

        return "redirect:/";
    }

}
