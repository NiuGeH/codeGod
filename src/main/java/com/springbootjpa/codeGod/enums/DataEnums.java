package com.springbootjpa.codeGod.enums;

public enum  DataEnums {
    RIGHT(1, "赠送权益"),
    CONSUMPTION(2, "消费提醒"),
    ORDER(3, "订单提醒"),
    REMIND(4, "医生提醒"),
    INTERVENE(5, "健康干预"),
    REPORT(6, "评估报告"),
    ADVICEBOOK(7, "健康建议书");

    private Integer key;    //代码
    private String text;    //名称

    DataEnums(int key, String text) {
        this.key = key;
        this.text = text;
    }

    public static DataEnums getByKey(Integer type) {
        for (DataEnums item : values()) {
            if (type != null && item.getKey().equals(type)) {
                return item;
            }
        }

        return null;
    }

    public Integer getKey() {
        return key;
    }

    public String getText() {
        return text;
    }
}
