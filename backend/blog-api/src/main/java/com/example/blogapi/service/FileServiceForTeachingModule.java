package com.example.blogapi.service;

import com.example.blogapi.vo.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 15252
 */
public interface FileServiceForTeachingModule {

    String generateDownloadUrl(String fileUrl);
    String generateDownloadUrlForArticle(String fileUrl);
}
