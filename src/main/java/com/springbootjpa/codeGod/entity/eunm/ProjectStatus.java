package com.springbootjpa.codeGod.entity.eunm;

public enum ProjectStatus {
         洽谈中("洽谈中", 0)
        ,筹备中("筹备中", 1)
        ,开发中("开发中", 2)
        ,维保中("维保中", 3);
        // 成员变量
        private String name;
        private int index;
        // 构造方法
        private ProjectStatus(String name, int index) {
            this.name = name;
            this.index = index;
        }
        // 普通方法
        public static String getName(int index) {
            for (ProjectStatus c : ProjectStatus.values()) {
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
