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
 * 勋章列表
 */
@Data
@ApiModel(value = "勋章列表")
@Entity
@Table(name = "operation_medal")
public class OperationMedalEntity extends AbstractEntity implements Serializable {

	/**
	 * 勋章名称
	 * default value: null
	 */
	@ApiModelProperty(value = "勋章名称")
	@Column(name = "medal_name", nullable = true, length = 100)
	private String medalName;

	/**
	 * 图标，上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "图标，上传文件id")
//	@Column(name = "medal_photo", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medal_photo")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile medalPhoto;

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
