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

/**
 * 文案协议列表
 */
@Data
@ApiModel(value = "文案协议列表")
@Entity
@Table(name = "operation_agreement")
public class OperationAgreementEntity extends AbstractEntity implements Serializable {

	/**
	 * 用户服务协议
	 * default value: null
	 */
	@ApiModelProperty(value = "用户服务协议")
	@Column(name = "user_service", nullable = true)
	private String userService;

	/**
	 * 驻场须知
	 * default value: null
	 */
	@ApiModelProperty(value = "驻场须知")
	@Column(name = "station_notice", nullable = true)
	private String stationNotice;

	/**
	 * 码神签约协议，上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "码神签约协议，上传文件id")
//	@Column(name = "code_god_sign", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_god_sign")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile codeGodSign;

	/**
	 * 人力外包协议，上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "人力外包协议，上传文件id")
//	@Column(name = "outsourcing", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "outsourcing")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile outsourcing;

	/**
	 * 项目签约协议，上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "项目签约协议，上传文件id")
//	@Column(name = "project_sign", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_sign")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile projectSign;

	/**
	 * 兼职协议，上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "兼职协议，上传文件id")
//	@Column(name = "part_time", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "part_time")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile partTime;

	/**
	 * 全职协议，上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "全职协议，上传文件id")
//	@Column(name = "full_time", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "full_time")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile fullTime;

	/**
	 * 推荐奖励
	 * default value: null
	 */
	@ApiModelProperty(value = "推荐奖励")
	@Column(name = "recommend_award", nullable = true, length = 11)
	private Integer recommendAward;

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
