package com.gxh.community.service;

import com.gxh.community.dto.CommentDTO;
import com.gxh.community.enums.CommentTypeEnum;
import com.gxh.community.enums.NotificationEnum;
import com.gxh.community.enums.NotificationStatusEnum;
import com.gxh.community.exception.CustomizeErrorCode;
import com.gxh.community.exception.CustomizeException;
import com.gxh.community.mapper.*;
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
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
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
            Question dbQuestion = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if(dbQuestion == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentMapper.insert(comment);
            commentExtMapper.incComment(parentComment);
            //创建通知
            getNotify(comment, dbComment.getCommentator(),commentator.getName(), dbQuestion.getTitle(), NotificationEnum.REPLY_COMMENT, dbQuestion.getId());
        }else{
            //回复问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbQuestion == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            dbQuestion.setCommentCount(1);
            questionExtMapper.incComment(dbQuestion);
            //创建通知
            getNotify(comment, dbQuestion.getCreator(), commentator.getName(), dbQuestion.getTitle(), NotificationEnum.REPLY_QUESTION, dbQuestion.getId());
        }
    }

    private void getNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationEnum notificationEnum, Long outerId) {
        if(receiver == comment.getCommentator()){
            return ;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationEnum.getType());
        notification.setNotifier(comment.getCommentator());
        notification.setOuterId(outerId);
        notification.setStatus(NotificationStatusEnum.UNREAD.getType());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);;
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum typeEnum) {
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(typeEnum.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);

        //如果comments为空，则返回一个空list
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //获取去掉重复的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人，并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment 为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
