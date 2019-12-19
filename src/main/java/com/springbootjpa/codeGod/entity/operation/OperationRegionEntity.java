package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 地域列表
 */
@Data
@ApiModel(value = "地域列表")
@Entity
@Table(name = "operation_region")
public class OperationRegionEntity extends AbstractEntity implements Serializable {

	/**
	 * 城市名称
	 * default value: null
	 */
	@ApiModelProperty(value = "城市名称")
	@Column(name = "city_name", nullable = true, length = 100)
	private String cityName;

	/**
	 * 城市排序
	 * default value: null
	 */
	@ApiModelProperty(value = "城市排序")
	@Column(name = "city_order", nullable = true)
	private Long cityOrder;

	/**
	 * 是否显示：0显示，1不显示
	 * default value: null
	 */
	@ApiModelProperty(value = "是否显示：0显示，1不显示")
	@Column(name = "display", nullable = true, length = 2)
	private Integer display;
	@Transient
	private String displayStr;

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
