package com.springbootjpa.codeGod.entity.humanResources;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 用户案例
 */
@Data
@ApiModel(value = "用户案例")
@Entity
@Table(name = "member_case")
public class MemberCaseEntity extends AbstractEntity implements Serializable {

	/**
	 * 关联的用户ID
	 * default value: null
	 */
	@ApiModelProperty(value = "关联的用户ID")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@NotFound(action = NotFoundAction.IGNORE)
//	@Column(name = "member_id", )
	private MemberEntity memberId;

	/**
	 * 是否为平台项目
	 * default value: null
	 */
	@ApiModelProperty(value = "是否为平台项目")
	@Column(name = "case_platform_project", nullable = true, length = 20)
	private String casePlatformProject;

	/**
	 * 项目名称
	 * default value: null
	 */
	@ApiModelProperty(value = "项目名称")
	@Column(name = "case_project_name", nullable = true, length = 20)
	private String caseProjectName;

	/**
	 * 图片 关联 uploadfile
	 * default value: null
	 */
	@ApiModelProperty(value = "图片 关联 uploadfile")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "case_photo")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile casePhoto;

	/**
	 * 编码能力
	 * default value: null
	 */
	@ApiModelProperty(value = "编码能力")
	@Column(name = "case_coding_ability", nullable = true, length = 20)
	private String caseCodingAbility;

	/**
	 * 沟通能力
	 * default value: null
	 */
	@ApiModelProperty(value = "沟通能力")
	@Column(name = "case_ability_to_communicate", nullable = true, length = 20)
	private String caseAbilityToCommunicate;

	/**
	 * 进度表现
	 * default value: null
	 */
	@ApiModelProperty(value = "进度表现")
	@Column(name = "case_schedule_performance", nullable = true, length = 20)
	private String caseSchedulePerformance;

	/**
	 * 开发周期
	 * default value: null
	 */
	@ApiModelProperty(value = "开发周期")
	@Column(name = "case_the_development_cycle", nullable = true, length = 20)
	private String caseTheDevelopmentCycle;

	/**
	 * 项目贡献度
	 * default value: null
	 */
	@ApiModelProperty(value = "项目贡献度")
	@Column(name = "case_project_contribution", nullable = true, length = 20)
	private String caseProjectContribution;

	/**
	 * 所用技能
	 * default value: null
	 */
	@ApiModelProperty(value = "所用技能")
	@Column(name = "case_the_skills_used", nullable = true, length = 20)
	private String caseTheSkillsUsed;

	/**
	 * 参与角色
	 * default value: null
	 */
	@ApiModelProperty(value = "参与角色")
	@Column(name = "case_participate_in_the_role", nullable = true, length = 20)
	private String caseParticipateInTheRole;

	/**
	 * 绩效点评
	 * default value: null
	 */
	@ApiModelProperty(value = "绩效点评")
	@Column(name = "case_the_performance_review", nullable = true, length = 20)
	private String caseThePerformanceReview;
}
