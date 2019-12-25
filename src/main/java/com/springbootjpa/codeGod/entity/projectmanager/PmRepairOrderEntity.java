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
 *  工单表  
 */
@Data
@ApiModel(value = " 工单表  ")
@Entity
@Table(name = "pm_repair_order")
public class PmRepairOrderEntity extends AbstractEntity implements Serializable {

	/**
	 * 创建人
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_by")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEntity createBy;

	/**
	 * 标题
	 * default value: null
	 */
	@ApiModelProperty(value = "标题")
	@Column(name = "title", nullable = true, length = 20)
	private String title;

	/**
	 * 创建时间
	 * default value: null
	 */
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time", nullable = true)
	private java.util.Date createTime;

	/**
	 * 受理人
	 * default value: null
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acceptor")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEntity acceptor;


	@ApiModelProperty(value = "回复内容")
	@Column(name = "reply", nullable = true, length = 255)
	private String reply;


	@ApiModelProperty(value = "内容")
	@Column(name = "content", nullable = true, length = 255)
	private String content;

	/**
	 * 项目ID
	 * default value: null
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmProjectEntity pmProjectEntity;

}
