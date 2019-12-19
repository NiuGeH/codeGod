package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 资源类型—技术栈关联表
 */
@Data
@ApiModel(value = "资源类型—技术栈关联表")
@Entity
@Table(name = "operation_resource_skill")
public class OperationResourceSkillEntity extends AbstractEntity implements Serializable {

	/**
	 * resource_id 资源类型id
	 * default value: null
	 */
//	@ApiModelProperty(value = "资源类型id")
//	@Column(name = "resource_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "resource_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private OperationResourceEntity resource;

	/**
	 * skill_id 技术栈id
	 * default value: null
	 */
//	@ApiModelProperty(value = "技术栈id")
//	@Column(name = "skill_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "skill_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private OperationSkillEntity skill;


	@Transient
	private List<OperationSkillEntity> operationSkillEntityList;
}
