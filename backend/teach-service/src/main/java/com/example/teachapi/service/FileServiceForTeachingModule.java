package com.example.teachapi.service;

/**
 * @author 15252
 */
public interface FileServiceForTeachingModule {

    String generateDownloadUrl(String fileUrl);
    String generateDownloadUrlForArticle(String fileUrl);
}
