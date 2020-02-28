package com.xk.community.service;

import com.xk.community.mapper.UserMapper;
import com.xk.community.model.User;
import com.xk.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void CreateOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccount_idEqualTo(user.getAccount_id());
        List<User> users = userMapper.selectByExample(userExample);

        if (users.isEmpty()){
            //插入
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }
        else {
            //更新用户信息
            User dbUser = users.get(0);

            User updateUser = new User();
            updateUser.setGmt_modified(System.currentTimeMillis());
            updateUser.setAvatar_url(user.getAvatar_url());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            //使用mybatis-generator插件方法完成
            UserExample userUpdateExample = new UserExample();
            userUpdateExample.createCriteria().andIdEqualTo(dbUser.getId());

            userMapper.updateByExampleSelective(updateUser, userUpdateExample);
        }
    }
}
