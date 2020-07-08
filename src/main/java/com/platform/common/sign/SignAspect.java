package com.platform.common.sign;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.domain.AjaxResult;
import com.platform.common.sysenum.ResultTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;


/**
 * @Description:验签切面类
 * @author jianghy
 * @date 2020/7/5 13:41 
 */
@Aspect
@Component
@Slf4j
public class SignAspect{

    /**
     * @Description:织入
     * @author jianghy
     * @date 2020/7/5 13:41 
     */
    @Pointcut("@annotation(com.platform.common.sign.SginAnot)")
    public void logPointCut() {
    }

    /**
     * @Description: 前置通知
     * @param joinPoint 1 
     * @return void 
     * @throws
     * @author jianghy
     * @date 2020/7/5 13:42 
     */
    @Before(value = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        OutputStream output = null;
        try {
            String className = joinPoint.getTarget().getClass().getName();
            log.info("前置通知==@Before=============类的名称：{}", className);
            // 获取方法签名
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Object[] args = joinPoint.getArgs();
            Object arg = args[0];
            boolean check = false;
            if (method != null) {
                SginAnot annotation = method.getAnnotation(SginAnot.class);
                if (null != annotation) {
                    SginEnum sginEnum = annotation.type();
                    if (sginEnum.getValue() == 1) {//MD5
                        check = MD5.check(arg);
                    }
                    if (sginEnum.getValue() == 2) {//SHA1
                        check = SHA1.check(arg);
                    }
                }
            }
            if (!check){
                output = response.getOutputStream();
                output.write(JSONObject.toJSONString(AjaxResult.errorMsg(ResultTypeEnum.SYS_SIGN_ERROR.getValue(),
                        ResultTypeEnum.SYS_SIGN_ERROR.getDesc())).getBytes("UTF-8"));
            }
        }catch (Exception e){
            log.error("校验参数异常: {}" + e.getMessage());
        }finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

    }
}