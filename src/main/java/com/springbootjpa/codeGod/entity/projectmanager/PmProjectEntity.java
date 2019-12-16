package com.springbootjpa.codeGod.entity.projectmanager;



import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 项目表
 */
@Data
@ApiModel(value = "项目表")
@Entity
@Table(name = "pm_project")
public class PmProjectEntity extends AbstractEntity implements Serializable {

	/**
	 * 产品经理编号
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_manager_Id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEntity memberEntity;

	/**
	 * 关联需求编号
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "demand_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmDemandEntity pmDemandEntity;

	/**
	 * 客户ID
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberPrivacyEntity memberPrivacyEntity;

	/**
	 * 项目名称
	 * default value: null
	 */
	@ApiModelProperty(value = "项目名称")
	@Column(name = "project_name", nullable = true, length = 20)
	private String projectName;

	/**
	 * 项目状态
	 * default value: null
	 */
	@ApiModelProperty(value = "项目状态")
	@Column(name = "project_status", nullable = true, length = 11)
	private Integer projectStatus;

	/**
	 * 备注
	 * default value: null
	 */
	@ApiModelProperty(value = "备注")
	@Column(name = "project_remark", nullable = true, length = 255)
	private String projectRemark;

	/**
	 * 项目类型
	 * default value: null
	 */
	@ApiModelProperty(value = "项目类型")
	@Column(name = "project_type", nullable = true, length = 11)
	private Integer projectType;

	/**
	 * 项目周期
	 * default value: null
	 */
	@ApiModelProperty(value = "项目周期")
	@Column(name = "project_period", nullable = true, length = 25)
	private String projectPeriod;

	/**
	 * 关键字
	 * default value: null
	 */
	@ApiModelProperty(value = "关键字")
	@Column(name = "project_keyword", nullable = true, length = 255)
	private String projectKeyword;

	/**
	 * 项目介绍
	 * default value: null
	 */
	@ApiModelProperty(value = "项目介绍")
	@Column(name = "project_introduce", nullable = true, length = 255)
	private String projectIntroduce;

	/**
	 * 私有项目
	 * default value: null
	 */
	@ApiModelProperty(value = "私有项目")
	@Column(name = "Private_project", nullable = true, length = 11)
	private Integer privateProject;

	/**
	 * 密码
	 * default value: null
	 */
	@ApiModelProperty(value = "密码")
	@Column(name = "project_password", nullable = true, length = 11)
	private Integer projectPassword;
}
