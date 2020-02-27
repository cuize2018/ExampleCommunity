package com.xk.community.mapper;


import com.xk.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (NAME, ACCOUNT_ID, TOKEN, GMT_CREATE, GMT_MODIFIED, avatar_url)\n" +
            "                       VALUES\n" +
            "                       (#{name}, #{account_id}, #{token}, #{gmt_create}, #{gmt_modified}, #{avatar_url})")
    void insert(User user);

    @Select("SELECT *\n" +
            "FROM user\n" +
            "WHERE token = #{token}\n")
    User findByToken(@Param("token") String token);

    @Select("SELECT *\n" +
            "FROM user\n" +
            "WHERE id = #{id}\n")
    User findById(@Param("id") Integer id);

    @Select("SELECT *\n" +
            "FROM user\n" +
            "WHERE account_id = #{account_id}\n")
    User findByAccountId(@Param("account_id") String account_id);

    @Update("UPDATE user SET name=#{name}, token=#{token}, gmt_modified=#{gmt_modified}, avatar_url=#{avatar_url}\n" +
            "WHERE account_id = #{account_id}")
    void update(User user);
}
