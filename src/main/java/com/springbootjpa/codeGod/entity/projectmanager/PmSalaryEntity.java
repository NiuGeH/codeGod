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
 *  工资表
 */
@Data
@ApiModel(value = " 工资表")
@Entity
@Table(name = "pm_salary")
public class PmSalaryEntity extends AbstractEntity implements Serializable {

	/**
	 * 款项名称
	 * default value: null
	 */
	@ApiModelProperty(value = "款项名称")
	@Column(name = "payment_name", nullable = true, length = 20)
	private String paymentName;

	/**
	 * 工资
	 * default value: null
	 */
	@ApiModelProperty(value = "工资")
	@Column(name = "salary", nullable = true)
	private Double salary;

	/**
	 * 社保缴纳
	 * default value: null
	 */
	@ApiModelProperty(value = "社保缴纳")
	@Column(name = "social_security", nullable = true )
	private Double socialSecurity;

	/**
	 * 公积金缴纳
	 * default value: null
	 */
	@ApiModelProperty(value = "公积金缴纳")
	@Column(name = "accumulation_fund", nullable = true )
	private Double accumulationFund;

	/**
	 * 个税缴纳
	 * default value: null
	 */
	@ApiModelProperty(value = "个税缴纳")
	@Column(name = "individual_income_tax", nullable = true)
	private Double individualIncomeTax;

	/**
	 * 奖金
	 * default value: null
	 */
	@ApiModelProperty(value = "奖金")
	@Column(name = "bonus", nullable = true )
	private Double bonus;

	/**
	 * 实发工资
	 * default value: null
	 */
	@ApiModelProperty(value = "实发工资")
	@Column(name = "take_home_pay", nullable = true)
	private Double takeHomePay;

	/**
	 * 付款备注
	 * default value: null
	 */
	@ApiModelProperty(value = "付款备注")
	@Column(name = "remark", nullable = true, length = 255)
	private String remark;

	/**
	 * 付款方式
	 * default value: null
	 */
	@ApiModelProperty(value = "付款方式")
	@Column(name = "payment_method", nullable = true, length = 11)
	private Integer paymentMethod;

	/**
	 * 付款凭证
	 * default value: null
	 */
	@ApiModelProperty(value = "付款凭证")
	@Column(name = "certificate", nullable = true, length = 11)
	private Integer certificate;

	/**
	 * 人员ID
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmTeamEntity pmTeamEntity;
}
