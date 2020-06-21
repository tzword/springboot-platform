package com.platform.service;

import com.platform.entity.User;

import java.util.Map;

/**
 * @author jianghy
 * @Description: 用户token校验服务
 * @date 2020/5/12 12:38
 */
public interface IUserTokenService {

    // 设置用户token
    public Map<String, String> setUserToken(User user);

    // 根据token获取用户信息
    public Map<String, Object> getUserToken(String token);

    // 校验token
    public boolean checkUserToken(String token);

}
