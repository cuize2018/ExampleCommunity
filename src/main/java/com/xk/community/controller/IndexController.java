package com.xk.community.controller;

import com.xk.community.mapper.UserMapper;
import com.xk.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();

        //在cookie中查询是否有token且token是否在数据库中
        for (Cookie cookie: cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                //数据库中是否能根据此token查询到user信息
                User user = userMapper.findByToken(token);

                if (user != null){
                    //若数据库中存在user信息，则把user信息写入session
                    httpServletRequest.getSession().setAttribute("user",user);
                }

                break;
            }
        }
        return "index";
    }
}
