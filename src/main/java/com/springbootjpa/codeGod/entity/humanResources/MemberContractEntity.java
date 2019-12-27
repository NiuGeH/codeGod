package com.springbootjpa.codeGod.entity.humanResources;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.ObjectUtils;

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date contractCycleStart;

    /**
     * 合同周期 end
     * default value: null
     */
    @ApiModelProperty(value = "合同周期 end")
    @Column(name = "contract_cycle_end", nullable = true )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date contractCycleEnd;

    /**
     * 结算方式 0天 1月 2季
     * default value: null
     */
    @ApiModelProperty(value = "结算方式 0天 1月 2季")
    @Column(name = "contract_close_way", nullable = true, length = 10)
    private Integer contractCloseWay;

    @Transient
    private String contractCloseWayData;

    public String getContractCloseWayData() {
        if(!ObjectUtils.isEmpty(getContractCloseWay())){
            if(getContractCloseWay() == HumanRecourcesStatus.MEMBER_CONTRACT_CLOSE_WAY_TIAN.getIndex()){
                return HumanRecourcesStatus.MEMBER_CONTRACT_CLOSE_WAY_TIAN.getName();
            }else if(getContractCloseWay() == HumanRecourcesStatus.MEMBER_CONTRACT_CLOSE_WAY_YUE.getIndex()){
                return HumanRecourcesStatus.MEMBER_CONTRACT_CLOSE_WAY_YUE.getName();
            }else if(getContractCloseWay() == HumanRecourcesStatus.MEMBER_CONTRACT_CLOSE_WAY_JI.getIndex()){
                return HumanRecourcesStatus.MEMBER_CONTRACT_CLOSE_WAY_JI.getName();
            }
        }
        return contractCloseWayData;
    }

    /**
     * 项目地址
     * default value: null
     */
    @ApiModelProperty(value = "项目地址")
//    @Column(name = "contract_address", nullable = true, length = 20)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_address")
    @NotFound(action = NotFoundAction.IGNORE)
    private OperationRegionEntity contractAddress;

    /**
     * 是否驻场 0 驻场 1 远程
     * default value: null
     */
    @ApiModelProperty(value = "是否驻场 0 驻场 1 远程")
    @Column(name = "contract_court", nullable = true, length = 20)
    private Integer contractCourt;

    @Transient
    private String contractCourtData;

    public String getContractCourtData() {
        if(!ObjectUtils.isEmpty(getContractCourt())){
            if(getContractCourt() == HumanRecourcesStatus.MEMBER_CONTRACT_COURT_ZC.getIndex()){
                return HumanRecourcesStatus.MEMBER_CONTRACT_COURT_ZC.getName();
            }else if(getContractCourt() == HumanRecourcesStatus.MEMBER_CONTRACT_COURT_YC.getIndex()){
                return HumanRecourcesStatus.MEMBER_CONTRACT_COURT_YC.getName();

            }
        }
        return contractCourtData;
    }

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

    /**
     * 结算人数
     */
    @ApiModelProperty(value = "结算人数")
    @Column(name = "contract_number",nullable = true,length = 20)
    private Integer contractNumber;


    /**
     * 签约状态 0已签约 1已终止
     */
    @Column(name = "contract_status",nullable = true)
    private Integer contractStatus;

    @Transient
    private String contractStatusData;

    public String getContractStatusData() {
        if(!ObjectUtils.isEmpty(getContractStatus())){
            if(getContractStatus() == HumanRecourcesStatus.MEMBER_CONTRACT_STATUS_YQY.getIndex()){
                return HumanRecourcesStatus.MEMBER_CONTRACT_STATUS_YQY.getName();
            }else if(getContractStatus() == HumanRecourcesStatus.MEMBER_CONTRACT_STATUS_YZZ.getIndex()){
                return HumanRecourcesStatus.MEMBER_CONTRACT_STATUS_YZZ.getName();
            }
        }
        return contractStatusData;
    }

    @Override
    public String toString() {
        return "MemberContractEntity{" +
                "memberId=" + memberId +
                ", employId=" + employId +
                ", contractUnit='" + contractUnit + '\'' +
                ", contractCycleStart=" + contractCycleStart +
                ", contractCycleEnd=" + contractCycleEnd +
                ", contractCloseWay=" + contractCloseWay +
                ", contractCloseWayData='" + contractCloseWayData + '\'' +
                ", contractAddress=" + contractAddress +
                ", contractCourt=" + contractCourt +
                ", contractPact=" + contractPact +
                ", contractNumber=" + contractNumber +
                ", contractStatus=" + contractStatus +
                ", contractStatusData='" + contractStatusData + '\'' +
                ", id=" + id +
                '}';
    }
}
