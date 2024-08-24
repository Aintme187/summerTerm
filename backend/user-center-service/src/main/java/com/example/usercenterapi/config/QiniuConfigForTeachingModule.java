package com.example.usercenterapi.config;

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

    @Value("xdfPX4CjTgI9pzLfNa2gTnTYLdNI9EdLZU_y4uYg")
    private String accessKey;

    @Value("ZE0DCzmUjYaFQqmjFbyb_i4aeH9ZYNgAUDS00QbE")
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
