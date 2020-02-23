package com.xk.community.mapper;


import com.xk.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
