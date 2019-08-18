package com.gxh.community.controller;

import com.gxh.community.dto.CommentCreateDTO;
import com.gxh.community.dto.ResultDTO;
import com.gxh.community.exception.CustomizeErrorCode;
import com.gxh.community.model.Comment;
import com.gxh.community.model.User;
import com.gxh.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


//@RequestBody的作用是将前台发送过来的固定格式的数据（xml或json）封装为对应的JavaBean对象
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
        commentService.insert(comment);
        return ResultDTO.successOf();
    }
}
