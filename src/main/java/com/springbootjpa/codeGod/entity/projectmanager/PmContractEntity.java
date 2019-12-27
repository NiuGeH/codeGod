package com.springbootjpa.codeGod.entity.projectmanager;


import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

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
	private Double contractMoney=0.0;

	/**
	 * 实际金额
	 * default value: null
	 */
	@ApiModelProperty(value = "实际金额")
	@Column(name = "actual_amount", nullable = true )
	private Double actualAmount=0.0;

	/**
	 * 签订日期
	 * default value: null
	 */
	@ApiModelProperty(value = "签订日期")
	@Column(name = "contract_date", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date contractDate;

	/**s
	 * 付款方式
	 * default value: null
	 */
	@ApiModelProperty(value = "付款方式")
	@Column(name = "payment_method", nullable = true, length = 255)
	private String paymentMethod;

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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contract_document_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile contractDocument;

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
