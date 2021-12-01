package com.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableElasticsearchRepositories(basePackages = "com.platform")
public class SpringbootPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootPlatformApplication.class, args);
    }

}
