package com.gxh.community.mapper;

import com.gxh.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    //插入数据
    @Insert("insert into question(id,title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag)" +
            " values(#{id},#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void create(Question question);

    //根据分页查询数据
    @Select("select * from question limit #{offset},#{size}")
    public List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    //查询中记录数
    @Select("select count(1) from question")
    public Integer count();

    //通过userId查询问题
    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") int userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator = #{userid}")
    Integer countByUserId(int userid);
}
