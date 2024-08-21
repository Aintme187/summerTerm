package com.example.teachapi.controller;


import com.example.teachapi.utils.QiniuUtils;
import com.example.teachapi.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    /**
     * 1.requestParam 取出image参数
     * 2.利用文件原始名称构建UUID, 唯一文件名称
     * 3.上传文件到
     * @param file
     * @return
     */
    @PostMapping
        public Result upload(@RequestParam("image")MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(20001, "上传失败");
    }
}
