package com.springbootjpa.codeGod.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRequestParam {
    public PageRequestParam(Integer page, Integer rows) {
        this.page = page;
        this.rows = rows;
    }

    /**
     * 总页数
     */
    private Long total;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页记录数
     */
    private Integer rows;

    /**
     * 排序字段
     */
    private String sidx;

    /**
     * 排序
     */
    private String sord;
}