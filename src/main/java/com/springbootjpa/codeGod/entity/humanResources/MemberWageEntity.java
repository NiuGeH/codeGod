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
 * 人力外包发工资表
 */
@Data
@ApiModel(value = "人力外包发工资表")
@Entity
@Table(name = "member_wage")
public class MemberWageEntity extends AbstractEntity implements Serializable {

	/**
	 * 合同id
	 * default value: null
	 */
	@ApiModelProperty(value = "合同id")
//	@Column(name = "contract_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contract_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberContractEntity contractId;

	/**
	 * 雇佣人员表id
	 * default value: null
	 */
	@ApiModelProperty(value = "雇佣人员表id")
//	@Column(name = "employ_personnel", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employ_personnel")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEmployPersonnelEntity employPersonnel;

	/**
	 * 结算周期开始时间
	 * default value: null
	 */
	@ApiModelProperty(value = "结算周期开始时间")
	@Column(name = "settlement_cycle_start", nullable = true )
	private java.util.Date settlementCycleStart;

	/**
	 * 结算周期结束时间
	 * default value: null
	 */
	@ApiModelProperty(value = "结算周期结束时间")
	@Column(name = "settlement_cycle_end", nullable = true )
	private java.util.Date settlementCycleEnd;

	/**
	 * 社保缴纳
	 * default value: null
	 */
	@ApiModelProperty(value = "社保缴纳")
	@Column(name = "wage_social_security", nullable = true, length = 20)
	private String wageSocialSecurity;

	/**
	 * 公积金缴纳
	 * default value: null
	 */
	@ApiModelProperty(value = "公积金缴纳")
	@Column(name = "wage_accumulation_fund", nullable = true, length = 20)
	private String wageAccumulationFund;

	/**
	 * 个人税
	 * default value: null
	 */
	@ApiModelProperty(value = "个人税")
	@Column(name = "wage_personal_taxes", nullable = true, length = 20)
	private String wagePersonalTaxes;

	/**
	 * 奖金
	 * default value: null
	 */
	@ApiModelProperty(value = "奖金")
	@Column(name = "wage_bonus", nullable = true, length = 20)
	private String wageBonus;

	/**
	 * 实发工资
	 * default value: null
	 */
	@ApiModelProperty(value = "实发工资")
	@Column(name = "wage_real_wages", nullable = true, length = 20)
	private String wageRealWages;

	/**
	 * 付款备注
	 * default value: null
	 */
	@ApiModelProperty(value = "付款备注")
	@Column(name = "wage_payment_note", nullable = true, length = 20)
	private String wagePaymentNote;

	/**
	 * 付款状态 0未支付 1已支付
	 * default value: null
	 */
	@ApiModelProperty(value = "付款状态 0未支付 1已支付")
	@Column(name = "wage_payment_status", nullable = true, length = 11)
	private Integer wagePaymentStatus;

	/**
	 * 付款方式 0平台划账 1线下打款
	 * default value: null
	 */
	@ApiModelProperty(value = "付款方式 0平台划账 1线下打款")
	@Column(name = "wage_payment_way", nullable = true, length = 11)
	private Integer wagePaymentWay;

	/**
	 * 付款凭证 uploadFile
	 * default value: null
	 */
	@ApiModelProperty(value = "付款凭证 uploadFile")
//	@Column(name = "wage_payment_credentials", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wage_payment_credentials")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile wagePaymentCredentials;


	@ApiModelProperty(value = "工作天数")
	@Column(name = "wage_number_days")
	private Integer wageNumberDays;
}
