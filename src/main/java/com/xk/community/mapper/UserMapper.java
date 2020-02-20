package com.xk.community.mapper;


import com.xk.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (NAME, ACCOUNT_ID, TOKEN, GMT_CREATE, GMT_MODIFIED)\n" +
            "                       VALUES\n" +
            "                       (#{name}, #{account_id}, #{token}, #{gmt_create}, #{gmt_modified})")
    void insert(User user);
}