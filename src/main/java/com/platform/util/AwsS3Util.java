package com.platform.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;



/**
 *  @author: michael.jiang
 *  @Date: 2021/5/12 14:58
 *  @Description: AwsS3工具类
 */
@Component
@Slf4j
public class AwsS3Util {

    @Value("${s3.accessKeyID}")
    private String accessKeyID;

    @Value("${s3.secretKey}")
    private String secretKey;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.region}")
    private String region;

    @Autowired
    private AmazonS3 amazonS3;


    /**
     * @description s3上传文件
     * @author  michael.jiang
     * @param multipartFile
     * @return  java.lang.String
     * @date    2021/5/12 15:51
     */
    public String S3upload(MultipartFile multipartFile){
        String localFileName;
        String originalFilename = multipartFile.getOriginalFilename();
        if (multipartFile.isEmpty()) {
            return "上传文件不能为空";
        }
        if(StringUtils.isEmpty(originalFilename)){
            localFileName = UUID.randomUUID() + "." + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1)+"xlsx";
        }else{
            //生成随机文件名   localFileName
            localFileName = UUID.randomUUID() + "." + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        }

        Date date = new Date();
        SimpleDateFormat formatter_yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatter_MM = new SimpleDateFormat("MM");
        //在随机名前加上年月
        String s3FileFullPath = formatter_yyyy.format(date) + "/" + formatter_MM.format(date) + "/" + localFileName;

        ObjectMetadata metadata  = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        try {
            //开始上传文件
            //s3FileFullPath作为该文件的唯一标识符key
            PutObjectResult putResult = amazonS3.putObject(bucketName, s3FileFullPath, multipartFile.getInputStream(), metadata);
            log.info("【流方式】上传MumbaiS3完成，md5：{}，S3文件key：{}",putResult.getETag(),s3FileFullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回文件在S3上的key
        return s3FileFullPath;
    }


    /**
     * @description s3文件下载
     * @author  michael.jiang
     * @param key
     * @param targetFilePath
     * @return  void
     * @date    2021/5/12 15:52
     */
    public void S3Download(String key,String targetFilePath){
        //获取S3Client对象
        S3Object object = amazonS3.getObject(new GetObjectRequest(bucketName,key));
        if(object!=null){
            System.out.println("Content-Type: " +object.getObjectMetadata().getContentType());
            InputStream input = null;
            FileOutputStream fileOutputStream = null;
            byte[] data;
            try {
                //获取文件流
                input=object.getObjectContent();
                data = new byte[input.available()];
                int len = 0;
                fileOutputStream = new FileOutputStream(targetFilePath);
                while ((len = input.read(data)) != -1) {
                    fileOutputStream.write(data, 0, len);
                }
                System.out.println("下载文件成功");
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(fileOutputStream!=null){
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(input!=null){
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
