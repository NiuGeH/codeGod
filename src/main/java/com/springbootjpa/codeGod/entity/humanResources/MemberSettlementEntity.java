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
 * 人力外包结算表
 */
@Data
@ApiModel(value = "人力外包结算表")
@Entity
@Table(name = "member_settlement")
public class MemberSettlementEntity extends AbstractEntity implements Serializable {

	/**
	 * 合同表 id
	 * default value: null
	 */
	@ApiModelProperty(value = "合同表 id")
//	@Column(name = "member_contract_id", nullable = false, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_contract_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberContractEntity memberContractId;

	/**
	 * 结算周期
	 * default value: null
	 */
	@ApiModelProperty(value = "结算周期")
	@Column(name = "settlement_cycle", nullable = true, length = 40)
	private String settlementCycle;

	/**
	 * 结算金额
	 * default value: null
	 */
	@ApiModelProperty(value = "结算金额")
	@Column(name = "settlement_amount", nullable = true, length = 20)
	private String settlementAmount;

	/**
	 * 结算备注
	 * default value: null
	 */
	@ApiModelProperty(value = "结算备注")
	@Column(name = "settlement_note", nullable = true, length = 255)
	private String settlementNote;

	/**
	 * 支付状态 0未支付 1已支付 3已到账
	 * default value: null
	 */
	@ApiModelProperty(value = "支付状态 0未支付 1已支付 3已到账")
	@Column(name = "settlement_pay_status", nullable = true, length = 11)
	private Integer settlementPayStatus;

	/**
	 * 提成状态 0未提成 1已提成
	 * default value: null
	 */
	@ApiModelProperty(value = "提成状态 0未提成 1已提成")
	@Column(name = "settlement_commission_status", nullable = true, length = 11)
	private Integer settlementCommissionStatus;

	/**
	 * 付款备注
	 * default value: null
	 */
	@ApiModelProperty(value = "付款备注")
	@Column(name = "settlement_payment_note", nullable = true, length = 100)
	private String settlementPaymentNote;

	/**
	 * 结算单 uploadFile
	 * default value: null
	 */
	@ApiModelProperty(value = "结算单 uploadFile")
//	@Column(name = "settlement_cycle_single", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "settlement_cycle_single")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile settlementCycleSingle;

	/**
	 * 确认收款 付款凭证 uploadFile
	 * default value: null
	 */
	@ApiModelProperty(value = "确认收款 付款凭证 uploadFile")
//	@Column(name = "settlement_confirm_pay_credentials", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "settlement_confirm_pay_credentials")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile settlementConfirmPayCredentials;

	/**
	 * 提成付款方式 0平台划账 1线下打款
	 * default value: null
	 */
	@ApiModelProperty(value = "提成付款方式 0平台划账 1线下打款")
	@Column(name = "settlement_commission_payment_type", nullable = true, length = 11)
	private Integer settlementCommissionPaymentType;

	/**
	 * 提成 付款凭证 ,隔开
	 * default value: null
	 */
	@ApiModelProperty(value = "提成 付款凭证 ,隔开")
	@Column(name = "settlement_payment_credentials", nullable = true, length = 30)
	private String settlementPaymentCredentials;
}
