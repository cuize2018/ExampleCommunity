package com.xk.community.advice;

import com.alibaba.fastjson.JSON;
import com.xk.community.dto.ResultDto;
import com.xk.community.exception.CustomizeErrorCode;
import com.xk.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义的异常处理
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    //ModelAndView, 一个渲染的页面
     ModelAndView handel(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        ResultDto resultDto;
        String contentType = request.getContentType();
        if (contentType == null) contentType = "text/html";

        //判断request的类型是json还是html页面
        if (contentType.equals("application/json")){
            //返回json
            if (ex instanceof CustomizeException){
                resultDto = ResultDto.errorOf((CustomizeException) ex);
            }
            else {
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        else{
            //错误页面跳转
            String error_message;
            if (ex instanceof CustomizeException){
                error_message = ex.getMessage();
            }
            else {
                error_message = CustomizeErrorCode.SYSTEM_ERROR.getMessage();
            }
            model.addAttribute("error_message", error_message);

            return new ModelAndView("error");
        }


    }
}
