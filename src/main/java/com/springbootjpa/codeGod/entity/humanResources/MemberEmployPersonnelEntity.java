package com.springbootjpa.codeGod.entity.humanResources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 雇佣指定人员表
 */
@Data
@ApiModel(value = "雇佣指定人员表")
@Entity
@Table(name = "member_employ_personnel")
public class MemberEmployPersonnelEntity extends AbstractEntity implements Serializable {

    /**
     * 对应的需求id
     * default value: null
     */
    @ApiModelProperty(value = "对应的需求id")
//	@Column(name = "employ_id", nullable = true, length = 20)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employ_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private MemberEmployEntity employId;

    /**
     * 客户名称
     * default value: null
     */
    @Column(name = "employ_persionnel_entrust_name", nullable = true)
    private String employPersionnelEntrustName;

    /**
     * 到岗日
     * default value: null
     */
    @ApiModelProperty(value = "到岗日")
    @Column(name = "employ_persionnel_come_to_start_time", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date employPersionnelComeToStartTime;

    /**
     * 截止日
     * default value: null
     */
    @ApiModelProperty(value = "截止日")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "employ_persionnel_abort_time", nullable = true)
    private java.util.Date employPersionnelAbortTime;

    /**
     * 职务
     * default value: null
     */
    @ApiModelProperty(value = "职务")
    @Column(name = "employ_persionnel_resource_id", nullable = true, length = 20)
    private String employPersionnelSkillId;

    @Transient
    private String employPersionnelSkillIdData;

    /**
     * 月薪
     * default value: null
     */
    @ApiModelProperty(value = "月薪")
    @Column(name = "employ_month_money", nullable = true, length = 20)
    private String employMonthMoney;

    /**
     * 项目地址
     * default value: null
     */
    @ApiModelProperty(value = "项目地址")
//	@Column(name = "employ_project_address", nullable = true, length = 80)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employ_project_address")
    @NotFound(action = NotFoundAction.IGNORE)
    private OperationRegionEntity employProjectAddress;

    /**
     * 开发方式 0现场开发 1远程开发
     * default value: null
     */
    @ApiModelProperty(value = "开发方式 0现场开发 1远程开发")
    @Column(name = "employ_development_way", nullable = true, length = 11)
    private Integer employDevelopmentWay;

    @Transient
    private String employDevelopmentWayData;

    public String getEmployDevelopmentWayData() {
        if(ObjectUtils.isEmpty(getEmployDevelopmentWay()))         return employDevelopmentWayData;

        if (getEmployDevelopmentWay() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_DEVELOPMENT_WAY_XCKF.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_DEVELOPMENT_WAY_XCKF.getName();
        } else if (getEmployDevelopmentWay() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_DEVELOPMENT_WAY_YCKF.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_DEVELOPMENT_WAY_YCKF.getName();
        }
        return employDevelopmentWayData;
    }

    /**
     * 邀请状态 0未邀请 1已拒绝 2已同意
     * default value: null
     */
    @ApiModelProperty(value = "邀请状态 0未邀请 1已拒绝 2已同意 3已邀请")
    @Column(name = "employ_invitation_status", nullable = true, length = 11)
    private Integer employInvitationStatus;

    @Transient
    private String employInvitationStatusData;

    public String getEmployInvitationStatusData() {
        if (!ObjectUtils.isEmpty(getEmployInvitationStatus())) {

            if (getEmployInvitationStatus() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_WYQ.getIndex()) {
                return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_WYQ.getName();
            } else if (getEmployInvitationStatus() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YJJ.getIndex()) {
                return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YJJ.getName();
            } else if (getEmployInvitationStatus() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YTY.getIndex()) {
                return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YTY.getName();
            } else if(getEmployInvitationStatus() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YYY.getIndex()){
                return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YYY.getName();
            }
        }

        return employInvitationStatusData;
    }

    /**
     * 工作状态 0未进场 1已进场 2已离场
     * default value: null
     */
    @ApiModelProperty(value = "工作状态 0未进场 1已进场 2已离场")
    @Column(name = "employ_work_status", nullable = true, length = 11)
    private Integer employWorkStatus;

    @Transient
    private String employWorkStatusData;

    public String getEmployWorkStatusData() {
        if(ObjectUtils.isEmpty(getEmployWorkStatus())){
            return employWorkStatusData;
        }
        if (getEmployWorkStatus() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_WORK_STATUS_WJC.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_WORK_STATUS_WJC.getName();
        } else if (getEmployWorkStatus() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_WORK_STATUS_YJC.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_WORK_STATUS_YJC.getName();
        } else if (getEmployWorkStatus() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_WORK_STATUS_YLC.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_WORK_STATUS_YLC.getName();
        }
        return employWorkStatusData;
    }

    /**
     * 人员类型 0客户自己pc端请他出手 1后台管理人员直接邀请
     * default value: null
     */
    @ApiModelProperty(value = "人员类型 0客户自己pc端请他出手 1后台管理人员直接邀请")
    @Column(name = "employ_persionnel_type", nullable = true, length = 11)
    private Integer employPersionnelType;

    @Transient
    private String employPersionnelTypeData;

    public String getEmployPersionnelTypeData() {
        if(ObjectUtils.isEmpty(getEmployPersionnelType())){
            return employPersionnelTypeData;
        }
        if (getEmployPersionnelType() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_TYPE_KHYQ.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_TYPE_KHYQ.getName();
        } else if (getEmployPersionnelType() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_TYPE_HTYQ.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_TYPE_HTYQ.getName();
        }
        return employPersionnelTypeData;
    }

    /**
     * 签约类型 0全职 1兼职
     * default value: null
     */
    @ApiModelProperty(value = "签约类型 0全职 1兼职")
    @Column(name = "employ_persionnel_sigin_type", nullable = true, length = 11)
    private Integer employPersionnelSiginType;

    @Transient
    private String employPersionnelSiginTypeData;

    public String getEmployPersionnelSiginTypeData() {
        if(ObjectUtils.isEmpty(getEmployPersionnelSiginType()))         return employPersionnelSiginTypeData;
        if (getEmployPersionnelSiginType() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_SIGIN_TYPE_QZ.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_SIGIN_TYPE_QZ.getName();
        } else if (getEmployPersionnelSiginType() == HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_SIGIN_TYPE_JZ.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSIONNEL_SIGIN_TYPE_JZ.getName();
        }
        return employPersionnelSiginTypeData;
    }

    /**
     * 被邀请人员id
     * default value: null
     */
    @ApiModelProperty(value = "被邀请人员id")
//	@Column(name = "member_id", nullable = true, length = 20)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private MemberEntity memberId;


    /**
     * 工作内容
     * default value: null
     */
    @ApiModelProperty(value = "工作内容")
    @Column(name = "employ_work_content", nullable = true)
    private String employWorkContent;


    /**
     * 单价
     * default value: null
     */
    @ApiModelProperty(value = "单价")
    @Column(name = "employ_unit_price", nullable = true)
    private String employUnitPrice;

    @Override
    public String toString() {
        return "MemberEmployPersonnelEntity{" +
                "employId=" + employId +
                ", employPersionnelComeToStartTime=" + employPersionnelComeToStartTime +
                ", employPersionnelAbortTime=" + employPersionnelAbortTime +
                ", employPersionnelSkillId='" + employPersionnelSkillId + '\'' +
                ", employMonthMoney='" + employMonthMoney + '\'' +
                ", employProjectAddress=" + employProjectAddress +
                ", employDevelopmentWay=" + employDevelopmentWay +
                ", employDevelopmentWayData='" + employDevelopmentWayData + '\'' +
                ", employInvitationStatus=" + employInvitationStatus +
                ", employInvitationStatusData='" + employInvitationStatusData + '\'' +
                ", employWorkStatus=" + employWorkStatus +
                ", employWorkStatusData='" + employWorkStatusData + '\'' +
                ", employPersionnelType=" + employPersionnelType +
                ", employPersionnelTypeData='" + employPersionnelTypeData + '\'' +
                ", employPersionnelSiginType=" + employPersionnelSiginType +
                ", employPersionnelSiginTypeData='" + employPersionnelSiginTypeData + '\'' +
                ", memberId=" + memberId +
                ", employWorkContent='" + employWorkContent + '\'' +
                ", employUnitPrice='" + employUnitPrice + '\'' +
                ", id=" + id +
                '}';
    }
}
