package com.gxh.community.mapper;

import com.gxh.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (id,account_id,name,token,gmt_create,gmt_modified) values(#{id},#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    public void insertUser(User user);
    @Select("select * from user where token = #{token}")
    public User findUserByToken(String token);
}
