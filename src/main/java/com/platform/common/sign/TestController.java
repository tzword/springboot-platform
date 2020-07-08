package com.platform.common.sign;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianghy
 * @Description: 测试
 * @date 2020/6/28 17:13
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/md5")
    @SginAnot(type = SginEnum.MD5)
    public String testSignMd5(@RequestBody TestEntity testEntity){
        System.out.println(testEntity.getSign());
        return "md5验签成功";
    }

    @RequestMapping("/sha1")
    @SginAnot(type = SginEnum.SHA1)
    public String testSignSha1(@RequestBody TestEntity testEntity){
        System.out.println(testEntity.getSign());
        return "sha1验签成功";
    }
}
