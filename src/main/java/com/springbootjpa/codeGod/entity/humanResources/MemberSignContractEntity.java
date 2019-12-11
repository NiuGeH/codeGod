package com.springbootjpa.codeGod.entity.humanResources;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 用户签约审核
 */
@Data
@ApiModel(value = "用户签约审核")
@Entity
@Table(name = "member_sign_contract")
public class MemberSignContractEntity extends AbstractEntity implements Serializable {

	/**
	 * 推荐人
	 * default value: null
	 */
	@ApiModelProperty(value = "推荐人")
//	@Column(name = "member_id", nullable = false, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEntity memberId;

	/**
	 * member表中申请人id
	 * default value: null
	 */
	@ApiModelProperty(value = "member表中申请人id")
//	@Column(name = "member_end_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_end_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEntity memberEndId;

	/**
	 * 推荐理由
	 * default value: null
	 */
	@ApiModelProperty(value = "推荐理由")
	@Column(name = "sigin_recommended_reasion", nullable = true, length = 50)
	private String siginRecommendedReasion;

	/**
	 * 申请岗位
	 * default value: null
	 */
	@ApiModelProperty(value = "申请岗位")
	@Column(name = "sigin_jobs", nullable = true, length = 20)
	private String siginJobs;

	/**
	 * 申请材料
	 * default value: null
	 */
	@ApiModelProperty(value = "申请材料")
//	@Column(name = "sigin_material", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sigin_material")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile siginMaterial;

	/**
	 * 签约结果 0 未申请 1 等待审核 2 已签约 3 已拒绝
	 * default value: null
	 */
	@ApiModelProperty(value = "签约结果 0 未申请 1 等待审核 2 已签约 3 已拒绝")
	@Column(name = "sigin_results", nullable = true, length = 20)
	private String siginResults;

	/**
	 * 验证码 0 未过期 1已过期
	 * default value: null
	 */
	@ApiModelProperty(value = "验证码 0 未过期 1已过期")
	@Column(name = "sigin_verification_code", nullable = true, length = 20)
	private String siginVerificationCode;

	/**
	 * 协议 Id
	 * default value: null
	 */
	@ApiModelProperty(value = "协议 Id")
//	@Column(name = "sigin_agreement", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sigin_agreement")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile siginAgreement;
}
