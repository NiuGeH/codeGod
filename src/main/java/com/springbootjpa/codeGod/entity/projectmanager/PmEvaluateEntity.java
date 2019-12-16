package com.springbootjpa.codeGod.entity.projectmanager;



import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import javax.persistence.*;

/**
 * 个人评价表  
 */
@Data
@ApiModel(value = "个人评价表  ")
@Entity
@Table(name = "pm_evaluate")
public class PmEvaluateEntity extends AbstractEntity implements Serializable {

	/**
	 * 项目贡献度
	 * default value: null
	 */
	@ApiModelProperty(value = "项目贡献度")
	@Column(name = "contribution", nullable = true )
	private Double contribution;

	/**
	 * 编码能力
	 * default value: null
	 */
	@ApiModelProperty(value = "编码能力")
	@Column(name = "code_capacity", nullable = true)
	private Double codeCapacity;

	/**
	 * 沟通能力
	 * default value: null
	 */
	@ApiModelProperty(value = "沟通能力")
	@Column(name = "communication_skills", nullable = true)
	private Double communicationSkills;

	/**
	 * 进度表现
	 * default value: null
	 */
	@ApiModelProperty(value = "进度表现")
	@Column(name = "schedule_performance", nullable = true)
	private Double schedulePerformance;

	/**
	 * 绩效点评
	 * default value: null
	 */
	@ApiModelProperty(value = "绩效点评")
	@Column(name = "performance_review", nullable = true )
	private Double performanceReview;

	/**
	 * team_id
	 * default value: null
	 */
	@ApiModelProperty(value = "team_id")
	@Column(name = "team_id", nullable = true, length = 20)
	private Long teamId;
}
