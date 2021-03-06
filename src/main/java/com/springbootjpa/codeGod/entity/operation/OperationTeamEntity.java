package com.springbootjpa.codeGod.entity.operation;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 服务团队列表
 */
@Data
@ApiModel(value = "服务团队列表")
@Entity
@Table(name = "operation_team")
public class OperationTeamEntity extends AbstractEntity implements Serializable {

	/**
	 * 团队名称
	 * default value: null
	 */
	@ApiModelProperty(value = "团队名称")
	@Column(name = "team_name", nullable = true, length = 255)
	private String teamName;

	/**
	 * 关联的地区id，所属城市
	 * default value: null
	 */
	@ApiModelProperty(value = "关联的地区id，所属城市")
//	@Column(name = "region_id", nullable = false, length = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private OperationRegionEntity region;

	/**
	 * 团队电话
	 * default value: null
	 */
	@ApiModelProperty(value = "团队电话")
	@Column(name = "team_phone", nullable = true, length = 255)
	private String teamPhone;

	/**
	 * 团队邮箱
	 * default value: null
	 */
	@ApiModelProperty(value = "团队邮箱")
	@Column(name = "team_email", nullable = true, length = 255)
	private String teamEmail;

	/**
	 * 团队地址
	 * default value: null
	 */
	@ApiModelProperty(value = "团队地址")
	@Column(name = "team_address", nullable = true, length = 255)
	private String teamAddress;

	/**
	 * 经度
	 * default value: null
	 */
	@ApiModelProperty(value = "经度")
	@Column(name = "longitude", nullable = true, length = 255)
	private String longitude;

	/**
	 * 纬度
	 * default value: null
	 */
	@ApiModelProperty(value = "纬度")
	@Column(name = "latitude", nullable = true, length = 255)
	private String latitude;

	/**
	 * 是否显示：0显示，1不显示
	 * default value: null
	 */
	@ApiModelProperty(value = "是否显示：0显示，1不显示")
	@Column(name = "state", nullable = true, length = 2)
	private Integer state;
	@Transient
	private String displayStr;

	/**
	 * 备注或口号
	 * default value: null
	 */
	@ApiModelProperty(value = "备注或口号")
	@Column(name = "remark", nullable = true)
	private String remark;

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
