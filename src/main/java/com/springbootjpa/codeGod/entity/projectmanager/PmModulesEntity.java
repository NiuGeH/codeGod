package com.springbootjpa.codeGod.entity.projectmanager;



import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 模块表
 */
@Data
@ApiModel(value = "模块表")
@Entity
@Table(name = "pm_modules")
public class PmModulesEntity extends AbstractEntity implements Serializable {

	/**
	 * 模块名称
	 * default value: null
	 */
	@ApiModelProperty(value = "模块类型")
	@Column(name = "module_name", nullable = true, length = 50)
	private String moduleName;

	/**
	 * 模块类型
	 * default value: null
	 */
	@ApiModelProperty(value = "模块类型")
	@Column(name = "module_type", nullable = true, length = 11)
	private Integer moduleType;
	@Transient
	private String moduleTypeString;

	/**
	 * 模块分类（招聘的岗位）
	 * default value: null
	 */
	@ApiModelProperty(value = "模块分类（招聘的岗位）")
	@Column(name = "recruitmen_id", nullable = true, length = 11)
	private Integer recruitmenId;
	@Transient
	private String recruitmenString;
	/**
	 * 技术栈
	 * default value: null
	 */
	@ApiModelProperty(value = "技术栈")
	@Column(name = "technology_stack", nullable = true, length = 11)
	private Integer technologyStack;
	@Transient
	private String technologyStackString;
	/**
	 * 模块描述
	 * default value: null
	 */
	@ApiModelProperty(value = "模块描述")
	@Column(name = "module_describe", nullable = true, length = 20)
	private String moduleDescribe;

	/**
	 * 工作量
	 * default value: null
	 */
	@ApiModelProperty(value = "工作量")
	@Column(name = "module_workload", nullable = true, length = 11)
	private Integer moduleWorkload;

	/**
	 * 里程碑日期
	 * default value: null
	 */
	@ApiModelProperty(value = "里程碑日期")
	@Column(name = "module_milestone_date", nullable = true )
	private Date moduleMilestoneDate;

	/**
	 * 状态（是否已被选择）
	 * default value: null
	 */
	@ApiModelProperty(value = "状态（是否已被选择）")
	@Column(name = "status", nullable = true, length = 11)
	private Integer status;

	/**
	 * 是否完成
	 * default value: null
	 */
	@ApiModelProperty(value = "是否完成")
	@Column(name = "accomplish", nullable = true, length = 11)
	private Integer accomplish;


	/**
	 * 是否完成
	 * default value: null
	 */
	@ApiModelProperty(value = "是否删除")
	@Column(name = "module_status", nullable = true, length = 11)
	private Integer moduleStatus;


	/**
	 * 当前进度
	 * default value: null
	 */
	@ApiModelProperty(value = "当前进度")
	@Column(name = "schedule", nullable = true, length = 11)
	private Integer schedule;

	/**
	 * 项目ID
	 * default value: null
	 */
//	@ApiModelProperty(value = "项目ID")
//	@Column(name = "project_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmProjectEntity pmProjectEntity;
	@Transient
	private Long projectId;

	/**
	 * 团队人ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmTeamEntity pmTeamEntity;
	@Transient
	private Long teamId;


}
