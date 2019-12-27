package com.springbootjpa.codeGod.entity.humanResources;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.entity.sys.SysUsersEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Proxy;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 雇佣需求表
 */
@Data
@ApiModel(value = "雇佣需求表")
@Entity
@Table(name = "member_employ")
public class MemberEmployEntity extends AbstractEntity implements Serializable {

    /**
     * 委托人 Id
     * default value: null
     */
    @ApiModelProperty(value = "委托人 Id")
//    @Column(name = "member_entrust_id", nullable = true, length = 20)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_entrust_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private MemberEntity memberEntrustId;

    /**
     * 委托人名称 客户会有不登录进行发布需求的
     * default value: null
     */
    @ApiModelProperty(value = "委托人名称 客户会有不登录进行发布需求的")
    @Column(name = "employ_entrust_name", nullable = true, length = 20)
    private String employEntrustName;

    /**
     * 委托时间
     * default value: null
     */
    @ApiModelProperty(value = "委托时间")
    @Column(name = "employ_entrust_time", nullable = true )
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private java.util.Date employEntrustTime;

    /**
     * 联系电话
     * default value: null
     */
    @ApiModelProperty(value = "联系电话")
    @Column(name = "employ_entrust_mobile", nullable = true, length = 20)
    private String employEntrustMobile;

    /**
     * 客户名称
     * default value: null
     */
    @ApiModelProperty(value = "客户名称")
    @Column(name = "employ_clientele_name", nullable = true, length = 30)
    private String employClienteleName;

    /**
     * 到岗时间
     * default value: null
     */
    @ApiModelProperty(value = "到岗时间")
    @Column(name = "employ_come_to_position", nullable = true )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date employComeToPosition;

    /**
     * 截止日
     * default value: null
     */
    @ApiModelProperty(value = "截止日")
    @Column(name = "employ_abort", nullable = true )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date employAbort;

    /**
     * 项目地址
     * default value: null
     */
    @ApiModelProperty(value = "项目地址")
//    @Column(name = "employ_item_address", nullable = true, length = 100)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employ_item_address")
    @NotFound(action = NotFoundAction.IGNORE)
    private OperationRegionEntity employItemAddress;

    /**
     * 雇佣人数
     * default value: null
     */
    @ApiModelProperty(value = "雇佣人数")
    @Column(name = "employ_number", nullable = true, length = 10)
    private Integer employNumber;

    /**
     * 开发方式
     * default value: null
     */
    @ApiModelProperty(value = "开发方式")
    @Column(name = "employ_mode", nullable = true, length = 20)
    private Integer employMode;

    @Transient
    private String employModeData;

    public String getEmployModeData() {
        if(ObjectUtils.isEmpty(getEmployMode())) return employModeData;
        if(getEmployMode() == HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYMODE_ZC.getIndex()){
            return HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYMODE_ZC.getName();
        }else if (getEmployMode() == HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYMODE_YC.getIndex()){
            return HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYMODE_YC.getName();
        }
        return employModeData;
    }

    /**
     * 简述要求
     * default value: null
     */
    @ApiModelProperty(value = "简述要求")
    @Column(name = "employ_sketch", nullable = true, length = 100)
    private String employSketch;

    /**
     * 状态 0 未处理 1已拒绝 2 已签合同
     * default value: null
     */
    @ApiModelProperty(value = "状态 0 未处理 1已拒绝 2 已签合同")
    @Column(name = "employ_status", nullable = true, length = 20)
    private Integer employStatus;

    @Transient
    private String employStatusData;

    public String getEmployStatusData() {
        if(ObjectUtils.isEmpty(getEmployStatus())) return employStatusData;

        if(getEmployStatus() == HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYSTATUS_WCL.getIndex()){
            return HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYSTATUS_WCL.getName();
        }else if(getEmployStatus() == HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYSTATUS_YJJ.getIndex()){
            return HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYSTATUS_YJJ.getName();
        }else if(getEmployStatus() == HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYSTATUS_YQHT.getIndex()){
            return HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYSTATUS_YQHT.getName();
        }
        return employStatusData;
    }

    /**
     * 处理人
     * default value: null
     */
    @ApiModelProperty(value = "处理人")
//    @Column(name = "employ_dispose_user", nullable = true, length = 20)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employ_dispose_user")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysUsersEntity employDisposeUser;

    /**
     * 处理时间
     * default value: null
     */
    @ApiModelProperty(value = "处理时间")
    @Column(name = "employ_dispose_tiem", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private java.util.Date employDisposeTiem;

    /**
     * 拒绝原因
     * default value: null
     */
    @ApiModelProperty(value = "拒绝原因")
    @Column(name = "employ_reason", nullable = true, length = 100)
    private String employReason;
}
