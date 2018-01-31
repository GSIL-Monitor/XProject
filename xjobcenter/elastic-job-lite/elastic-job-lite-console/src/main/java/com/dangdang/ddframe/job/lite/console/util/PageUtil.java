package com.dangdang.ddframe.job.lite.console.util;

import com.dangdang.ddframe.job.lite.console.common.Constants;

/**
 * Created by yangsh
 */
public class PageUtil {

    /**
     * 获取总页数
     *
     * @param pageSize
     * @param totalRecord
     * @return
     */
    public static Integer getTotalPage(Integer pageSize, Integer totalRecord) {
        Integer totalPage = (pageSize + totalRecord + - 1) / pageSize;
        if (totalPage == 0) {
            totalPage = 1;
        }
        return totalPage;
    }

    /**
     * 获取是否有上一页
     *
     * @param pageNo
     * @return
     */
    public static Boolean getHasPre(Integer pageNo) {
        Boolean hasPre = true;
        if (pageNo == 1) {
            hasPre = false;
        }
        return hasPre;
    }

    /**
     * 获取是否有下一页
     *
     * @param pageNo
     * @return
     */
    public static Boolean getHasNext(Integer pageNo, Integer totalPage) {
        Boolean hasNext = true;
        if (pageNo == totalPage) {
            hasNext = false;
        }
        return hasNext;
    }

    /**
     * 获取分页序列
     *
     * @param pageNo
     * @param totalPage
     * @return
     */
    public static Integer[] getPages(Integer pageNo, Integer totalPage) {
        Integer start = pageNo - Constants.PAGE_STEP;
        Integer end = pageNo + Constants.PAGE_STEP;

        if (start < 1) {
            start = 1;
            end = 2*Constants.PAGE_STEP + 1;
            if (end > totalPage) {
                end = totalPage;
            }
        }

        if (end > totalPage) {
            start = totalPage - 2*Constants.PAGE_STEP;
            end = totalPage;
            if (start < 1) {
                start = 1;
            }
        }

        Integer[] pages = new Integer[end - start + 1] ;
        for (int i = start; i <= end; i++) {
            pages[i-start] = i;
        }

        return pages;
    }

}
