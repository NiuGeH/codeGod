package com.springbootjpa.codeGod.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    /**
     * 总页数
     */
    private Long total = 0l;

    /**
     * 当前页
     */
    private Integer page = 0;

    /**
     * 本次查询总记录数
     */
    private Long records = 0l;

    /**
     * 结果集
     */
    private List<T> rows;

    /**
     * 加密后的结果集
     */
    private String rowsPublic;
}
