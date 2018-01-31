package com.xinguang.xusercenter.mapper;

import com.xinguang.xusercenter.entity.Question;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by yangsh
 */
public interface QuestionMapper {

    /**
     * 获取问题
     * @return
     */
    @Select("SELECT * FROM t_question WHERE id=#{0}")
    Question getQuestionById(Integer id);

    /**
     * 获取问题列表
     * @return
     */
    @Select("SELECT * FROM t_question")
    List<Question> getList();

}
