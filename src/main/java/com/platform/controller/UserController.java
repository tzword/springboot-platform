package com.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.annotation.AccessLimit;
import com.platform.common.base.CurdController;
import com.platform.common.domain.AjaxResult;
import com.platform.common.sysenum.ResultTypeEnum;
import com.platform.entity.User;
import com.platform.service.IUserService;
import com.platform.service.IUserTokenService;
import com.platform.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  控制器
 * </p>
 *
 * @author jianghy
 * @since 2020-04-28
*/
@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "用户管理", tags = {"用户管理"})
public class UserController extends CurdController<User> {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserTokenService iUserTokenService;
    /**
     * @Description: 测试redis
     * @param  1 
     * @return java.lang.String 
     * @throws
     * @author jianghy
     * @date 2020/4/28 15:55 
     */
    @PostMapping("/redis")
    @ApiOperation(value = "测试redis", notes = "测试redis")
    public String testRedis() {
        // 访问地址http://127.0.0.1:8080/user/redis   ， 如果输出“redis加载成功”说明成功了
        Object myTest = redisUtil.get("myTest");
        if (null != myTest) {
            return myTest.toString();
        }
        return null;
    }

    /**
     * @Description: 备数据源
     * @param  1 
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/30 12:32 
     */
    @PostMapping("/testSlaveDataSource")
    @ApiOperation(value = "备数据源", notes = "备数据源")
    public AjaxResult testSlaveDataSource() {
        return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(),ResultTypeEnum.BIZ_SUCCESS.getDesc(),iUserService.testSlaveGetById(7));
    }


    /**
     * @Description: 主数据源
     * @param  1
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/30 12:32
     */
    @PostMapping("/testMasterDataSource")
    @ApiOperation(value = "主数据源", notes = "主数据源")
    public AjaxResult testMasterDataSource() {
        return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(),ResultTypeEnum.BIZ_SUCCESS.getDesc(),iUserService.testMasterGetById(7));
    }

    @PostConstruct
    public void init() {
        redisUtil.set("myTest", "redis加载成功");
    }


    /**
     * @Description: 用户登录
     * @param  1 
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/5/12 13:58 
     */
    @PostMapping("/testLogin")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public AjaxResult login(){
        //校验用户名和密码和验证码，略
        //校验token
        User user = new User();
        user.setId(111);
        user.setName("张三");
        Map<String, String> stringStringMap = iUserTokenService.setUserToken(user);
        String token = stringStringMap.get("token");
        return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(),ResultTypeEnum.BIZ_SUCCESS.getDesc(),token);
    };

    
    /**
     * @Description: 测试接口
     * @param  1 
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/5/13 10:02
     */
    @PostMapping("/testApi")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    public AjaxResult testApi(@RequestBody JSONObject jsonObject){
        String token = jsonObject.getString("token");
        String name = jsonObject.getString("name");
        boolean status = iUserTokenService.checkUserToken(token);
        if (status){
            log.info("校验token成功");
            //填写业务逻辑
            return AjaxResult.success();
        }else{
            return AjaxResult.error();
        }

    };


    /**
     * @Description: 测试限流
     * @param  1 
     * @return com.platform.common.domain.AjaxResult 
     * @throws
     * @author jianghy
     * @date 2021/2/5 17:05
     */
    @AccessLimit(seconds=5, maxCount=5, needLogin=false)
    @RequestMapping("/testLimit")
    public AjaxResult testLimit(){
        return AjaxResult.success();
    }
    
}
