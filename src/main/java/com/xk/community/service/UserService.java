package com.xk.community.service;

import com.xk.community.mapper.UserMapper;
import com.xk.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void CreateOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccount_id());
        if (dbUser == null){
            //插入
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }
        else {
            //更新用户信息
            dbUser.setGmt_modified(System.currentTimeMillis());
            dbUser.setAvatar_url(user.getAvatar_url());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());

            userMapper.update(dbUser);
        }
    }
}
