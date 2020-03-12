package com.xk.community.controller;

import com.xk.community.dto.PageDto;
import com.xk.community.model.User;
import com.xk.community.service.NotificationService;
import com.xk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    /**
     *
     * @param model 前后端交互的模型
     * @param page 当前页码
     * @param size 每一页可以显示的最大页面数目
     * @return 跳转到的页面地址
     */
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search) {

        //显示页面信息，把有页面信息加入model
        //使用SpringBoot的Service来组装
        PageDto pageInfo = questionService.list(search, page, size);

        model.addAttribute("pageInfos", pageInfo);
        model.addAttribute("searchContent", search);
        return "index";
    }
}
