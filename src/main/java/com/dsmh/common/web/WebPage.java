package com.dsmh.common.web;

import lombok.Data;

/**
 * @author TeamScorpio
 * @since 2022/3/10
 */
@Data
public class WebPage {

    private Long page;

    private Long size;

    public Long getPage() {
        return page == null ? 1L : page;
    }

    public Long getSize() {
        return size == null? 2000L : size;
    }

}
