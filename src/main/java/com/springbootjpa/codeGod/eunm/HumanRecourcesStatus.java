package com.springbootjpa.codeGod.eunm;


public enum HumanRecourcesStatus {
    MEMBERSIGNCONTRACT_SIGIN_RESULTS_WSQ("未申请",0)
    ,MEMBERSIGNCONTRACT_SIGIN_RESULTS_DDSH("等待审核",1)
    ,MEMBERSIGNCONTRACT_SIGIN_RESULTS_YQY("已签约",2)
    ,MEMBERSIGNCONTRACT_SIGIN_RESULTS_YJJ("已拒绝",3)
    ,MEMBER_STATE_ZC("正常",0)
    ,MEMBER_STATE_SC("删除",1);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private HumanRecourcesStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (HumanRecourcesStatus c : HumanRecourcesStatus.values()) {
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
