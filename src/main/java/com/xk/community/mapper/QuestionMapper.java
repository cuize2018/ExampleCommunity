package com.xk.community.mapper;

import com.xk.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question (title, description, gmt_create, gmt_modified, creator, tag)\n" +
            "                       VALUES\n" +
            "                       (#{title}, #{description}, #{gmt_create}, #{gmt_modified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("SELECT *\n" +
            "FROM question")
    List<Question> list();
}
