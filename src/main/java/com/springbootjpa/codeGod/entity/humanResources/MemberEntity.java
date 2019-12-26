package com.springbootjpa.codeGod.entity.humanResources;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.operation.OperationCompanyEntity;
import com.springbootjpa.codeGod.entity.operation.OperationMedalEntity;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * 用户基础表
 */
//@Data
@ApiModel(value = "用户基础表")
@Entity
@Table(name = "member")
public class MemberEntity extends AbstractEntity implements Serializable {

    /**
     * 昵称
     * default value: null
     */
    @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name", nullable = true, length = 20)
    private String nickName;

    /**
     * 创建时间
     * default value: null
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time", nullable = true)
    private java.util.Date createTime;

    /**
     * 修改时间
     * default value: null
     */
    @ApiModelProperty(value = "修改时间")
    @Column(name = "update_time", nullable = true)
    private java.util.Date updateTime;

    /**
     * 所在城市
     * default value: null
     */
    @ApiModelProperty(value = "所在城市")
//	@Column(name = "memebr_city", nullable = true, length = 30)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memebr_city")
    @NotFound(action = NotFoundAction.IGNORE)
    private OperationRegionEntity memebrCityEntity;

    @Transient
    private String memebrCity;

    /**
     * 关键字 用,分割
     * default value: null
     */
    @ApiModelProperty(value = "关键字 用,分割")
    @Column(name = "member_word", nullable = true, length = 50)
    private String memberWord;

    /**
     * 所属公司
     * default value: null
     */
    @ApiModelProperty(value = "所属公司")
//	@Column(name = "member_company", nullable = true, length = 30)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_company")
    @NotFound(action = NotFoundAction.IGNORE)
    private OperationCompanyEntity operationCompanyEntity;

    @Transient
    private String memberCompany;
    /**
     * 个人介绍
     * default value: null
     */
    @ApiModelProperty(value = "个人介绍")
    @Column(name = "member_introduce", nullable = true, length = 300)
    private String memberIntroduce;

    /**
     * 用户类型 0真实用户 1自建用户
     * default value: null
     */
    @ApiModelProperty(value = "用户类型 0真实用户 1自建用户")
    @Column(name = "member_type", nullable = true, length = 11)
    private Integer memberType;

    @Transient
    private String memberTypeData;

    public String getMemberTypeData() {
        if (memberType == HumanRecourcesStatus.MEMBER_MEMBERTYPE_ZJYH.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBERTYPE_ZJYH.getName();
        } else if (memberType == HumanRecourcesStatus.MEMBER_MEMBERTYPE_ZSYH.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBERTYPE_ZSYH.getName();
        }
        return memberTypeData;
    }

    /**
     * 签约岗位 用,号分割
     * default value: null
     */
    @ApiModelProperty(value = "签约岗位 用,号分割")
    @Column(name = "member_signing_post", nullable = true, length = 30)
    private String memberSigningPost;

    @Transient
    private String memberSigningPostData;

    public String getMemberSigningPostData() {
        String[] split = memberSigningPost.split(",");
        StringBuilder sb = new StringBuilder();
        if (split.length > 1) {
            for (String s : split) {
                sb.append(getMemberPostDataPrv(Integer.valueOf(s)));
                sb.append("/");
            }
            return sb.substring(0, sb.length() - 1);
        } else {
            return getMemberPostDataPrv(Integer.valueOf(split[0]));
        }
    }

    private String getMemberPostDataPrv(Integer msp) {
        if (HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_KF.getIndex() == msp) {
            return HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_KF.getName();
        } else if (HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_CS.getIndex() == msp) {
            return HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_CS.getName();
        } else if (HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_UI.getIndex() == msp) {
            return HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_UI.getName();
        } else if (HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_CP.getIndex() == msp) {
            return HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_CP.getName();
        } else if (HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_XS_KH.getIndex() == msp) {
            return HumanRecourcesStatus.MEMBER_MEMBERSIGNINGPOST_XS_KH.getName();
        } else {
            return "";
        }
    }

    /**
     * 签约方式 0全职 1兼职
     * default value: null
     */
    @ApiModelProperty(value = "签约方式 0全职 1兼职")
    @Column(name = "member_signing_mode", nullable = true, length = 3)
    private Integer memberSigningMode;
    @Transient
    private String memberSigningModeString;

    public String getMemberSigningModeString() {
        if (HumanRecourcesStatus.MEMBER_MEMBERSIGNINGMODE_QZ.getIndex() == memberSigningMode) {
            return HumanRecourcesStatus.MEMBER_MEMBERSIGNINGMODE_QZ.getName();
        } else if (HumanRecourcesStatus.MEMBER_MEMBERSIGNINGMODE_JZ.getIndex() == memberSigningMode) {
            return HumanRecourcesStatus.MEMBER_MEMBERSIGNINGMODE_JZ.getName();
        } else {
            return "";
        }
    }

    /**
     * 勋章 ,号分割
     * default value: null
     */
    @ApiModelProperty(value = "勋章 ,号分割")
    @Column(name = "member_medal", nullable = true, length = 40)
    private String memberMedal;

    @ApiModelProperty(value = "勋章 实体类集合")
    @Transient
    private List<OperationMedalEntity> memberMedalList;

    /**
     * 是否驻场
     * default value: null
     */
    @ApiModelProperty(value = "是否驻场")
    @Column(name = "member_stationing", nullable = true, length = 30)
    private String memberStationing;

    @Transient
    private String memberStationingData;

    public String getMemberStationingData() {
        if (Integer.valueOf(memberStationing) == HumanRecourcesStatus.MEMBER_MEMBERSTATIONING_KZC.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBERSTATIONING_KZC.getName();
        } else if (HumanRecourcesStatus.MEMBER_MEMBERSTATIONING_BKZC.getIndex() == Integer.valueOf(memberStationing)) {
            return HumanRecourcesStatus.MEMBER_MEMBERSTATIONING_BKZC.getName();
        } else if (HumanRecourcesStatus.MEMBER_MEMBERSTATIONING_QGCC.getIndex() == Integer.valueOf(memberStationing)) {
            return HumanRecourcesStatus.MEMBER_MEMBERSTATIONING_QGCC.getName();
        }
        return memberStationingData;
    }

    /**
     * 当前状态  可接单 工作中
     * default value: null
     */
    @ApiModelProperty(value = "当前状态  可接单 工作中")
    @Column(name = "member_status", nullable = true, length = 10)
    private Integer memberStatus;

    @Transient
    private String memberStatusData;

    public String getMemberStatusData() {
        if (!ObjectUtils.isEmpty(memberStatus)) {
            if (this.memberStatus == HumanRecourcesStatus.MEMBER_MEMBERSTATUS_KJD.getIndex()) {
                return HumanRecourcesStatus.MEMBER_MEMBERSTATUS_KJD.getName();
            } else if (this.memberStatus == HumanRecourcesStatus.MEMBER_MEMBERSTATUS_GZZ.getIndex()) {
                return HumanRecourcesStatus.MEMBER_MEMBERSTATUS_GZZ.getName();
            }
        }
        return "";
    }

    /**
     * 是否公开显示 0显示  1不显示
     * default value: null
     */
    @ApiModelProperty(value = "是否公开显示 0显示  1不显示")
    @Column(name = "member_display", nullable = true, length = 2)
    private Integer memberDisplay;

    @Transient
    private String memberDisplayData;

    public String getMemberDisplayData() {
        if (memberDisplay == HumanRecourcesStatus.MEMBER_MEMBERDISPLAY_XS.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBERDISPLAY_XS.getName();
        } else if (memberDisplay == HumanRecourcesStatus.MEMBER_MEMBERDISPLAY_BXS.getIndex()) {
            return HumanRecourcesStatus.MEMBER_MEMBERDISPLAY_BXS.getName();
        }
        return memberDisplayData;
    }

    /**
     * 签约协议 可上传多个 ,号分割
     * default value: null
     */
    @ApiModelProperty(value = "签约协议 可上传多个 ,号分割")
    @Column(name = "member_signing_agreement", nullable = true, length = 50)
    private String memberSigningAgreement;

    @Transient
    private List<UploadFile> memberSigningAgreementList;

    /**
     * 签约状态 0 拒绝 1 已签约
     * default value: null
     */
    @ApiModelProperty(value = "签约结果 0 未申请 1 等待审核 2 已签约 3 已拒绝")
    @Column(name = "member_signing_status", nullable = true, length = 4)
    private Integer memberSigningStatus;

    @Transient
    private String memberSigningStatusData;

    public String getMemberSigningStatusData() {

//		return "memberSignStatusData";
        if (!ObjectUtils.isEmpty(getMemberSigningStatus())) {
//            return getMemberSigningStatus().toString();
            if (getMemberSigningStatus() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_WSQ.getIndex()) {
                return HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_WSQ.getName();
            } else if (getMemberSigningStatus() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_DDSH.getIndex()) {
                return HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_DDSH.getName();
            } else if (getMemberSigningStatus() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YQY.getIndex()) {
                return HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YQY.getName();
            } else if (getMemberSigningStatus() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YJJ.getIndex()) {
                return HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YJJ.getName();
            } else {
                return "";
            }
        }
        return "";
    }

    public void setMemberSignStatusData(String memberSignStatusData) {
        this.memberSigningStatusData = memberSignStatusData;
    }

    /**
     * 所属团队
     * default value: null
     */
    @ApiModelProperty(value = "所属团队")
    @Column(name = "member_team", nullable = true, length = 20)
    private Long memberTeam;

    /**
     * 工作态度
     * default value: null
     */
    @ApiModelProperty(value = "工作态度")
    @Column(name = "member_attitude", nullable = true, length = 10)
    private String memberAttitude;

    /**
     * 榜评
     * default value: null
     */
    @ApiModelProperty(value = "榜评")
    @Column(name = "member_review", nullable = true, length = 50)
    private String memberReview;

    /**
     * 技能管理
     * default value: null
     */
    @ApiModelProperty(value = "技能管理")
    @Column(name = "member_skill_management", nullable = true, length = 100)
    private String memberSkillManagement;

    /**
     * 远程开发报价
     */
    @ApiModelProperty(value = "远程开发报价")
    @Column(name = "member_long_range", nullable = true, length = 40)
    private String memberLongRange;

    /**
     * 驻场开发报价
     */
    @ApiModelProperty(value = "驻场开发报价")
    @Column(name = "member_on_site_development", nullable = true, length = 40)
    private String memberOnSiteDevelopment;

    /**
     * 私密信息表
     */
    @ApiModelProperty(value = "私密信息表")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_pricacy")
    @NotFound(action = NotFoundAction.IGNORE)
    private MemberPrivacyEntity memberPricacy;

    /**
     * 删除 0 正常 1 逻辑删除
     */
    @Column(name = "member_state")
    private Integer memberState = 0;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public OperationRegionEntity getMemebrCityEntity() {
        return memebrCityEntity;
    }

    public void setMemebrCityEntity(OperationRegionEntity memebrCityEntity) {
        this.memebrCityEntity = memebrCityEntity;
    }

    public String getMemebrCity() {
        return memebrCity;
    }

    public void setMemebrCity(String memebrCity) {
        this.memebrCity = memebrCity;
    }

    public String getMemberWord() {
        return memberWord;
    }

    public void setMemberWord(String memberWord) {
        this.memberWord = memberWord;
    }

    public OperationCompanyEntity getOperationCompanyEntity() {
        return operationCompanyEntity;
    }

    public void setOperationCompanyEntity(OperationCompanyEntity operationCompanyEntity) {
        this.operationCompanyEntity = operationCompanyEntity;
    }

    public String getMemberCompany() {
        return memberCompany;
    }

    public void setMemberCompany(String memberCompany) {
        this.memberCompany = memberCompany;
    }

    public String getMemberIntroduce() {
        return memberIntroduce;
    }

    public void setMemberIntroduce(String memberIntroduce) {
        this.memberIntroduce = memberIntroduce;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public void setMemberTypeData(String memberTypeData) {
        this.memberTypeData = memberTypeData;
    }

    public String getMemberSigningPost() {
        return memberSigningPost;
    }

    public void setMemberSigningPost(String memberSigningPost) {
        this.memberSigningPost = memberSigningPost;
    }

    public void setMemberSigningPostData(String memberSigningPostData) {
        this.memberSigningPostData = memberSigningPostData;
    }

    public Integer getMemberSigningMode() {
        return memberSigningMode;
    }

    public void setMemberSigningMode(Integer memberSigningMode) {
        this.memberSigningMode = memberSigningMode;
    }

    public void setMemberSigningModeString(String memberSigningModeString) {
        this.memberSigningModeString = memberSigningModeString;
    }

    public String getMemberMedal() {
        return memberMedal;
    }

    public void setMemberMedal(String memberMedal) {
        this.memberMedal = memberMedal;
    }

    public List<OperationMedalEntity> getMemberMedalList() {
        return memberMedalList;
    }

    public void setMemberMedalList(List<OperationMedalEntity> memberMedalList) {
        this.memberMedalList = memberMedalList;
    }

    public String getMemberStationing() {
        return memberStationing;
    }

    public void setMemberStationing(String memberStationing) {
        this.memberStationing = memberStationing;
    }

    public void setMemberStationingData(String memberStationingData) {
        this.memberStationingData = memberStationingData;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Integer getMemberDisplay() {
        return memberDisplay;
    }

    public void setMemberDisplay(Integer memberDisplay) {
        this.memberDisplay = memberDisplay;
    }

    public void setMemberDisplayData(String memberDisplayData) {
        this.memberDisplayData = memberDisplayData;
    }

    public String getMemberSigningAgreement() {
        return memberSigningAgreement;
    }

    public void setMemberSigningAgreement(String memberSigningAgreement) {
        this.memberSigningAgreement = memberSigningAgreement;
    }

    public List<UploadFile> getMemberSigningAgreementList() {
        return memberSigningAgreementList;
    }

    public void setMemberSigningAgreementList(List<UploadFile> memberSigningAgreementList) {
        this.memberSigningAgreementList = memberSigningAgreementList;
    }

    public Integer getMemberSigningStatus() {
        return memberSigningStatus;
    }

    public void setMemberSigningStatus(Integer memberSigningStatus) {
        this.memberSigningStatus = memberSigningStatus;
    }

    public Long getMemberTeam() {
        return memberTeam;
    }

    public void setMemberTeam(Long memberTeam) {
        this.memberTeam = memberTeam;
    }

    public String getMemberAttitude() {
        return memberAttitude;
    }

    public void setMemberAttitude(String memberAttitude) {
        this.memberAttitude = memberAttitude;
    }

    public String getMemberReview() {
        return memberReview;
    }

    public void setMemberReview(String memberReview) {
        this.memberReview = memberReview;
    }

    public String getMemberSkillManagement() {
        return memberSkillManagement;
    }

    public void setMemberSkillManagement(String memberSkillManagement) {
        this.memberSkillManagement = memberSkillManagement;
    }

    public String getMemberLongRange() {
        return memberLongRange;
    }

    public void setMemberLongRange(String memberLongRange) {
        this.memberLongRange = memberLongRange;
    }

    public String getMemberOnSiteDevelopment() {
        return memberOnSiteDevelopment;
    }

    public void setMemberOnSiteDevelopment(String memberOnSiteDevelopment) {
        this.memberOnSiteDevelopment = memberOnSiteDevelopment;
    }

    public MemberPrivacyEntity getMemberPricacy() {
        return memberPricacy;
    }

    public void setMemberPricacy(MemberPrivacyEntity memberPricacy) {
        this.memberPricacy = memberPricacy;
    }

    public Integer getMemberState() {
        return memberState;
    }

    public void setMemberState(Integer memberState) {
        this.memberState = memberState;
    }
}
