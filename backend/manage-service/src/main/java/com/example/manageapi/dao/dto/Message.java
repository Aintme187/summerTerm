package com.example.manageapi.dao.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Message {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String sendNickname;
    private String sendAvatar;
    private String sendContent;
    private String articleTitle;
    private Long articleId;
    private Long toUid;
    private String createDate;
}
