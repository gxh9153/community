package com.gxh.community.mapper;

import com.gxh.community.model.Question;
import com.gxh.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
}