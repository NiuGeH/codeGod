package com.springbootjpa.codeGod.entity.finance;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.projectmanager.PmContractEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmSettleAccountsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 项目收款审核列表
 */
@Data
@ApiModel(value = "项目收款审核列表")
@Entity
@Table(name = "finance_project_receipt")
public class FinanceProjectReceiptEntity extends AbstractEntity implements Serializable {

	/**
	 * 合同id
	 * default value: null
	 */
	@ApiModelProperty(value = "合同id")
//	@Column(name = "contract_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contract_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmContractEntity contract;

	/**
	 * 结算单id
	 * default value: null
	 */
	@ApiModelProperty(value = "结算单id")
//	@Column(name = "settlement_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "settlement_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmSettleAccountsEntity settlement;

	/**
	 * 审核材料，关联上传文件id，合同
	 * default value: null
	 */
	@ApiModelProperty(value = "审核材料，关联上传文件id，合同")
//	@Column(name = "check_file", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "check_file")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile checkFile;

	/**
	 * 支付审核：0未审核，1已审核
	 * default value: null
	 */
	@ApiModelProperty(value = "支付审核：0未审核，1已审核")
	@Column(name = "check_state", nullable = true, length = 2)
	private Integer checkState;

	/**
	 * 审核人
	 * default value: null
	 */
	@ApiModelProperty(value = "审核人")
	@Column(name = "check_user", nullable = true, length = 255)
	private String checkUser;

	/**
	 * 审核时间
	 * default value: null
	 */
	@ApiModelProperty(value = "审核时间")
	@Column(name = "check_time", nullable = true)
	private String checkTime;
}
