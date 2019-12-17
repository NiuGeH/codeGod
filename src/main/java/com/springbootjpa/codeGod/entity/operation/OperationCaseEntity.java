package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
	@Column(name = "case_order", nullable = true, length = 100)
	private String caseOrder;

	/**
	 * 是否显示：0不显示，1显示
	 * default value: null
	 */
	@ApiModelProperty(value = "是否显示：0显示，1不显示")
	@Column(name = "display", nullable = true, length = 2)
	private Integer display;
	@Transient
	private String displayStr;

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
