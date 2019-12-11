package com.springbootjpa.codeGod.entity.humanResources;
import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 人力合同表
 */
@Data
@ApiModel(value = "人力合同表")
@Entity
@Table(name = "member_contract")
public class MemberContractEntity extends AbstractEntity implements Serializable {

    /**
     * 签约用户
     * default value: null
     */
    @ApiModelProperty(value = "签约用户")
//    @Column(name = "member_id", nullable = true, length = 20)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private MemberEntity memberId;

    /**
     * 需求Id
     * default value: null
     */
    @ApiModelProperty(value = "需求Id")
//    @Column(name = "employ_id", nullable = true, length = 20)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employ_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private MemberEmployEntity employId;

    /**
     * 结算单位
     * default value: null
     */
    @ApiModelProperty(value = "结算单位")
    @Column(name = "contract_unit", nullable = true, length = 20)
    private String contractUnit;

    /**
     * 合同周期 start
     * default value: null
     */
    @ApiModelProperty(value = "合同周期 start")
    @Column(name = "contract_cycle_start", nullable = true )
    private java.util.Date contractCycleStart;

    /**
     * 合同周期 end
     * default value: null
     */
    @ApiModelProperty(value = "合同周期 end")
    @Column(name = "contract_cycle_end", nullable = true )
    private java.util.Date contractCycleEnd;

    /**
     * 结算方式 0天 1月 2季
     * default value: null
     */
    @ApiModelProperty(value = "结算方式 0天 1月 2季")
    @Column(name = "contract_close_way", nullable = true, length = 10)
    private String contractCloseWay;

    /**
     * 项目地址
     * default value: null
     */
    @ApiModelProperty(value = "项目地址")
    @Column(name = "contract_address", nullable = true, length = 20)
    private String contractAddress;

    /**
     * 是否驻场 0 驻场 1 远程
     * default value: null
     */
    @ApiModelProperty(value = "是否驻场 0 驻场 1 远程")
    @Column(name = "contract_court", nullable = true, length = 20)
    private String contractCourt;

    /**
     * 合同 uploadfile ID
     * default value: null
     */
    @ApiModelProperty(value = "合同 uploadfile ID")
//    @Column(name = "contract_pact", nullable = true, length = 20)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_pact")
    @NotFound(action = NotFoundAction.IGNORE)
    private UploadFile contractPact;
}
