package com.platform.common.base;

import com.platform.resp.UserLoginRes;

/**
 * @Description: 公用类
 * @author jianghy
 * @date 2020/8/4 10:33
 */
public class SystemThreadLocal {

    /**
     * 设定当前登录用户
     */
    private static final ThreadLocal<UserLoginRes> USER_LOGIN_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设定当前登录用户
     */
    public static void set(UserLoginRes system) {
        USER_LOGIN_THREAD_LOCAL.set(system);
    }

    /**
     * 取得登录的用户
     */
    public static UserLoginRes get() {
        return USER_LOGIN_THREAD_LOCAL.get();
    }

    /**
     * 删除登录的用户
     */
    public static void remove() {
        USER_LOGIN_THREAD_LOCAL.remove();
    }
}
