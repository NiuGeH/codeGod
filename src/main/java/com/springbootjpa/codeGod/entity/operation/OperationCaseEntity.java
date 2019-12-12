package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 案例类型列表
 */
@Data
@ApiModel(value = "案例类型列表")
@Entity
@Table(name = "operation_case")
public class OperationCaseEntity extends AbstractEntity implements Serializable {

	/**
	 * 案例名称
	 * default value: null
	 */
	@ApiModelProperty(value = "案例名称")
	@Column(name = "case_name", nullable = true, length = 100)
	private String caseName;

	/**
	 * 案例排序
	 * default value: null
	 */
	@ApiModelProperty(value = "案例排序")
	@Column(name = "order", nullable = true, length = 100)
	private String order;

	/**
	 * 是否显示：0不显示，1显示
	 * default value: null
	 */
	@ApiModelProperty(value = "是否显示：0不显示，1显示")
	@Column(name = "display", nullable = true, length = 2)
	private Integer display;

	/**
	 * 创建时间
	 * default value: null
	 */
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time", nullable = true)
	private String createTime;

	/**
	 * 创建用户
	 * default value: null
	 */
	@ApiModelProperty(value = "创建用户")
	@Column(name = "create_user", nullable = true, length = 255)
	private String createUser;

	/**
	 * 更新时间
	 * default value: null
	 */
	@ApiModelProperty(value = "更新时间")
	@Column(name = "modify_time", nullable = true)
	private String modifyTime;

	/**
	 * 更新用户
	 * default value: null
	 */
	@ApiModelProperty(value = "更新用户")
	@Column(name = "modify_user", nullable = true, length = 255)
	private String modifyUser;
}
