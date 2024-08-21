package com.example.teachapi.config;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Keith
 */
@Configuration
public class QiniuConfigForTeachingModule {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Bean
    public Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public UploadManager getUploadManager() {
        return new UploadManager(new com.qiniu.storage.Configuration());
    }
}
