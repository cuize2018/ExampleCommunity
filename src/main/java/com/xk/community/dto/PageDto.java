package com.xk.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面信息数据结构，用于存储分页所用的全部信息
 */
@Data
public class PageDto<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;//当前页码
    private List<Integer> pages = new ArrayList<>();//当前页能显示的全部页码
    private Integer totalPages;//总页数

    public void setPagination(Integer page, Integer totalPages) {
        this.setTotalPages(totalPages);
        this.setPage(page);

        //添加在当前页码时可显示的页码范围
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, (Integer) (page - i));
            }
            if (page + i <= totalPages) {
                pages.add(page + i);
            }

        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            if(page != 0) {
                showPrevious = true;
            }
        }
        //是否展示下一页
        if (page.equals(totalPages)) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示首页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //是否展示末页
        if (pages.contains(totalPages)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
