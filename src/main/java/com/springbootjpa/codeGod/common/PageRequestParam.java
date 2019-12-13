package com.springbootjpa.codeGod.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "分页实体类")
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
    @ApiModelProperty(value = "总页数")
    private Long total;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Integer page;

    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数")
    private Integer rows;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String sidx;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private String sord;
}