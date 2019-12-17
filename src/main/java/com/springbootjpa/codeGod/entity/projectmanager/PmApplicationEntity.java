package com.springbootjpa.codeGod.entity.projectmanager;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 需求报名表
 */
@Data
@ApiModel(value = "需求报名表")
@Entity
@Table(name = "pm_application")
public class PmApplicationEntity extends AbstractEntity implements Serializable {

	/**
	 * 产品经理ID
	 * default value: null
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_manager_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEntity memberEntity;

	/**
	 * 需求ID demand_id
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "demand_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmDemandEntity pmDemandEntity;
	@Transient
	private Long demandId;
	/**
	 * 个人诉求
	 * default value: null
	 */
	@ApiModelProperty(value = "个人诉求")
	@Column(name = "remark", nullable = true, length = 20)
	private String remark;

	/**
	 * 项目数
	 * default value: null
	 */
	@ApiModelProperty(value = "项目数")
	@Column(name = "number", nullable = true, length = 11)
	private Integer number;
}
