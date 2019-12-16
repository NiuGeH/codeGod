package com.springbootjpa.codeGod.entity.finance;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberSettlementEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.entity.operation.OperationAgreementEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmSettleAccountsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 提成审核表
 */
@Data
@ApiModel(value = "提成审核表")
@Entity
@Table(name = "finance_commission")
public class FinanceCommissionEntity extends AbstractEntity implements Serializable {

	/**
	 * 人力外包结算表id
	 * default value: null
	 */
	@ApiModelProperty(value = "人力外包结算表id")
//	@Column(name = "outsourcing_settlement_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "outsourcing_settlement_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberSettlementEntity outsourcingSettlement;

	/**
	 * 项目结算表id
	 * default value: null
	 */
	@ApiModelProperty(value = "项目结算表id")
//	@Column(name = "project_settlement_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_settlement_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmSettleAccountsEntity projectSettlement;

	/**
	 * 用户签约审核表id
	 * default value: null
	 */
	@ApiModelProperty(value = "用户签约审核表id")
//	@Column(name = "member_sign_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_sign_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MemberSignContractEntity memberSign;

	/**
	 * 产品提成金额
	 * default value: null
	 */
	@ApiModelProperty(value = "产品提成金额")
	@Column(name = "product_commission", nullable = true, length = 11)
	private Integer productCommission;

	/**
	 * 销售提成金额
	 * default value: null
	 */
	@ApiModelProperty(value = "销售提成金额")
	@Column(name = "sell_commission", nullable = true, length = 11)
	private Integer sellCommission;

	/**
	 * 推荐销售奖励金额
	 * default value: null
	 */
	@ApiModelProperty(value = "推荐销售奖励金额")
	@Column(name = "recommend_reward", nullable = true, length = 11)
	private Integer recommendReward;

	/**
	 * 推荐人奖励，关联文案协议id
	 * default value: null
	 */
	@ApiModelProperty(value = "推荐人奖励，关联文案协议id")
//	@Column(name = "referrer_reward", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referrer_reward")
	@NotFound(action = NotFoundAction.IGNORE)
	private OperationAgreementEntity referrerReward;

	/**
	 * 奖励类型：0推荐奖励，1销售奖励，只有奖励推荐人时才选择奖励类型
	 * default value: null
	 */
	@ApiModelProperty(value = "奖励类型：0推荐奖励，1销售奖励，只有奖励推荐人时才选择奖励类型")
	@Column(name = "reward_type", nullable = true, length = 20)
	private Integer rewardType;

	/**
	 * 发放方式：0平台划账，1线下打款
	 * default value: null
	 */
	@ApiModelProperty(value = "发放方式：0平台划账，1线下打款")
	@Column(name = "pay_type", nullable = true, length = 2)
	private Integer payType;

	/**
	 * 提成来源：0人力外包，1项目
	 * default value: null
	 */
	@ApiModelProperty(value = "提成来源：0人力外包，1项目")
	@Column(name = "source", nullable = true, length = 2)
	private Integer source;

	/**
	 * 审核材料，关联上传文件id
	 * default value: null
	 */
	@ApiModelProperty(value = "审核材料，关联上传文件id")
//	@Column(name = "check_file", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "check_file")
	@NotFound(action = NotFoundAction.IGNORE)
	private UploadFile checkFile;

	/**
	 * 支付审核：0未审核，1已审核
	 * default value: null
	 */
	@ApiModelProperty(value = "支付审核：0未审核，1已审核")
	@Column(name = "check_state", nullable = true, length = 2)
	private Integer checkState;

	/**
	 * 审核人
	 * default value: null
	 */
	@ApiModelProperty(value = "审核人")
	@Column(name = "check_user", nullable = true, length = 255)
	private String checkUser;

	/**
	 * 审核时间
	 * default value: null
	 */
	@ApiModelProperty(value = "审核时间")
	@Column(name = "check_time", nullable = true)
	private java.util.Date checkTime;
}
