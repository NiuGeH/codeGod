package com.springbootjpa.codeGod.entity.humanResources;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 用户技能
 */
@Data
@ApiModel(value = "用户技能")
@Entity
@Table(name = "member_resource")
public class MemberResourcEentity extends AbstractEntity implements Serializable {

	/**
	 * 资源Id
	 * default value: null
	 */
	@ApiModelProperty(value = "资源Id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_operation_resource")
	@NotFound(action = NotFoundAction.IGNORE)
//	@Column(name = "member_operation_resource", nullable = true, length = 20)
	private OperationResourceEntity memberOperationResource;

	/**
	 * 熟练度 0参与者 1独挡一面
	 * default value: null
	 */
	@ApiModelProperty(value = "熟练度 0参与者 1独挡一面")
	@Column(name = "member_proficiency", nullable = true, length = 11)
	private Integer memberProficiency;

}
