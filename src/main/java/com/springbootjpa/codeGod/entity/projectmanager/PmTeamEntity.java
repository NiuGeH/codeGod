package com.springbootjpa.codeGod.entity.projectmanager;


import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * 团队表
 */
@Data
@ApiModel(value = " 团队表")
@Entity
@Table(name = "pm_team")
public class PmTeamEntity extends AbstractEntity implements Serializable {

    /**
     * 姓名
     * default value: null
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private MemberEntity memberEntity;

    /**
     * 职务
     * default value: null
     */
    @ApiModelProperty(value = "职务")
    @Column(name = "duty", nullable = true, length = 11)
    private Integer duty;
    @Transient
    private String dutyString;
    /**
     * 参与角色
     * default value: null
     */
    @ApiModelProperty(value = "参与角色")
    @Column(name = "role", nullable = true, length = 11)
    private Integer role;
    @Transient
    private String roleString;
    /**
     * 报价
     * default value: null
     */
    @ApiModelProperty(value = "报价")
    @Column(name = "cost", nullable = true)
    private Double cost;


    /**
     * 工作量
     * default value: null
     */
    @ApiModelProperty(value = "工作量")
    @Column(name = "wordload", nullable = true)
    private Double wordload;

    /**
     * 个人诉求
     * default value: null
     */
    @ApiModelProperty(value = "个人诉求")
    @Column(name = "remark", nullable = true, length = 50)
    private String remark;

    /**
     * 状态
     * default value: null
     */
    @ApiModelProperty(value = "状态")
    @Column(name = "status", nullable = true, length = 11)
    private Integer status;
    @Transient
    private String statusString;

    /**
     * 评价
     * default value: null
     */
    @ApiModelProperty(value = "评价")
    @Column(name = "evaluate", nullable = true, length = 20)
    private Long evaluate;

    /**
     * 理由
     * default value: null
     */
    @ApiModelProperty(value = "理由")
    @Column(name = "reason", nullable = true, length = 255)
    private String reason;

    /**
     * 结算方式
     * default value: null
     */
    @ApiModelProperty(value = "结算方式")
    @Column(name = "clearing_form", nullable = true, length = 11)
    private Integer clearingForm;
    @Transient
    private String clearingFormString;


    /**
     * 驻场要求
     * default value: null
     */
    @ApiModelProperty(value = "驻场要求")
    @Column(name = "site_requirements", nullable = true, length = 11)
    private Integer siteRequirements;
    @Transient
    private String siteRequirementsString;

//    /**
//     * 模块ID
//     * default value: null
//     */
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "module_id")
//    @NotFound(action = NotFoundAction.IGNORE)
//    private List<PmModulesEntity> pmModulesEntity;

    /**
     * 项目贡献度
     * default value: null
     */
    @ApiModelProperty(value = "项目贡献度")
    @Column(name = "contribution", nullable = true, length = 20)
    private Long contribution;

    /**
     * 项目ID
     * default value: null
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private PmProjectEntity pmProjectEntity;
}
