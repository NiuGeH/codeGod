package com.springbootjpa.codeGod.entity.projectmanager;


import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 合同表
 */
@Data
@ApiModel(value = "合同表")
@Entity
@Table(name = "pm_contract")
public class PmContractEntity extends AbstractEntity implements Serializable {

	/**
	 * 合同名称
	 * default value: null
	 */
	@ApiModelProperty(value = "合同名称")
	@Column(name = "contract_name", nullable = true, length = 20)
	private String contractName;

	/**
	 * 合同金额
	 * default value: null
	 */
	@ApiModelProperty(value = "合同金额")
	@Column(name = "contract_money", nullable = true)
	private Double contractMoney;

	/**
	 * 实际金额
	 * default value: null
	 */
	@ApiModelProperty(value = "实际金额")
	@Column(name = "actual_amount", nullable = true )
	private Double actualAmount;

	/**
	 * 签订日期
	 * default value: null
	 */
	@ApiModelProperty(value = "签订日期")
	@Column(name = "contract_date", nullable = true )
	private Date contractDate;

	/**
	 * 付款方式
	 * default value: null
	 */
	@ApiModelProperty(value = "付款方式")
	@Column(name = "payment_method", nullable = true, length = 11)
	private Integer paymentMethod;

	/**
	 * 付款方式说明
	 * default value: null
	 */
	@ApiModelProperty(value = "付款方式说明")
	@Column(name = "explains", nullable = true, length = 255)
	private String explains;

	/**
	 * 合同文件
	 * default value: null
	 */
	@ApiModelProperty(value = "合同文件")
	@Column(name = "contract_document", nullable = true, length = 11)
	private Integer contractDocument;

	/**
	 * 关联项目编号
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmProjectEntity pmProjectEntity;

	/**
	 * 关联需求编号
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "demand_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmDemandEntity pmDemandEntity;
}
