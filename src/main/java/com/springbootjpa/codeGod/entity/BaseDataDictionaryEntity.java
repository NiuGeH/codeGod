package com.springbootjpa.codeGod.entity;
import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import javax.persistence.*;

/**
 * 基础表 数据字典
 */
@Data
@ApiModel(value = "基础表 数据字典")
@Entity
@Table(name = "base_data_dictionary")
public class BaseDataDictionaryEntity extends AbstractEntity implements Serializable {

	/**
	 * 对应的key 如 0,1,2
	 * default value: null
	 */
	@ApiModelProperty(value = "对应的key 如 0,1,2")
	@Column(name = "data_key", nullable = false, length = 20)
	private String dataKey;

	/**
	 * 对应的value 如 是，否 等
	 * default value: null
	 */
	@ApiModelProperty(value = "对应的value 如 是，否 等")
	@Column(name = "data_value", nullable = false, length = 20)
	private String dataValue;

	/**
	 * 对应的 表.字段名
	 * default value: null
	 */
	@ApiModelProperty(value = "对应的 表.字段名")
	@Column(name = "data_column_name", nullable = false, length = 20)
	private String dataColumnName;
}
