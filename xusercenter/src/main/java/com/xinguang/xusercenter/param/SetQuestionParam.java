package com.xinguang.xusercenter.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh
 */
@Getter
@Setter
public class SetQuestionParam {

    private String id; // 安全ID = 用户ID
    private Integer questiononeId; // 问题1ID
    private String answerone; // 问题1答案
    private String questiononePrompt; // 问题1提示
    private Integer questiontwoId; // 问题2ID
    private String answertwo; // 问题2答案
    private String questiontwoPrompt; // 问题2提示

}
