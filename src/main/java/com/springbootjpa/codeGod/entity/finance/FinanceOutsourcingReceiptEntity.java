package com.springbootjpa.codeGod.entity.finance;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberSettlementEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 人力外包收款审核列表
 */
@Data
@ApiModel(value = "人力外包收款审核列表")
@Entity
@Table(name = "finance_outsourcing_receipt")
public class FinanceOutsourcingReceiptEntity extends AbstractEntity implements Serializable {

	/**
	 * 人力外包结算表id
	 * default value: null
	 */
	@ApiModelProperty(value = "人力外包结算表id")
//	@Column(name = "settlement_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "settlement_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberSettlementEntity settlement;

	/**
	 * 审核材料，费用清单，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "审核材料，费用清单，关联上传文件id")
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
