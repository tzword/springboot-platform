package com.platform.common.jg.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.google.gson.Gson;
import com.platform.common.jg.common.DASFileConstants;
import com.platform.entity.FileMeta;
import com.platform.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;


/**
 * @author: michael.jiang
 * @Date: 2021/5/12 14:58
 * @Description: AwsS3工具类
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

    @Value("${s3.filePath.prefix}")
    private String filePathPrefix;

    @Autowired
    private AmazonS3 amazonS3;


    /**
     * @param multipartFile
     * @return java.lang.String
     * @description s3上传文件
     * @author michael.jiang
     * @date 2021/5/12 15:51
     */
    public String S3upload(MultipartFile multipartFile) {
        String localFileName;
        String originalFilename = multipartFile.getOriginalFilename();
        if (multipartFile.isEmpty()) {
            return "上传文件不能为空";
        }
        if (StringUtils.isEmpty(originalFilename)) {
            localFileName = UUID.randomUUID() + "." + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1) + "pdf";
        } else {
            //生成随机文件名   localFileName
            localFileName = UUID.randomUUID() + "." + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        }

        //在随机名前加上年月
        String s3FileFullPath = DateUtil.getYear() + "/" + DateUtil.getMonth() + "/" + localFileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        try {
            //开始上传文件
            //s3FileFullPath作为该文件的唯一标识符key
            PutObjectResult putResult = amazonS3.putObject(bucketName, s3FileFullPath, multipartFile.getInputStream(), metadata);
            log.info("【流方式】上传MumbaiS3完成，md5：{}，S3文件key：{}", putResult.getETag(), s3FileFullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回文件在S3上的key
        return s3FileFullPath;
    }


    /**
     * 上传文件示例
     *
     * @param
     */
    public String uploadFile(String fileName, String filePath) {
        String uploadPath = "";
        try {
            File file = new File(filePath+fileName);

            //设置文件目录
            if (StringUtils.isEmpty(uploadPath)) {
                uploadPath = DateUtil.getYear() + "/" + DateUtil.getMonth() + "/";
            }
            //生成随机文件名
            String expandedName = file.getName().substring(file.getName().lastIndexOf("."));
            String key = uploadPath + UUID.randomUUID().toString() + expandedName;
            // 设置文件上传对象
            PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
            // 设置公共读取
//            request.withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, key);
            // 上传文件
            URL url = amazonS3.generatePresignedUrl(urlRequest);
            if (null != url){
                return key;
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 上传文件
     */
    public PutObjectResult uploadS3(FileMeta fileMeta) {
        // file
        String fName = fileMeta.getFileName();
        // file-meta name
        String fileName = "file" + DASFileConstants.S3_FILE_NAME_SP + fileMeta.getMarcoPoloMsgId() + DASFileConstants.S3_FILE_NAME_SP + fName.split("\\.")[0] + ".json";
        String file = filePathPrefix + "/" + DateUtil.todayPath() + "/" + fileMeta.getPublishDate() + "/" + fileName;
        String json = new Gson().toJson(Collections.singletonList(fileMeta));
        byte[] jsonByte = json.getBytes(StandardCharsets.UTF_8);
        InputStream stream = new ByteArrayInputStream(jsonByte);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(jsonByte.length);
        return amazonS3.putObject(bucketName, file, stream, metadata);
    }


    /**
     * @param key
     * @param targetFilePath
     * @return void
     * @description s3文件下载
     * @author michael.jiang
     * @date 2021/5/12 15:52
     */
    public void S3Download(String key, String targetFilePath) {
        //获取S3Client对象
        S3Object object = amazonS3.getObject(new GetObjectRequest(bucketName, key));
        if (object != null) {
            System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
            InputStream input = null;
            FileOutputStream fileOutputStream = null;
            byte[] data;
            try {
                //获取文件流
                input = object.getObjectContent();
                data = new byte[input.available()];
                int len = 0;
                fileOutputStream = new FileOutputStream(targetFilePath);
                while ((len = input.read(data)) != -1) {
                    fileOutputStream.write(data, 0, len);
                }
                System.out.println("下载文件成功");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (input != null) {
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
