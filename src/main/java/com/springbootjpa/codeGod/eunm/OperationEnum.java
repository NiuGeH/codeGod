package com.springbootjpa.codeGod.eunm;

public enum OperationEnum {
    //所有删除 冻结 都用这个 0正常  1删除
    OPERATIONMEDAL_STATE_ZC("正常",0)
    ,OPERATIONMEDAL_STATE_SC("删除",1);
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
