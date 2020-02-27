package com.xk.community.mapper;

import com.xk.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question (title, description, gmt_create, gmt_modified, creator, tag)\n" +
            "                       VALUES\n" +
            "                       (#{title}, #{description}, #{gmt_create}, #{gmt_modified}, #{creator}, #{tag})")
    void create(Question question);

    /**
     * 
     * @param offset 数据库中的偏移量，如offset=5，则从第6个开始选择
     * @param size 数据库中从offset起始可以选择的范围
     * @return
     */
    @Select("SELECT *\n" +
            "FROM question\n" +
            "limit #{offset}, #{size}")
    List<Question> list(Integer offset, Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = ${userId} limit #{offset}, #{size}")
    List<Question> listByUserId(Integer userId, Integer offset, Integer size);

    @Select("select count(1) from question where creator = ${userId}")
    Integer countByUserId(Integer userId);

    @Select("select * from question where id = ${id}")
    Question getById(Integer id);

    @Update("UPDATE question SET title=#{title}, description=#{description}, gmt_modified=#{gmt_modified}, tag=#{tag}\n" +
            "WHERE id = #{id}")
    void update(Question question);
}
