package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 子栏目列表
 */
@Data
@ApiModel(value = "子栏目列表")
@Entity
@Table(name = "operation_subtopic")
public class OperationSubtopicEntity extends AbstractEntity implements Serializable {

	/**
	 * 子栏目名称
	 * default value: null
	 */
	@ApiModelProperty(value = "子栏目名称")
	@Column(name = "subtopic_name", nullable = true, length = 100)
	private String subtopicName;

	/**
	 * 所属栏目id
	 * default value: null
	 */
	@ApiModelProperty(value = "所属栏目id")
//	@Column(name = "topic_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private OperationTopicEntity topic;

	/**
	 * 子栏目排序
	 * default value: null
	 */
	@ApiModelProperty(value = "子栏目排序")
	@Column(name = "order", nullable = true, length = 100)
	private String order;

	/**
	 * 内容
	 * default value: null
	 */
	@ApiModelProperty(value = "内容")
	@Column(name = "content", nullable = true)
	private String content;

	/**
	 * 地址
	 * default value: null
	 */
	@ApiModelProperty(value = "路径地址")
	@Column(name = "url", nullable = true, length = 100)
	private String url;

	/**
	 * 创建时间
	 * default value: null
	 */
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time", nullable = true)
	private String createTime;

	/**
	 * 创建用户
	 * default value: null
	 */
	@ApiModelProperty(value = "创建用户")
	@Column(name = "create_user", nullable = true, length = 255)
	private String createUser;

	/**
	 * 更新时间
	 * default value: null
	 */
	@ApiModelProperty(value = "更新时间")
	@Column(name = "modify_time", nullable = true)
	private String modifyTime;

	/**
	 * 更新用户
	 * default value: null
	 */
	@ApiModelProperty(value = "更新用户")
	@Column(name = "modify_user", nullable = true, length = 255)
	private String modifyUser;
}
