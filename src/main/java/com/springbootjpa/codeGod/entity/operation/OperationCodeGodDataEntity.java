package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 码神资料列表
 */
@Data
@ApiModel(value = "码神资料列表")
@Entity
@Table(name = "operation_code_god_data")
public class OperationCodeGodDataEntity extends AbstractEntity implements Serializable {

	/**
	 * 已完成项目数量
	 * default value: null
	 */
	@ApiModelProperty(value = "已完成项目数量")
	@Column(name = "amount_project", nullable = true, length = 20)
	private Long amountProject;

	/**
	 * 累计合同金额
	 * default value: null
	 */
	@ApiModelProperty(value = "累计合同金额")
	@Column(name = "gross_account", nullable = true, length = 20)
	private Long grossAccount;

	/**
	 * 签约码神数量
	 * default value: null
	 */
	@ApiModelProperty(value = "签约码神数量")
	@Column(name = "amount_sign_contract", nullable = true, length = 20)
	private Long amountSignContract;

	/**
	 * 全国服务团队数量
	 * default value: null
	 */
	@ApiModelProperty(value = "全国服务团队数量")
	@Column(name = "amount_team", nullable = true, length = 20)
	private Long amountTeam;
}
