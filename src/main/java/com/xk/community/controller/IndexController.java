package com.xk.community.controller;

import com.xk.community.dto.QuestionDto;
import com.xk.community.mapper.QuestionMapper;
import com.xk.community.mapper.UserMapper;
import com.xk.community.model.Question;
import com.xk.community.model.User;
import com.xk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest,
                        Model model){
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null && cookies.length != 0) {
            //在cookie中查询是否有token且token是否在数据库中
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //数据库中是否能根据此token查询到user信息
                    User user = userMapper.findByToken(token);

                    if (user != null) {
                        //若数据库中存在user信息，则把user信息写入session
                        httpServletRequest.getSession().setAttribute("user", user);
                    }

                    break;
                }
            }
        }
        //显示处问题列表，把问题列表加入model
        //使用SpringBoot的Service来组装
        List<QuestionDto> questions = questionService.list();
        model.addAttribute("questions", questions);
        return "index";
    }
}
