package com.gxh.community.controller;

import com.gxh.community.dto.CommentDTO;
import com.gxh.community.model.Comment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//@RequestBody的作用是将前台发送过来的固定格式的数据（xml或json）封装为对应的JavaBean对象
@RestController
public class CommentController {
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContext());
        comment.setType(commentDTO.getType());
        comment.setCommentator(19);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(0);
        return null;
    }
}
