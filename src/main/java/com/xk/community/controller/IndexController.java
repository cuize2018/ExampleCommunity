package com.xk.community.controller;

import com.xk.community.dto.PageDto;
import com.xk.community.mapper.UserMapper;
import com.xk.community.model.User;
import com.xk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    /**
     *
     * @param httpServletRequest request请求
     * @param model 前后端交互的模型
     * @param page 当前页码
     * @param size 每一页可以显示的最大页面数目
     * @return 跳转到的页面地址
     */
    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
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
        //显示页面信息，把有页面信息加入model
        //使用SpringBoot的Service来组装
        PageDto pageInfo = questionService.list(page, size);

        model.addAttribute("pageInfos", pageInfo);
        return "index";
    }
}
