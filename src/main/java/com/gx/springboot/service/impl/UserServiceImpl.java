package com.gx.springboot.service.impl;

import com.gx.springboot.domain.UserInfo;
import com.gx.springboot.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guoxing
 * @version V1.0
 * @Package com.gx.springboot.service
 * @date 2021/1/27 15:48
 */
@Service
public class UserServiceImpl implements IUserService {


    @Override
    public UserInfo findUserByUserName(String userName) {

        List<UserInfo> users = findAll();
        for(UserInfo user : users){
            if(user.getUsername().equals(userName)){
                return user;
            }
        }
        return null;

    }

    @Override
    public List<UserInfo> findAll(){

        List<UserInfo> users = new ArrayList<>(2);
        List<String> cat = new ArrayList<>(2);
        cat.add("sing");
        cat.add("rap");
        List<String> dog = new ArrayList<>(2);
        dog.add("jump");
        dog.add("basketball");
        users.add(new UserInfo("zhangsan","123",true,"cat",cat));
        users.add(new UserInfo("lisi","123",true,"dog",dog));
        return users;
    }
}
