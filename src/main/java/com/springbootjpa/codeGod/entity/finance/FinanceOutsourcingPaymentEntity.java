package com.springbootjpa.codeGod.entity.finance;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberWageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 人力外包付款审核列表
 */
@Data
@ApiModel(value = "人力外包付款审核列表")
@Entity
@Table(name = "finance_outsourcing_payment")
public class FinanceOutsourcingPaymentEntity extends AbstractEntity implements Serializable {

	/**
	 * 人力外包工资表id
	 * default value: null
	 */
	@ApiModelProperty(value = "人力外包工资表id")
//	@Column(name = "wage_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wage_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberWageEntity wage;

	/**
	 * 审核材料地址，多个材料用”,“隔开，有考勤记录和月报
	 * default value: null
	 */
	@ApiModelProperty(value = "审核材料地址，多个材料用”,“隔开，有考勤记录和月报")
	@Column(name = "check_file", nullable = true, length = 255)
	private String checkFile;

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
