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
 * 技术栈列表
 */
@Data
@ApiModel(value = "技术栈列表")
@Entity
@Table(name = "operation_skill")
public class OperationSkillEntity extends AbstractEntity implements Serializable {

	/**
	 * 技术栈名称
	 * default value: null
	 */
	@ApiModelProperty(value = "技术栈名称")
	@Column(name = "skill_name", nullable = true, length = 100)
	private String skillName;

	/**
	 * 技术栈排序
	 * default value: null
	 */
	@ApiModelProperty(value = "技术栈排序")
	@Column(name = "skill_order", nullable = true, length = 100)
	private Long skillOrder;

	/**
	 * 是否显示：0显示，1不显示
	 * default value: null
	 */
	@ApiModelProperty(value = "是否显示：0显示，1不显示")
	@Column(name = "display", nullable = true, length = 2)
	private Integer display;

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
