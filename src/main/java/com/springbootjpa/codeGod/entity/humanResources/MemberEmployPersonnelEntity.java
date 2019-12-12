package com.springbootjpa.codeGod.entity.humanResources;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
	 * 到岗日
	 * default value: null
	 */
	@ApiModelProperty(value = "到岗日")
	@Column(name = "employ_persionnel_come_to_start_time", nullable = true)
	private java.util.Date employPersionnelComeToStartTime;

	/**
	 * 截止日
	 * default value: null
	 */
	@ApiModelProperty(value = "截止日")
	@Column(name = "employ_persionnel_abort_time", nullable = true)
	private java.util.Date employPersionnelAbortTime;

	/**
	 * 职务
	 * default value: null
	 */
	@ApiModelProperty(value = "职务")
	@Column(name = "employ_persionnel_skill_id", nullable = true, length = 20)
	private String employPersionnelSkillId;

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
	@Column(name = "employ_project_address", nullable = true, length = 80)
	private String employProjectAddress;

	/**
	 * 开发方式 0现场开发 1远程开发
	 * default value: null
	 */
	@ApiModelProperty(value = "开发方式 0现场开发 1远程开发")
	@Column(name = "employ_development_way", nullable = true, length = 11)
	private Integer employDevelopmentWay;

	/**
	 * 邀请状态 0未邀请 1已拒绝 2已同意
	 * default value: null
	 */
	@ApiModelProperty(value = "邀请状态 0未邀请 1已拒绝 2已同意")
	@Column(name = "employ_invitation_status", nullable = true, length = 11)
	private Integer employInvitationStatus;

	/**
	 * 工作状态 0未进场 1已进场 2已离场
	 * default value: null
	 */
	@ApiModelProperty(value = "工作状态 0未进场 1已进场 2已离场")
	@Column(name = "employ_work_status", nullable = true, length = 11)
	private Integer employWorkStatus;

	/**
	 * 人员类型 0客户自己pc端请他出受 1后台管理人员直接邀请
	 * default value: null
	 */
	@ApiModelProperty(value = "人员类型 0客户自己pc端请他出受 1后台管理人员直接邀请")
	@Column(name = "employ_persionnel_type", nullable = true, length = 11)
	private Integer employPersionnelType;

	/**
	 * 签约类型 0全职 1兼职
	 * default value: null
	 */
	@ApiModelProperty(value = "签约类型 0全职 1兼职")
	@Column(name = "employ_persionnel_sigin_type", nullable = true, length = 11)
	private Integer employPersionnelSiginType;

	/**
	 * 邀请人员id
	 * default value: null
	 */
	@ApiModelProperty(value = "邀请人员id")
//	@Column(name = "member_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEntity memberId;
}
