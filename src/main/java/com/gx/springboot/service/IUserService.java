package com.gx.springboot.service;

import com.gx.springboot.domain.UserInfo;

import java.util.List;

/**
 * @author guoxing
 * @version V1.0
 * @Package com.gx.springboot.service
 * @date 2021/1/27 15:47
 */
public interface IUserService {

    public UserInfo findUserByUserName(String userName);

    public List<UserInfo> findAll();
}
