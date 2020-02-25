package com.xk.community.controller;

import com.xk.community.dto.PageDto;
import com.xk.community.mapper.UserMapper;
import com.xk.community.model.User;
import com.xk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          HttpServletRequest httpServletRequest,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        User user = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length != 0) {
            //在cookie中查询是否有token且token是否在数据库中
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //数据库中是否能根据此token查询到user信息
                     user = userMapper.findByToken(token);

                    if (user != null) {
                        //若数据库中存在user信息，则把user信息写入session
                        httpServletRequest.getSession().setAttribute("user", user);
                    }

                    break;
                }
            }
        }
        if (user == null)return "redirect:/";

        if (action.equals("questions")){
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
        }
        else if (action.equals("replies")){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        PageDto userPageInfo = questionService.list(user.getId(), page, size);
        model.addAttribute("userPageInfos", userPageInfo);
        return "profile";
    }
}
