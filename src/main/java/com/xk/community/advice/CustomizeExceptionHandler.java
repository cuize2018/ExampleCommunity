package com.xk.community.advice;

import com.xk.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的异常处理
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    //ModelAndView, 一个渲染的页面
    public ModelAndView handel(Throwable ex, Model model) {
        String error_message="";
        if (ex instanceof CustomizeException){
            error_message += ex.getMessage();
        }
        else {
            error_message += "服务器冒烟了，请稍后再试!";
        }
        model.addAttribute("error_message", error_message);

        return new ModelAndView("error");
    }

    public HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
