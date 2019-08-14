package com.gxh.community.service;

import com.gxh.community.mapper.UserMapper;
import com.gxh.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbuser = userMapper.findUserById(user.getAccountId());
        if(dbuser == null){
            //如果没有就插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
        }else{
            //有就更新
            dbuser.setGmtModified(System.currentTimeMillis());
            dbuser.setToken(user.getToken());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            dbuser.setName(user.getName());
            userMapper.update(user);
        }
    }
}
