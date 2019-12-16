package com.springbootjpa.codeGod.entity.finance;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.projectmanager.PmSalaryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 项目付款审核列表
 */
@Data
@ApiModel(value = "项目付款审核列表")
@Entity
@Table(name = "finance_project_payment")
public class FinanceProjectPaymentEntity extends AbstractEntity implements Serializable {

	/**
	 * 团队成员工资表id
	 * default value: null
	 */
	@ApiModelProperty(value = "团队成员工资表id")
//	@Column(name = "salary_id", nullable = true, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salary_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PmSalaryEntity salary;

	/**
	 * 审核材料，关联上传文件id，工作情况表
	 * default value: null
	 */
	@ApiModelProperty(value = "审核材料，关联上传文件id，工作情况表")
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
	private String checkTime;
}
