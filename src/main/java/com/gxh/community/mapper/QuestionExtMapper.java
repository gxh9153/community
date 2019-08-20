package com.gxh.community.mapper;

import com.gxh.community.model.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
}