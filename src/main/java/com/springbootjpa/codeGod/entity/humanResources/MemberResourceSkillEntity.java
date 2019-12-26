package com.springbootjpa.codeGod.entity.humanResources;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 用户技术表
 */
@Data
@ApiModel(value = "用户技术表")
@Entity
@Table(name = "member_resource_skill")
public class MemberResourceSkillEntity extends AbstractEntity implements Serializable {

	/**
	 * 技术id
	 * default value: null
	 */
	@ApiModelProperty(value = "技术id")
//	@Column(name = "skill_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private OperationSkillEntity skillId;

	/**
	 * 0掌握 1熟练 2精通
	 * default value: null
	 */
	@ApiModelProperty(value = "0掌握 1熟练 2精通")
	@Column(name = "skill_proficiency", nullable = true, length = 11)
	private Integer skillProficiency;

	/**
	 * 用户资源池ID
	 * default value: null
	 */
	@ApiModelProperty(value = "用户资源池ID")
	@Column(name = "member_resource_id", nullable = true, length = 20)
//	@ManyToOne(targetEntity = MemberResourceEentity.class)
//	@JoinColumn(name = "member_resource_id",referencedColumnName = "id")
//	@NotFound(action = NotFoundAction.IGNORE)
	private Long memberResourceId;
}
