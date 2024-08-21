package com.example.blogapi.service.impl;

import com.example.blogapi.service.FileServiceForTeachingModule;
import com.example.blogapi.utils.QiniuUtilsForTeaching;
import com.example.blogapi.utils.StringUtilsForTeaching;
import com.example.blogapi.vo.Result;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Keith
 */
@Service
public class FileServiceForTeachingModuleImpl implements FileServiceForTeachingModule {

    @Autowired
    private QiniuUtilsForTeaching qiniuUtilsForTeaching;

    @Autowired
    private Auth auth;

    @Value("seicj6zi6.hb-bkt.clouddn.com")
    private String domainForArticle;

    @Value("${qiniu.path}")
    private String domain;

    @Autowired
    private UploadManager uploadManager;

    @Override
    public String generateDownloadUrl(String fileUrl) {
        DownloadUrl url = new DownloadUrl(domain, false, fileUrl);

        long expireInSeconds = 3600;
        long deadline = System.currentTimeMillis() / 1000 + expireInSeconds;

        String urlString = null;
        try {
            urlString = url.buildURL(auth, deadline);
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }

        return urlString;
    }

    @Override
    public String generateDownloadUrlForArticle(String fileUrl) {
        DownloadUrl url = new DownloadUrl(domainForArticle, false, fileUrl);

        long expireInSeconds = 3600;
        long deadline = System.currentTimeMillis() / 1000 + expireInSeconds;

        String urlString = null;
        try {
            urlString = url.buildURL(auth, deadline);
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }

        return urlString;
    }

}
