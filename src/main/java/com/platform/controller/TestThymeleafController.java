package com.platform.controller;

import com.platform.util.HTMLTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("thymeleaf")
public class TestThymeleafController {

    @GetMapping("test")
    public String getTemplate(){
        String template = "<html><span th:text=\"${title}\"></span></html>";
        Map params = new HashMap<>();
        params.put("title", "测试指标");
        String output = HTMLTemplateUtils.render(template, params);
        System.out.println(output);
        return output;
    }
}
