package com.springbootjpa.codeGod.entity;

import lombok.Data;

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


    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "salt", length = 100)
    private String salt;

    @Column(name = "locked")
    private Boolean locked;
}
