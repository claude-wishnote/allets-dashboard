package com.allets.backend.data.server.config;

import com.allets.backend.data.server.utils.AwsUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by claude on 2015/10/16.
 */
@Configuration
public class AWSUploadConfig {
    @Value("${aws.access.key}")
    String awsAccessKey;
    @Value("${aws.secret.key}")
    String awsSecretKey;
    @Value("${aws.bucket.name}")
    String awsBucketName;
    @Value("${file.upload.dir}")
    String fileUploadDir;
    @Value("${file.upload.file.name}")
    String fileUploadFileName;


    @Bean
    public AwsUpload awsUpload() {
        AwsUpload awsUpload = new AwsUpload(awsAccessKey,awsSecretKey,awsBucketName);
        awsUpload.setFileUploadDir(fileUploadDir);
        awsUpload.setFileUploadFileName(fileUploadFileName);
        return awsUpload;
    }
}
