package com.xinguang.xusercenter.service;

import com.xinguang.xusercenter.common.base.ReturnData;
import com.xinguang.xusercenter.common.util.ReturnUtil;
import com.xinguang.xusercenter.entity.Question;
import com.xinguang.xusercenter.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangsh
 */
@Service
@Slf4j
public class QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    /**
     * 获取密保问题列表
     * @return
     */
    public ReturnData getQuestions() {
        List<Question> questions = questionMapper.getList();

        Map<String, Object> data = new HashMap<>();

        data.put("list", questions);

        return ReturnUtil.success(data);
    }

}
