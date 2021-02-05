package com.platform.interceptor;

import com.alibaba.fastjson.JSON;
import com.platform.common.annotation.AccessLimit;
import com.platform.common.domain.AjaxResult;
import com.platform.common.sysenum.ResultTypeEnum;
import com.platform.mapper.UserMapper;
import com.platform.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author jianghy
 * @Description: 防刷拦截器
 * @date 2021/2/5 10:10
 */
@Slf4j
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        //判断请求是方法的请求
        if (handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            //获取方法中的注解，看看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null){
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            //如果需要登录
            if(needLogin){
               //获取登录的session进行判断
                //...
                key = "" +"1";//这里假设用户是1，项目中是动态获取userId
            }
            //从redis中获取用户的访问的次数
            AccessKey ak = AccessKey.withExpire(seconds);
            Integer count = (Integer) redisUtil.get(key);
            if (count == null){
                //第一次访问
                redisUtil.set(key,1,ak.getSeconds());
            }else if(count < maxCount){
                //加1
                redisUtil.incr(key,1);
            }else{
                //超出访问次数
                render(response,ResultTypeEnum.BIZ_REQUEST_LIMIT.getValue(),
                    ResultTypeEnum.BIZ_REQUEST_LIMIT.getDesc());
                return false;
            }
        }
        return true;
    }


    /**
     * @Description: 转发
     * @param response 1
     * @param code 2
     * @param msg 3
     * @return void
     * @throws
     * @author jianghy
     * @date 2021/2/5 17:13
     */
    private void render(HttpServletResponse response, String code, String msg) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(AjaxResult.errorMsg(code,msg));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

}
