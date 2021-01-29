package com.platform.service.impl;//package com.chinaunicom.service.impl;
//
//import com.chinaunicom.entity.User;
//import com.chinaunicom.service.IUserTokenService;
//import com.chinaunicom.util.MD5Util;
//import com.chinaunicom.util.RedisUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author jianghy
// * @Description: 用户token校验接口实现类
// * @date 2020/5/12 12:47
// */
//@Service
//public class UserTokenServiceImpl implements IUserTokenService {
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//
//    /**
//     * @Description: 设置用户token
//     * @param user 1
//     * @return java.util.Map<java.lang.String,java.lang.String>
//     * @throws
//     * @author jianghy
//     * @date 2020/5/12 13:02
//     */
//    @Override
//    public Map<String, String> setUserToken(User user) {
//        Map<String, String> map = new HashMap<>();
//        Map<String,Object> userMap = new HashMap<>();
//        userMap.put("user",user);
//        //设置规则  时间戳（13位）+用户id 进行md5加密;
//        String token = MD5Util.encode("123456");
//        boolean hmset = redisUtil.hmset(token, userMap,60);
//        if (hmset){
//            map.put("respCode","0000");
//            map.put("respMsg","设置token成功");
//            map.put("token",token);
//        }else {
//            map.put("respCode","0001");
//            map.put("respMsg","设置token失败");
//        }
//        return map;
//    }
//
//
//    /**
//     * @Description: 根据用户token获取用户信息
//     * @param token 1
//     * @return java.util.Map<java.lang.String,java.lang.Object>
//     * @throws
//     * @author jianghy
//     * @date 2020/5/12 13:32
//     */
//    @Override
//    public Map<String, Object> getUserToken(String token) {
//        Map<String, Object> map = new HashMap<>();
//        Map<Object, Object> hmget = redisUtil.hmget(token);
//        for (Map.Entry<Object, Object> entry : hmget.entrySet()) {
//            String key = entry.getKey().toString();
//            map.put(key,entry.getValue());
//        }
//        return map;
//    }
//
//
//    /**
//     * @Description: 校验token成功
//     * @param token 1
//     * @return java.util.Map<java.lang.String,java.lang.String>
//     * @throws
//     * @author jianghy
//     * @date 2020/5/12 13:39
//     */
//    @Override
//    public boolean checkUserToken(String token) {
//        Map<String, String> map = new HashMap<>();
//       // 判断token是否存在
//        Map<Object, Object> hmget = redisUtil.hmget(token);
//        if (hmget != null && hmget.size() > 0){
//            long expire = redisUtil.getExpire(token);
//            if (expire > 0){
//                redisUtil.expire(token,60);
//                return true;
//            }
//        }else{
//            map.put("respCode","0001");
//            map.put("respMsg","校验token失败");
//        }
//        return false;
//    }
//}
