package com.platform.controller;

import com.platform.common.annotation.NoLogin;
import com.platform.entity.TestBean;
import com.platform.service.TestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jianghy
 * @Description: 测试controller
 * @date 2020/11/13 11:10
 */

@RestController
@RequestMapping("/testes")
@Api(value = "测试es", tags = {"测试es"})
public class EsTestController {

    @Autowired
    TestService testService;

    @NoLogin
    @RequestMapping("findAll")
    public Iterable<TestBean> findAll() {

        return testService.findAll();
    }

    @NoLogin
    @RequestMapping("list")
    public String save() {
        List<TestBean> list = null;
        testService.save(list);
        return "success";
    }

    @NoLogin
    @RequestMapping("save")
    public void save(TestBean bean) {
        testService.save(bean);
    }

    @NoLogin
    @RequestMapping("findByName")
    public List<TestBean> findByName(String name) {
        return testService.findByName(name);
    }

    @NoLogin
    @RequestMapping("findByNameOrDesc")
    public List<TestBean> findByNameOrDesc(String text) {
        return testService.findByNameOrDesc(text);
    }

}
