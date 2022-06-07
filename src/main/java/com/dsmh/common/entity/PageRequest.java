package com.dsmh.common.entity;

import lombok.Data;

/**
 * @author: Asher
 * @date: 2022/4/22 15:53
 * @description:
 */
@Data
public class PageRequest {
    private static final int DEFAULT_PAGE_INDEX = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 200;
    private Integer pageIndex;
    private Integer pageSize;

    public PageRequest() {
        this(0, 10);
    }

    public PageRequest(Integer pageIndex) {
        this(pageIndex, 10);
    }

    public PageRequest(Integer pageIndex, Integer pageSize) {
        this.setPageIndex(pageIndex);
        this.setPageSize(pageSize);
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = null != pageIndex && pageIndex != 0 ? pageIndex : DEFAULT_PAGE_INDEX;
    }

    public void setPageSize(Integer pageSize) {
        pageSize = null != this.pageIndex && pageSize != 0 ? pageSize : DEFAULT_PAGE_SIZE;
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }

        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }
}
