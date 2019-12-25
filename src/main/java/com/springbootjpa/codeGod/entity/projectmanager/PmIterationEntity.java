package com.springbootjpa.codeGod.entity.projectmanager;



import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * 迭代表   
 */
@Data
@ApiModel(value = "迭代表   ")
@Entity
@Table(name = "pm_iteration")
public class PmIterationEntity extends AbstractEntity implements Serializable {

	/**
	 * 迭代时间
	 * default value: null
	 */
	@ApiModelProperty(value = "迭代时间")
	@Column(name = "iteration_time", nullable = true)
	private java.util.Date iterationTime;

	/**
	 * 迭代费用
	 * default value: null
	 */
	@ApiModelProperty(value = "迭代费用")
	@Column(name = "iteration_money", nullable = true)
	private Double iterationMoney;

	/**
	 * 变更内容
	 * default value: null
	 */
	@ApiModelProperty(value = "变更内容")
	@Column(name = "iteration_content", nullable = true, length = 255)
	private String iterationContent;

	/**
	 * 模块ID
	 * default value: null
	 */
//	@ApiModelProperty(value = "模块ID")
//	@Column(name = "module_id", nullable = true, length = 20)
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private List<PmModulesEntity> pmModulesEntity;

	/**
	 * 项目ID
	 * default value: null
	 */
//	@ApiModelProperty(value = "模块ID")
//	@Column(name = "module_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmProjectEntity pmProjectEntity;
}
