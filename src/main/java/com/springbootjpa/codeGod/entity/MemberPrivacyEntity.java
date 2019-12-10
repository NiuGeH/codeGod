package com.springbootjpa.codeGod.entity;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import javax.persistence.*;

/**
 * 用户私密表
 */
@Data
@ApiModel(value = "用户私密表")
@Entity
@Table(name = "member_privacy")
public class MemberPrivacyEntity extends AbstractEntity implements Serializable {

    /**
     * 关联的用户
     * default value: null
     */
    @ApiModelProperty(value = "关联的用户")
    @Column(name = "member_id", nullable = false, length = 20)
    private Long memberId;

    /**
     * 真实姓名
     * default value: null
     */
    @ApiModelProperty(value = "真实姓名")
    @Column(name = "member_real_name", nullable = true, length = 10)
    private String memberRealName;

    /**
     * 邮箱
     * default value: null
     */
    @ApiModelProperty(value = "邮箱")
    @Column(name = "member_email", nullable = true, length = 20)
    private String memberEmail;

    /**
     * qq
     * default value: null
     */
    @ApiModelProperty(value = "qq")
    @Column(name = "member_qq", nullable = true, length = 15)
    private String memberQq;

    /**
     * 微信
     * default value: null
     */
    @ApiModelProperty(value = "微信")
    @Column(name = "member_wechat", nullable = true, length = 30)
    private String memberWechat;

    /**
     * 联系地址
     * default value: null
     */
    @ApiModelProperty(value = "联系地址")
    @Column(name = "member_contact_address", nullable = true, length = 50)
    private String memberContactAddress;

    /**
     * 远程报价
     * default value: null
     */
    @ApiModelProperty(value = "远程报价")
    @Column(name = "member_long_range_price", nullable = true, length = 30)
    private String memberLongRangePrice;

    /**
     * 驻场报价
     * default value: null
     */
    @ApiModelProperty(value = "驻场报价")
    @Column(name = "member_stationing_price", nullable = true, length = 30)
    private String memberStationingPrice;

    /**
     * 提现账户
     * default value: null
     */
    @ApiModelProperty(value = "提现账户")
    @Column(name = "member_cash_withdrawal", nullable = true, length = 30)
    private String memberCashWithdrawal;

    /**
     * 形象照
     * default value: null
     */
    @ApiModelProperty(value = "形象照")
    @Column(name = "member_photo_file", nullable = true, length = 20)
    private Long memberPhotoFile;

    /**
     * 头像
     * default value: null
     */
    @ApiModelProperty(value = "头像")
    @Column(name = "member_photo_head_portrait", nullable = true, length = 20)
    private Long memberPhotoHeadPortrait;

    /**
     * 身份证号
     * default value: null
     */
    @ApiModelProperty(value = "身份证号")
    @Column(name = "member_cardno", nullable = true, length = 30)
    private String memberCardno;

    /**
     * 个人资料 可上传多个 用,号隔开 就是upload_file 的主键
     * default value: null
     */
    @ApiModelProperty(value = "个人资料 可上传多个 用,号隔开 就是upload_file 的主键")
    @Column(name = "member_personal_data", nullable = true, length = 50)
    private String memberPersonalData;
}
