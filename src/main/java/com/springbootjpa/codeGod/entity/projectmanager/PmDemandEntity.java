package com.springbootjpa.codeGod.entity.projectmanager;



import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 需求表  
 */
@Data
@ApiModel(value = "需求表  ")
@Entity
@Table(name = "pm_demand")
public class PmDemandEntity extends AbstractEntity implements Serializable {

	/**
	 * 发布人
	 * default value: null
	 */
	@ApiModelProperty(value = "发布人")
	@Column(name = "demand_publisher", nullable = true, length = 20)
	private String demandPublisher;

	/**
	 * 联系方式
	 * default value: null
	 */
	@ApiModelProperty(value = "联系方式")
	@Column(name = "demand_contact_information", nullable = true, length = 20)
	private String demandContactInformation;

	/**
	 * 发布时间
	 * default value: null
	 */
	@ApiModelProperty(value = "发布时间")
	@Column(name = "demand_deliver_time", nullable = true)
	private Date demandDeliverTime;

	/**
	 * 项目ID
	 * default value: null
	 */
	//@ApiModelProperty(value = "项目ID")
	//@Column(name = "", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmTeamEntity pmTeamEntity;

	/**
	 * 客户ID
	 * default value: null
	 */
	//@ApiModelProperty(value = "客户ID")
	//@Column(name = "custom_id", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberPrivacyEntity memberPrivacyEntity;

	/**
	 * 开发预算
	 * default value: null
	 */
	@ApiModelProperty(value = "开发预算")
	@Column(name = "demand_budget", nullable = true )
	private Double demandBudget;

	/**
	 * 需求描述
	 * default value: null
	 */
	@ApiModelProperty(value = "需求描述")
	@Column(name = "demand_describe", nullable = true, length = 255)
	private String demandDescribe;

	/**
	 * 交付时限
	 * default value: null
	 */
	@ApiModelProperty(value = "交付时限")
	@Column(name = "demand_delivery_time", nullable = true)
	private java.util.Date demandDeliveryTime;

	/**
	 * 设计文档
	 * default value: null
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "demand_design_document_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile uploadFile;

	/**
	 * 项目地址
	 * default value: null
	 */
	@ApiModelProperty(value = "项目地址")
	@Column(name = "demand_adderss", nullable = true, length = 11)
	private Integer demandAdderss;

	/**
	 * 开发方式
	 * default value: null
	 */
	@ApiModelProperty(value = "开发方式")
	@Column(name = "demand_development_model", nullable = true, length = 11)
	private Integer demandDevelopmentModel;

	/**
	 * 需求状态
	 * default value: null
	 */
	@ApiModelProperty(value = "需求状态")
	@Column(name = "demand_status", nullable = true, length = 11)
	private Integer demandStatus;
	@Transient
	private String demandStatus1;
	/**
	 * 拒绝原因
	 * default value: null
	 */
	@ApiModelProperty(value = "拒绝原因")
	@Column(name = "demand_refusal_cause", nullable = true, length = 255)
	private String demandRefusalCause;

	/**
	 * 产品经理编号
	 * default value: null
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_manager_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEntity memberEntity;
}
