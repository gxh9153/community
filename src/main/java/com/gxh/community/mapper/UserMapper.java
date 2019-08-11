package com.gxh.community.mapper;

import com.gxh.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user (id,account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) values(#{id},#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    public void insertUser(User user);

    @Select("select * from user where token = #{token}")
    public User findUserByToken(String token);

    @Select("select * from user where id = #{id}")
    public User findUserById(int creator);
}
