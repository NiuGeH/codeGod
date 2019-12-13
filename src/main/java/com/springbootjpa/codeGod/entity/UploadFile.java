package com.springbootjpa.codeGod.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "upload_file")
public class UploadFile extends AbstractEntity {

    /**
     * 文件显示名称
     */
    @Column(name = "display_name")
    private String displayName;

    /**
     * 文件路径，包含完整的相对路径，前面带/，后面带后缀名
     */
    @ApiModelProperty(value = "文件路径")
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 改文件关联的业务ID，如订单号等
     */
    @Column(name = "related_key")
    private String relatedKey;

    /**
     * 操作人
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 操作时间
     */
    @Column(name = "create_time", length = 20)
    private String createTime;

    /**
     * 更新人
     */
    @Column(name = "update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @Column(name = "update_time", length = 20)
    private String updateTime;

    /**
     * 文件类型：1图片 2普通文件 11身份证正面  12身份证背面
     */
    @Column(name = "file_type")
    private Integer fileType;

    /**
     * 文件用途类型，选项为：1/null、普通用途；4、评估报告的专用附件，因为默认一个订单就对应一种附件;5、垫付费用凭据；6、报销凭证；7、自检仪容
     */
    @Column(name = "use_type")
    private Integer useType;

    /**
     * 文件来源，1:上传(属于自己的文件) 	2:引用其它订单的附件(非属于自己的文件)，默认为1
     */
    @Column(name = "file_from")
    private Integer fileFrom;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getRelatedKey() {
        return relatedKey;
    }

    public void setRelatedKey(String relatedKey) {
        this.relatedKey = relatedKey;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Integer getFileFrom() {
        return fileFrom;
    }

    public void setFileFrom(Integer fileFrom) {
        this.fileFrom = fileFrom;
    }

}
