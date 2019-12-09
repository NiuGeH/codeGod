package com.springbootjpa.codeGod.common;

import lombok.Data;

@Data
public class AjaxResult<T> {
    private boolean success;
    private String message;
    private Integer code;
    private T data;
}