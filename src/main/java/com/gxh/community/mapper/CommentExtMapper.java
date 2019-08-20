package com.gxh.community.mapper;
import com.gxh.community.model.Comment;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentExtMapper {
    int incComment(Comment comment);
}