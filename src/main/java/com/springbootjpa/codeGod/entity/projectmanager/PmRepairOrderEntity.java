package com.springbootjpa.codeGod.entity.projectmanager;



import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(value = "创建人")
	@Column(name = "create_by", nullable = true, length = 11)
	private Integer createBy;

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
	@ApiModelProperty(value = "受理人")
	@Column(name = "acceptor", nullable = true, length = 20)
	private Long acceptor;
}
