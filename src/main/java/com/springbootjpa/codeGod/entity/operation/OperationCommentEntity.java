package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 新闻评论列表
 */
@Data
@ApiModel(value = "新闻评论列表")
@Entity
@Table(name = "operation_comment")
public class OperationCommentEntity extends AbstractEntity implements Serializable {

	/**
	 * 关联的新闻id
	 * default value: null
	 */
	@ApiModelProperty(value = "关联的新闻id")
//	@Column(name = "news_id", nullable = false, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "news_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private OperationNewsEntity news;

	/**
	 * 关联的用户id
	 * default value: null
	 */
	@ApiModelProperty(value = "关联的用户id")
//	@Column(name = "member_id", nullable = false, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberEntity member;

	/**
	 * 内容
	 * default value: null
	 */
	@ApiModelProperty(value = "内容")
	@Column(name = "content", nullable = true)
	private String content;

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
