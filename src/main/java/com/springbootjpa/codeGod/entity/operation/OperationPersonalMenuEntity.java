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

/**
 * 个人中心的菜单列表
 */
@Data
@ApiModel(value = "个人中心的菜单列表")
@Entity
@Table(name = "operation_personal_menu")
public class OperationPersonalMenuEntity extends AbstractEntity implements Serializable {

	/**
	 * 菜单名称
	 * default value: null
	 */
	@ApiModelProperty(value = "菜单名称")
	@Column(name = "menu_name", nullable = true, length = 100)
	private String menuName;

	/**
	 * 图标，上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "图标，上传文件id")
//	@Column(name = "menu_photo", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_photo")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile menuPhoto;

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
