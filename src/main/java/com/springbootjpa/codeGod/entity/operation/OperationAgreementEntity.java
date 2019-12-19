package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 文案协议列表
 */
@Data
@ApiModel(value = "文案协议列表")
@Entity
@Table(name = "operation_agreement")
public class OperationAgreementEntity extends AbstractEntity implements Serializable {

	/**
	 * 协议名称
	 * default value: null
	 */
	@ApiModelProperty(value = "协议名称")
	@Column(name = "name", nullable = true, length = 255)
	private String name;

	/**
	 * 协议相对路径或者具体内容
	 * default value: null
	 */
	@ApiModelProperty(value = "协议相对路径或者具体内容")
	@Column(name = "value", nullable = true)
	private String value;

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

}
