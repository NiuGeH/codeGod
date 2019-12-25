package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 合作企业列表
 */
@Data
@ApiModel(value = "合作企业列表")
@Entity
@Table(name = "operation_company")
public class OperationCompanyEntity extends AbstractEntity implements Serializable {

	/**
	 * 企业名称
	 * default value: null
	 */
	@ApiModelProperty(value = "企业名称")
	@Column(name = "company_name", nullable = true, length = 100)
	private String companyName;

	/**
	 * 企业地址
	 * default value: null
	 */
	@ApiModelProperty(value = "企业地址")
	@Column(name = "company_address", nullable = true, length = 100)
	private String companyAddress;

	/**
	 * 企业电话
	 * default value: null
	 */
	@ApiModelProperty(value = "企业电话")
	@Column(name = "team_phone", nullable = true, length = 20)
	private String teamPhone;

	/**
	 * 开户名称
	 * default value: null
	 */
	@ApiModelProperty(value = "开户名称")
	@Column(name = "account_name", nullable = true, length = 100)
	private String accountName;

	/**
	 * 开户行
	 * default value: null
	 */
	@ApiModelProperty(value = "开户行")
	@Column(name = "bank", nullable = true, length = 100)
	private String bank;

	/**
	 * 收款账户
	 * default value: null
	 */
	@ApiModelProperty(value = "收款账户")
	@Column(name = "account_number", nullable = true, length = 100)
	private String accountNumber;

	/**
	 * 创建时间
	 * default value: null
	 */
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time", nullable = true)
	private Date createTime;

	/**
	 * 更新时间
	 * default value: null
	 */
	@ApiModelProperty(value = "更新时间")
	@Column(name = "modify_time", nullable = true)
	private Date modifyTime;

	/**
	 * 是否显示：0显示，1不显示
	 * default value: null
	 */
	@ApiModelProperty(value = "是否显示：0显示，1不显示")
	@Column(name = "company_display", nullable = true, length = 11)
	private Integer companyDisplay;
	@Transient
	private String companyDisplayStr;

}
