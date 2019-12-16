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
 *  招聘表
 */
@Data
@ApiModel(value = " 招聘表")
@Entity
@Table(name = "pm_recruitment")
public class PmRecruitmentEntity extends AbstractEntity implements Serializable {

	/**
	 * 招聘职务
	 * default value: null
	 */
	@ApiModelProperty(value = "招聘职务")
	@Column(name = "recruitment_duty", nullable = true, length = 11)
	private Integer recruitmentDuty;

	/**
	 * 要求角色
	 * default value: null
	 */
	@ApiModelProperty(value = "要求角色")
	@Column(name = "recruitment_role", nullable = true, length = 11)
	private Integer recruitmentRole;

	/**
	 * 招聘人数
	 * default value: null
	 */
	@ApiModelProperty(value = "招聘人数")
	@Column(name = "recruitment_number", nullable = true, length = 11)
	private Integer recruitmentNumber;

	/**
	 * 费用
	 * default value: null
	 */
	@ApiModelProperty(value = "费用")
	@Column(name = "recruitment_cost", nullable = true, length = 11)
	private Integer recruitmentCost;

	/**
	 * 工作内容
	 * default value: null
	 */
	@ApiModelProperty(value = "工作内容")
	@Column(name = "recruitment_content", nullable = true, length = 20)
	private String recruitmentContent;

	/**
	 * 驻场要求
	 * default value: null
	 */
	@ApiModelProperty(value = "驻场要求")
	@Column(name = "recruitment_require", nullable = true, length = 11)
	private Integer recruitmentRequire;

	/**
	 * 技能要求
	 * default value: null
	 */
	@ApiModelProperty(value = "技能要求")
	@Column(name = "recruitment_skill", nullable = true, length = 20)
	private String recruitmentSkill;

	/**
	 * 对外显示
	 * default value: null
	 */
	@ApiModelProperty(value = "对外显示")
	@Column(name = "recruitment_show", nullable = true, length = 11)
	private Integer recruitmentShow;

	/**
	 * 项目ID
	 * default value: null
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmProjectEntity pmProjectEntity;
}
