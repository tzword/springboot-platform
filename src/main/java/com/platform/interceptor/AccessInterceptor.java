package com.platform.interceptor;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.annotation.NoLogin;
import com.platform.common.base.SystemThreadLocal;
import com.platform.common.domain.AjaxResult;
import com.platform.common.sysenum.ResultTypeEnum;
import com.platform.entity.User;
import com.platform.mapper.UserMapper;
import com.platform.resp.UserLoginRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Description 用户登录拦截器
 * @Author zhangz145
 * @Date 2020/5/16 19:31
 */
@Slf4j
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("requestId", RandomUtil.randomString(10));
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");

        if (noLogin(handler)) { //不需要登陆
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            response.getWriter().write(JSON.toJSONString(AjaxResult.errorMsg(ResultTypeEnum.SYS_TOKEN_ERROR.getValue(), ResultTypeEnum.SYS_TOKEN_ERROR.getDesc())));
            return false;
        }
        UserLoginRes userRes = new UserLoginRes();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("token", token);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            response.getWriter().write(JSON.toJSONString(AjaxResult.errorMsg(ResultTypeEnum.SYS_TOKEN_ERROR.getValue(),
                    ResultTypeEnum.SYS_TOKEN_ERROR.getDesc())));
            return false;
        }
        userRes.setUser(user);

        //token失效时间1天
        if (isExpired(user.getTokenExpiredTime())) {
            response.getWriter().write(JSON.toJSONString(AjaxResult.errorMsg(ResultTypeEnum.SYS_TOKEN_EXPIRED_ERROR.getValue(),
                    ResultTypeEnum.SYS_TOKEN_EXPIRED_ERROR.getDesc())));
            return false;
        }
        SystemThreadLocal.set(userRes);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        SystemThreadLocal.remove();
    }

    public boolean noLogin(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod han = (HandlerMethod) handler;
            NoLogin noLogin = han.getMethodAnnotation(NoLogin.class);
            if (noLogin != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * TOKEN是否过期
     *
     * @param expireTime
     * @return * @return: boolean
     */
    public boolean isExpired(Date expireTime) {
        Date d = new Date();
        return d.getTime() > expireTime.getTime() ? true : false;
    }

}
