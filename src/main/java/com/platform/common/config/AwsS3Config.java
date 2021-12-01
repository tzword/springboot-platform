package com.platform.common.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {
    @Value("${s3.accessKeyID}")
    private String accessKeyID;

    @Value("${s3.secretKey}")
    private String secretKey;

    @Value("${s3.region}")
    private String region;

    @Bean
    public AmazonS3 amazonS3(){
        //创建访问凭证对象
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyID, secretKey);
        //创建s3对象
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                //设置服务器所属地区
                .withRegion(region)
                .build();
    }
}
