package com.springbootjpa.codeGod.entity.eunm;

public enum  DemandStatus {
    待处理("待处理",0)
    ,
    已拒单("已拒单",1)
    ,
    已安排产品("已安排产品",2)
    ,
    已发布项目("已发布项目",3);

    // 成员变量
    private final String name;
    private final int index;

    // 构造方法
    private DemandStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (DemandStatus c : DemandStatus.values()) {
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


    public int getIndex() {
        return index;
    }


}
