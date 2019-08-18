package com.gxh.community.service;

import com.gxh.community.dto.CommentDTO;
import com.gxh.community.enums.CommentTypeEnum;
import com.gxh.community.exception.CustomizeErrorCode;
import com.gxh.community.exception.CustomizeException;
import com.gxh.community.mapper.CommentMapper;
import com.gxh.community.mapper.QuestionExtMapper;
import com.gxh.community.mapper.QuestionMapper;
import com.gxh.community.mapper.UserMapper;
import com.gxh.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbQuestion == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            dbQuestion.setCommentCount(1);
            questionExtMapper.incComment(dbQuestion);
        }
    }

    public List<CommentDTO> listByQuestionId(Long id) {
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(example);

        //如果comments为空，则返回一个空list
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //获取去掉重复的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        ArrayList<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人，并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment 为commentDTO
        comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return null;
    }
}
