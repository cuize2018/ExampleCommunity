package com.xk.community.controller;

import com.xk.community.dto.AccessTokenDto;
import com.xk.community.dto.GithubUser;
import com.xk.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.redirect.url}")
    private String redirect_url;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest httpServletRequest){

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirect_url);

        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);

        if (user != null){
            //登录成功，写cookie和session
            httpServletRequest.getSession().setAttribute("user", user);
            return "redirect:/";//使用"redirect"前缀，此时重新调用index页面
        }
        else {
            //登录失败，重新登录
            return "redirect:/";
        }
//        return "index";//不使用"redirect"前缀，此时仅仅重新渲染index页面，不修改地址
    }

}
