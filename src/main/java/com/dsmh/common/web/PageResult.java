package com.dsmh.common.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author TeamScorpio
 * @since 2022/3/10
 */

public class PageResult<T> extends BaseResult {


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageData<T> {
        private long currentPage;

        private long totalPages;

        private long totalElements;

        private List<T> data;
    }

    private final PageData<T> data;


    public PageResult(IPage<T> page) {
        super();
        PageData<T> pageData = new PageData<>();
        pageData.setCurrentPage(page.getCurrent());
        pageData.setTotalPages(page.getPages());
        pageData.setTotalElements(page.getTotal());
        pageData.setData(page.getRecords());
        this.data = pageData;

    }

    public PageResult(PageData<T> pageData) {
        super();
        this.data = pageData;
    }


    public PageData<T> getData() {
        return data;
    }

    public static <T> PageResult<?> ok(IPage<T> data) {
        return new PageResult<>(data);
    }

    public static ResponseEntity<?> noContent() {
        return ResponseEntity.noContent().build();
    }
}
