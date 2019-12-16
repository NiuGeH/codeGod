package com.springbootjpa.codeGod.entity.projectmanager;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

import javax.persistence.*;
import javax.swing.*;

/**
 * 项目评价表 
 */
@Data
@ApiModel(value = "项目评价表 ")
@Entity
@Table(name = "pm_rating")
public class PmRatingEntity extends AbstractEntity implements Serializable {

	/**
	 * 评分
	 * default value: null
	 */
	@ApiModelProperty(value = "评分")
	@Column(name = "grade", nullable = true, length = 11)
	private Integer grade;

	/**
	 * 评价
	 * default value: null
	 */
	@ApiModelProperty(value = "评价")
	@Column(name = "evaluate", nullable = true, length = 20)
	private String evaluate;

	/**
	 * 显示到首页
	 * default value: null
	 */
	@ApiModelProperty(value = "显示到首页")
	@Column(name = "shows", nullable = true, length = 11)
	private Integer shows;

	/**
	 * 成果展示
	 * default value: null
	 */
	@ApiModelProperty(value = "成果展示")
	@Column(name = "performance_id", nullable = true, length = 11)
	private Integer performanceId;

	/**
	 * 项目ID
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="project_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmProjectEntity projectEntity;
}
