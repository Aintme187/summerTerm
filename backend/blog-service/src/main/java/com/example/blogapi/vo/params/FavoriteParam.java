package com.example.blogapi.vo.params;

import com.example.blogapi.vo.FavoriteVo;
import lombok.Data;

@Data
public class FavoriteParam {
    private Long articleId;
    private Long favorite;
}
