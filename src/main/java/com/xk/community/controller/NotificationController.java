package com.xk.community.controller;

import com.xk.community.dto.NotificationDto;
import com.xk.community.enums.NotificationTypeEnum;
import com.xk.community.model.User;
import com.xk.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest httpServletRequest, @PathVariable(name = "id") Long id) {

        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user == null) return "redirect:/";

        NotificationDto notificationDto = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDto.getType() ||
                NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDto.getType()) {
            return "redirect:/question/" + notificationDto.getOuter_id();
        }
        else {
            return "redirect:/";
        }

    }
}
