package com.platform.controller;

import com.platform.util.AwsS3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("s3")
@Slf4j
public class AwsS3TestController {

    @Autowired
    private AwsS3Util awsS3Util;

    @RequestMapping("upload")
    public void upload(@RequestParam(name = "files") MultipartFile files){
        String key = awsS3Util.S3upload(files);
        log.info("key:{}",key);
    }

    @RequestMapping("download")
    public void download(@RequestParam(name = "key") String key,@RequestParam("targetPath") String targetPath){
        awsS3Util.S3Download(key,targetPath);
        log.info("下载成功");
    }
}
