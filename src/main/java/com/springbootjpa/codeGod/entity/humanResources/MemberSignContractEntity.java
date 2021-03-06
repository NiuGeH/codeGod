package com.springbootjpa.codeGod.entity.humanResources;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
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
	@JoinColumn(name = "member_referrer_id")
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
	private Integer siginResults;

	/**
	 * 签约结果返回数据
	 */
	@Transient
	private String siginResultsData;

	public String getSiginResultsData() {
		if(!ObjectUtils.isEmpty(getSiginResults())){
			if (getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_WSQ.getIndex()){
				return HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_WSQ.getName();
			}else if(getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_DDSH.getIndex()){
				return HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_DDSH.getName();
			}else if(getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YQY.getIndex()){
				return HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YQY.getName();
			}else if(getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YJJ.getIndex()){
				return HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YJJ.getName();
			}
		}
		return siginResultsData;
	}

	/**
	 * 验证码 0 未过期 1已过期
	 * default value: null
	 */
	@ApiModelProperty(value = "验证码 0 未过期 1已过期")
	@Column(name = "sigin_verification_code", nullable = true, length = 20)
	private Integer siginVerificationCode;

	/**
	 * 验证码 是否过期返回数据
	 */
	@Transient
	private String siginVerificationCodeData;

	public String getSiginVerificationCodeData() {
		if(!ObjectUtils.isEmpty(getSiginVerificationCode())){
			if(getSiginVerificationCode() == HumanRecourcesStatus.MEMBER_SIGN_CONTRACT_SIGIN_VERIFICATION_CODE_WGQ.getIndex()){
				return HumanRecourcesStatus.MEMBER_SIGN_CONTRACT_SIGIN_VERIFICATION_CODE_WGQ.getName();
			}else if(getSiginVerificationCode() == HumanRecourcesStatus.MEMBER_SIGN_CONTRACT_SIGIN_VERIFICATION_CODE_YGQ.getIndex()){
				return HumanRecourcesStatus.MEMBER_SIGN_CONTRACT_SIGIN_VERIFICATION_CODE_YGQ.getName();
			}
		}
		return siginVerificationCodeData;
	}

	@Override
	public String toString() {
		return "MemberSignContractEntity{" +
				"memberId=" + memberId +
				", memberEndId=" + memberEndId +
				", siginRecommendedReasion='" + siginRecommendedReasion + '\'' +
				", siginJobs='" + siginJobs + '\'' +
				", siginMaterial=" + siginMaterial +
				", siginResults=" + siginResults +
				", siginResultsData='" + siginResultsData + '\'' +
				", siginVerificationCode=" + siginVerificationCode +
				", siginVerificationCodeData='" + siginVerificationCodeData + '\'' +
				", id=" + id +
				'}';
	}
}
