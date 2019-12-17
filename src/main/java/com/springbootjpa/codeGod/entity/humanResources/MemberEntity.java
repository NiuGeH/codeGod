package com.springbootjpa.codeGod.entity.humanResources;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.operation.OperationMedalEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * 用户基础表
 */
@Data
@ApiModel(value = "用户基础表")
@Entity
@Table(name = "member")
public class MemberEntity extends AbstractEntity implements Serializable {

	/**
	 * 昵称
	 * default value: null
	 */
	@ApiModelProperty(value = "昵称")
	@Column(name = "nick_name", nullable = true, length = 20)
	private String nickName;

	/**
	 * 创建时间
	 * default value: null
	 */
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time", nullable = true)
	private java.util.Date createTime;

	/**
	 * 修改时间
	 * default value: null
	 */
	@ApiModelProperty(value = "修改时间")
	@Column(name = "update_time", nullable = true )
	private java.util.Date updateTime;

	/**
	 * 所在城市
	 * default value: null
	 */
	@ApiModelProperty(value = "所在城市")
	@Column(name = "memebr_city", nullable = true, length = 30)
	private String memebrCity;

	/**
	 * 关键字 用,分割
	 * default value: null
	 */
	@ApiModelProperty(value = "关键字 用,分割")
	@Column(name = "member_word", nullable = true, length = 50)
	private String memberWord;

	/**
	 * 所属公司
	 * default value: null
	 */
	@ApiModelProperty(value = "所属公司")
	@Column(name = "member_company", nullable = true, length = 30)
	private String memberCompany;

	/**
	 * 个人介绍
	 * default value: null
	 */
	@ApiModelProperty(value = "个人介绍")
	@Column(name = "member_introduce", nullable = true, length = 300)
	private String memberIntroduce;

	/**
	 * 用户类型 0真实用户 1自建用户
	 * default value: null
	 */
	@ApiModelProperty(value = "用户类型 0真实用户 1自建用户")
	@Column(name = "member_type", nullable = true, length = 11)
	private Integer memberType;

	/**
	 * 签约岗位 用,号分割
	 * default value: null
	 */
	@ApiModelProperty(value = "签约岗位 用,号分割")
	@Column(name = "member_signing_post", nullable = true, length = 30)
	private String memberSigningPost;

	/**
	 * 签约方式 0全职 1兼职
	 * default value: null
	 */
	@ApiModelProperty(value = "签约方式 0全职 1兼职")
	@Column(name = "member_signing_mode", nullable = true, length = 3)
	private Integer memberSigningMode;

	/**
	 * 勋章 ,号分割
	 * default value: null
	 */
	@ApiModelProperty(value = "勋章 ,号分割")
	@Column(name = "member_medal", nullable = true, length = 40)
	private String memberMedal;

	@ApiModelProperty(value = "勋章 实体类集合")
	@Transient
	private List<OperationMedalEntity> memberMedalList;

	/**
	 * 是否驻场
	 * default value: null
	 */
	@ApiModelProperty(value = "是否驻场")
	@Column(name = "member_stationing", nullable = true, length = 30)
	private String memberStationing;

	/**
	 * 当前状态  可接单 工作中
	 * default value: null
	 */
	@ApiModelProperty(value = "当前状态  可接单 工作中")
	@Column(name = "member_status", nullable = true, length = 10)
	private String memberStatus;

	/**
	 * 是否公开显示 0显示  1不显示
	 * default value: null
	 */
	@ApiModelProperty(value = "是否公开显示 0显示  1不显示")
	@Column(name = "member_display", nullable = true, length = 2)
	private Integer memberDisplay;

	/**
	 * 签约协议 可上传多个 ,号分割
	 * default value: null
	 */
	@ApiModelProperty(value = "签约协议 可上传多个 ,号分割")
	@Column(name = "member_signing_agreement", nullable = true, length = 50)
	private String memberSigningAgreement;

	/**
	 * 签约状态 0 拒绝 1 已签约
	 * default value: null
	 */
	@ApiModelProperty(value = "签约结果 0 未申请 1 等待审核 2 已签约 3 已拒绝")
	@Column(name = "member_signing_status", nullable = true, length = 4)
	private Integer memberSigningStatus;

	/**
	 * 所属团队
	 * default value: null
	 */
	@ApiModelProperty(value = "所属团队")
	@Column(name = "member_team", nullable = true, length = 20)
	private Long memberTeam;

	/**
	 * 工作态度
	 * default value: null
	 */
	@ApiModelProperty(value = "工作态度")
	@Column(name = "member_attitude", nullable = true, length = 10)
	private String memberAttitude;

	/**
	 * 榜评
	 * default value: null
	 */
	@ApiModelProperty(value = "榜评")
	@Column(name = "member_review", nullable = true, length = 50)
	private String memberReview;

	/**
	 * 技能管理
	 * default value: null
	 */
	@ApiModelProperty(value = "技能管理")
	@Column(name = "member_skill_management", nullable = true, length = 100)
	private String memberSkillManagement;

	/**
	 * 远程开发报价
	 */
	@ApiModelProperty(value = "远程开发报价")
	@Column(name = "member_long_range" , nullable = true , length = 40)
	private String memberLongRange;

	/**
	 * 驻场开发报价
	 */
	@ApiModelProperty(value = "驻场开发报价")
	@Column(name = "member_on_site_development" , nullable = true , length = 40)
	private String memberOnSiteDevelopment;

	/**
	 * 私密信息表
	 */
	@ApiModelProperty(value = "私密信息表")
	@Transient
	private MemberPrivacyEntity memberPrivacyEntity;

}
