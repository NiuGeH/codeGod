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
	@Column(name = "user_service_agreement", nullable = true)
	private String userServiceAgreement;

	/**
	 * 驻场须知
	 * default value: null
	 */
	@ApiModelProperty(value = "驻场须知")
	@Column(name = "resident_notice", nullable = true)
	private String residentNotice;

	/**
	 * 码神签约协议，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "码神签约协议，关联上传文件id")
//	@Column(name = "code_god_sign_agreement", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_god_sign_agreement")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile codeGodSignAgreement;

	/**
	 * 人力外包协议，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "人力外包协议，关联上传文件id")
//	@Column(name = "worker_outsourcing_agreement", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "worker_outsourcing_agreement")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile workerOutsourcingAgreement;

	/**
	 * 项目签约协议，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "项目签约协议，关联上传文件id")
//	@Column(name = "project_sign_agreement", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_sign_agreement")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile projectSignAgreement;

	/**
	 * 兼职协议，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "兼职协议，关联上传文件id")
//	@Column(name = "parttime_agreement", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parttime_agreement")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile parttimeAgreement;

	/**
	 * 全职协议，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "全职协议，关联上传文件id")
//	@Column(name = "fulltime_agreement", nullable = true, length = 20)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fulltime_agreement")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile fulltimeAgreement;

	/**
	 * 推荐奖励，单位元
	 * default value: null
	 */
	@ApiModelProperty(value = "推荐奖励，单位元")
	@Column(name = "recommend_reward", nullable = true, length = 11)
	private Integer recommendReward;

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
