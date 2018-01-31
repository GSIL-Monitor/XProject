package com.dangdang.ddframe.job.lite.console.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh
 */
@Getter
@Setter
public class Page {

    private Integer pageNo; // 当前页
    private Integer pageSize; // 每页显示记录数
    private Integer totalRecord; // 总记录数
    private Integer totalPage; // 总页数
    private Boolean hasPre; // 是否有上一页
    private Boolean hasNext; // 是否有下一页
    private Integer[] pages; // 页码序列
    private Integer pagePre; // 上一页
    private Integer pageNext; // 下一页

}
