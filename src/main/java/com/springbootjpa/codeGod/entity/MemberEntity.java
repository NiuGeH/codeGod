package com.springbootjpa.codeGod.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 用户基础表
 */
@Data
@Entity
@ApiModel(value = "用户基础表")
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
	@Column(name = "create_time", nullable = true, length = 20)
	private String createTime;

	/**
	 * 修改时间
	 * default value: null
	 */
	@ApiModelProperty(value = "修改时间")
	@Column(name = "update_time", nullable = true, length = 20)
	private String updateTime;

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
	@ApiModelProperty(value = " 签约协议 可上传多个 ,号分割")
	@Column(name = "member_signing_agreement", nullable = true, length = 50)
	private String memberSigningAgreement;

	/**
	 * 签约状态 0 拒绝 1 已签约
	 * default value: null
	 */
	@ApiModelProperty(value = "签约状态 0 拒绝 1 已签约")
	@Column(name = "member_signing_status", nullable = true, length = 4)
	private Integer memberSigningStatus;

	/**
	 * 所属团队
	 * default value: null
	 */
	@ApiModelProperty(value = "所属团队 （待开发）")
	@Column(name = "member_team", nullable = true, length = 20)
	private Long memberTeam;
}
