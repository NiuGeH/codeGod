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
import java.util.List;

/**
 * 资源类型列表
 */
@Data
@ApiModel(value = "资源类型列表")
@Entity
@Table(name = "operation_resource")
public class OperationResourceEntity extends AbstractEntity implements Serializable {

	/**
	 * 资源类型名称
	 * default value: null
	 */
	@ApiModelProperty(value = "资源类型名称")
	@Column(name = "resource_name", nullable = true, length = 100)
	private String resourceName;

	/**
	 * 图标，上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "图标，上传文件id")
//	@Column(name = "resource_photo", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_photo")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile resourcePhoto;

	/**
	 * 数量
	 * default value: null
	 */
	@ApiModelProperty(value = "数量")
	@Column(name = "amount", nullable = true, length = 20)
	private Long amount;

	/**
	 * 资源排序
	 * default value: null
	 */
	@ApiModelProperty(value = "资源排序")
	@Column(name = "resource_order", nullable = true)
	private Long resourceOrder;

	/**
	 * 技术栈名称
	 */
	@Transient
	private List<String> skillNames;

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
