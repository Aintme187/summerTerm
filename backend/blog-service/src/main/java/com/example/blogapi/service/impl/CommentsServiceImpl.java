package com.example.blogapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blogapi.dao.mapper.ArticleMapper;
import com.example.blogapi.dao.mapper.CommentMapper;
import com.example.blogapi.dao.mapper.MessageMapper;
import com.example.blogapi.dao.mapper.UserMessageMapper;
import com.example.blogapi.dao.pojo.*;
import com.example.blogapi.service.CommentsService;
import com.example.blogapi.service.SysUserService;
import com.example.blogapi.service.UserClient;
import com.example.blogapi.utils.UserThreadLocal;
import com.example.blogapi.vo.CommentVo;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.UserVo;
import com.example.blogapi.vo.params.CommentParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;


@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMessageMapper userMessageMapper;

    @Autowired
    private UserClient userClient;

    public CommentVo copy(Comment comment){
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //时间格式化
        commentVo.setCreateDate(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        Long authorId = comment.getAuthorId();
        Result result = userClient.getMyInfo(authorId);
        Object data = result.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        SysUser sysUser = objectMapper.convertValue(data, SysUser.class);

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);

        commentVo.setAuthor(userVo);
        //评论的评论
        List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
        commentVo.setChildrens(commentVoList);
        if (comment.getLevel() > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments = this.commentMapper.selectList(queryWrapper);
        return copyList(comments);
    }

    public List<CommentVo> copyList(List<Comment> commentList){
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    @Override
    public Result commentsByArticleId(Long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return Result.success(copyList(comments));
    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Long sysUserId = sysUser.getId();

        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        comment.setAuthorId(sysUserId);
        comment.setCreateDate(new Date(System.currentTimeMillis()));

        Long parent = commentParam.getParent();
        if(parent == null || parent == 0){
            comment.setLevel(1);
        }
        else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);

        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);

        commentMapper.insert(comment);


        /*
        封装消息实体和用户消息关联实体
         */
        Message message = new Message();
        Article article = articleMapper.selectById(commentParam.getArticleId());

        message.setSendAvatar(sysUser.getAvatar());
        message.setSendNickname(sysUser.getNickname());
        int contentLength = comment.getContent().length();
        message.setSendContent(comment.getContent().substring(0, min(20, contentLength)));
        message.setArticleTitle(article.getTitle());
        message.setArticleId(article.getId());
        message.setToUid(comment.getToUid());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        message.setCreateDate(simpleDateFormat.format(new Date(System.currentTimeMillis())));

        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(commentParam.getToUserId());
        userMessage.setMessageId(-1L);

        userMessageMapper.insert(userMessage);

        messageMapper.insert(message);

        userMessage.setMessageId(message.getId());
        userMessageMapper.updateById(userMessage);

        return Result.success(comment);
    }
}