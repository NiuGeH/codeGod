package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 新闻列表
 */
@Data
@ApiModel(value = "新闻列表")
@Entity
@Table(name = "operation_news")
public class OperationNewsEntity extends AbstractEntity implements Serializable {

	/**
	 * 新闻标题
	 * default value: null
	 */
	@ApiModelProperty(value = "新闻标题")
	@Column(name = "title", nullable = true, length = 100)
	private String title;

	/**
	 * 内容
	 * default value: null
	 */
	@ApiModelProperty(value = "内容")
	@Column(name = "content", nullable = true)
	private String content;

	/**
	 * 浏览量
	 * default value: null
	 */
	@ApiModelProperty(value = "浏览量")
	@Column(name = "views", nullable = true, length = 20)
	private Long views;

	/**
	 * 是否首页显示：0显示，1不显示
	 * default value: null
	 */
	@ApiModelProperty(value = "是否首页显示：0显示，1不显示")
	@Column(name = "display", nullable = true, length = 2)
	private Integer display;

	/**
	 * 当前状态：0正常，1删除(逻辑)
	 * default value: null
	 */
	@ApiModelProperty(value = "当前状态：0正常，1删除(逻辑)")
	@Column(name = "state", nullable = true, length = 2)
	private Integer state;

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
