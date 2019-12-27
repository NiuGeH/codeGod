package com.springbootjpa.codeGod.entity.projectmanager;



import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

import javax.persistence.*;

/**
 *  结算表
 */
@Data
@ApiModel(value = " 结算表")
@Entity
@Table(name = "pm_settle_accounts")
public class PmSettleAccountsEntity extends AbstractEntity implements Serializable {

	/**
	 * 款项类型
	 * default value: null
	 */
	@ApiModelProperty(value = "款项类型")
	@Column(name = "funds_type", nullable = true, length = 11)
	private Integer fundsType;

	/**
	 * 款型名称
	 * default value: null
	 */
	@ApiModelProperty(value = "款型名称")
	@Column(name = "funds_name", nullable = true, length = 20)
	private String fundsName;

	/**
	 * 应收金额
	 * default value: null
	 */
	@ApiModelProperty(value = "应收金额")
	@Column(name = "amount_receivable", nullable = true )
	private Double amountReceivable;

	/**
	 * 收款备注
	 * default value: null
	 */
	@ApiModelProperty(value = "收款备注")
	@Column(name = "remark", nullable = true, length = 255)
	private String remark;
	/**
	 * 收款备注
	 * default value: null
	 */
	@ApiModelProperty(value = "付款备注")
	@Column(name = "payment_note", nullable = true, length = 255)
	private String payment_note;
	/**
	 * 收款凭证
	 * default value: null
	 */
	@ApiModelProperty(value = "收款凭证")
	@Column(name = "certificate", nullable = true, length = 50)
	private String certificate;

	/**
	 * 项目ID
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmProjectEntity pmProjectEntity;
}
