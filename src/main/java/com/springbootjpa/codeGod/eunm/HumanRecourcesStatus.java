package com.springbootjpa.codeGod.eunm;


public enum HumanRecourcesStatus {
    MEMBER_MEMBERDISPLAY_XS("显示",0)
    ,MEMBER_MEMBERDISPLAY_BXS("不显示",1)
    ,MEMBER_MEMBERCASE_S("是",0)
    ,MEMBER_MEMBERCASE_F("否",1)
    ,MEMBERSIGNCONTRACT_SIGIN_RESULTS_WSQ("未申请",0)
    ,MEMBERSIGNCONTRACT_SIGIN_RESULTS_DDSH("等待审核",1)
    ,MEMBERSIGNCONTRACT_SIGIN_RESULTS_YQY("已签约",2)
    ,MEMBERSIGNCONTRACT_SIGIN_RESULTS_YJJ("已拒绝",3)
    ,MEMBER_MEMBERSIGNINGPOST_KF("开发",0)
    ,MEMBER_MEMBERSIGNINGPOST_CS("测试",1)
    ,MEMBER_MEMBERSIGNINGPOST_UI("UI",2)
    ,MEMBER_MEMBERSIGNINGPOST_CP("产品",3)
    ,MEMBER_MEMBERSIGNINGPOST_XS_KH("销售/客户",4)
    ,MEMBER_STATE_ZC("正常",0)
    ,MEMBER_STATE_SC("删除",1)
    ,MEMBERCASE_CASEPLATGORMPROJECT_S("是",0)
    ,MEMBERCASE_CASEPLATGORMPROJECT_F("否",1)
    ,MEMBER_MEMBERSIGNINGMODE_QZ("全职",0)
    ,MEMBER_MEMBERSIGNINGMODE_JZ("兼职",1)
    ,MEMBER_MEMBERSTATIONING_KZC("可驻场",0)
    ,MEMBER_MEMBERSTATIONING_BKZC("不可驻场",1)
    ,MEMBER_MEMBERSTATIONING_QGCC("可全国出差",2)
    ,MEMBER_MEMBERSTATUS_KJD("可接单",0)
    ,MEMBER_MEMBERSTATUS_GZZ("工作中",1)
    ,MEMBER_MEMBERTYPE_ZSYH("真实用户",0)
    ,MEMBER_MEMBERTYPE_ZJYH("自建用户",1)
    ,MEMBER_EMPLOY_EMPLOYSTATUS_WCL("未处理",0)
    ,MEMBER_EMPLOY_EMPLOYSTATUS_YJJ("已拒绝",1)
    ,MEMBER_EMPLOY_EMPLOYSTATUS_YQHT("已签合同",2)
    ,MEMBER_EMPLOY_EMPLOYMODE_ZC("驻场",0)
    ,MEMBER_EMPLOY_EMPLOYMODE_YC("远程",1)
    ,MEMBER_MEMBER_EMPLOY_PERSONNEL_TYPE_KHYQ("客户邀请",0)//employ_persionnel_type
    ,MEMBER_MEMBER_EMPLOY_PERSONNEL_TYPE_HTYQ("后台直接邀请",1)//employ_persionnel_type
    ,MEMBER_MEMBER_EMPLOY_PERSONNEL_DEVELOPMENT_WAY_XCKF("现场开发",0)//employ_development_way
    ,MEMBER_MEMBER_EMPLOY_PERSONNEL_DEVELOPMENT_WAY_YCKF("远程开发",1)//employ_development_way
    ,MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_WYQ("未邀请",0)//employ_invitation_status
    ,MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YJJ("已拒绝",1)//employ_invitation_status
    ,MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YTY("已同意",2)//employ_invitation_status
    ,MEMBER_MEMBER_EMPLOY_PERSIONNEL_WORK_STATUS_WJC("未进场",0) //employ_work_status
    ,MEMBER_MEMBER_EMPLOY_PERSIONNEL_WORK_STATUS_YJC("已进场",1) //employ_work_status
    ,MEMBER_MEMBER_EMPLOY_PERSIONNEL_WORK_STATUS_YLC("已离场",2) //employ_work_status
    ,MEMBER_MEMBER_EMPLOY_PERSIONNEL_SIGIN_TYPE_QZ("全职",0)  //employ_persionnel_sigin_type
    ,MEMBER_MEMBER_EMPLOY_PERSIONNEL_SIGIN_TYPE_JZ("兼职",1)  //employ_persionnel_sigin_type
    ;
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
