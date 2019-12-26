package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 个人中心的菜单图标列表
 */
@Data
@ApiModel(value = "个人中心的菜单图标列表")
@Entity
@Table(name = "operation_personal_menu")
public class OperationPersonalMenuEntity extends AbstractEntity implements Serializable {

	/**
	 * 我的邀请图标，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "我的邀请图标，关联上传文件id")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "my_invitation")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile myInvitation;

	/**
	 * 需求列表图标，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "需求列表图标，关联上传文件id")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "demand_list")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile demandList;

	/**
	 * 推荐项目图标，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "推荐项目图标，关联上传文件id")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recommend_project")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile recommendProject;

	/**
	 * 发起工单图标，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "发起工单图标，关联上传文件id")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publish_order")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile publishOrder;

	/**
	 * 我的经纪人图标，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "我的经纪人图标，关联上传文件id")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "my_agent")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile myAgent;

	/**
	 * 发布需求图标，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "发布需求图标，关联上传文件id")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publish_demand")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile publishDemand;

	/**
	 * 运维需求图标，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "运维需求图标，关联上传文件id")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operation_demand")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile operationDemand;

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
