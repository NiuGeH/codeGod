package com.springbootjpa.codeGod.eunm;

public enum OperationEnum {

    //所有删除 冻结 都用这个 0正常  1删除
    OPERATION_STATE_ZC("正常",0)
    ,OPERATION_STATE_SC("删除",1)
    ,OPERATION_ENUM_REGION_DISPLAY_XS("显示",0)
    ,OPERATION_ENUM_REGION_DISPLAY_BXS("不显示",1)
    //所有是否显示 0是1否
    ,OPERATION_DISPLAY_YES("是",0)
    ,OPERATION_DISPLAY_NO("否",1)
    //子栏目是否可以跳转url
    ,OPERATION_URL_STATE_NO("不可以",0)
    ,OPERATION_URL_STATE_YES("可以",1)
    //新闻访问量基数
    ,OPERATION_NEWS_VIEWS("新闻访问量基数",1000);


    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private OperationEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (OperationEnum c : OperationEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
