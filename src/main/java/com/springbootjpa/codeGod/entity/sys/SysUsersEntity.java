package com.springbootjpa.codeGod.entity.sys;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_users")// 用户表
public class SysUsersEntity extends AbstractEntity implements Serializable {

//        username varchar(100) comment '用户名',
//        password varchar(100) comment '密码',
//        salt varchar(100) comment '盐值',
//        role_id bigint(20) comment '角色列表',
//        locked bool default false comment '是否锁定',

    @ApiModelProperty(value = "用户名")
    @Column(name = "username", length = 100)
    private String username;

    @ApiModelProperty(value = "密码")
    @Column(name = "password", length = 100)
    private String password;

    @ApiModelProperty(value = "不需要填写")
    @Column(name = "salt", length = 100)
    private String salt;

    @ApiModelProperty(value = "是否锁定")
    @Column(name = "locked")
    private Boolean locked;

    @ApiModelProperty(value = "旧密码")
    @Transient
    private String newPwd;
}
