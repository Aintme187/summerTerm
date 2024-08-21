package com.example.teachapi.vo.params;

import com.example.teachapi.vo.CategoryVo;
import com.example.teachapi.vo.TagVo;
import lombok.Data;

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