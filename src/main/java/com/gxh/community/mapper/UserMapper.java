package com.gxh.community.mapper;

import com.gxh.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user (id,account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) values(#{id},#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    public void insertUser(User user);

    @Select("select * from user where token = #{token}")
    public User findUserByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    public User findUserById(@Param("id") Integer id);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id = #{id}")
    void update(User user);
}
