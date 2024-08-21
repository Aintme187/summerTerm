package com.example.blogapi.vo.params;

import com.example.blogapi.vo.CategoryVo;
import com.example.blogapi.vo.TagVo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}