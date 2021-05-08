package com.platform.controller;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("freemarker")
public class FreemarkerController {

    @GetMapping("test")
    public String getTemplate() throws IOException, TemplateException {
        String templateValue = "<html><span>${name}<span><html>";
        Configuration configuration = configuration();
        Map<String, Object> root = new HashMap<>(4);
        root.put("name", "你好");
        StringWriter stringWriter = new StringWriter();
        Template template = new Template("test-template", templateValue, configuration);
        template.process(root, stringWriter);
        System.out.println(stringWriter.toString());
        return stringWriter.toString();
    }

    private Configuration configuration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }
}
