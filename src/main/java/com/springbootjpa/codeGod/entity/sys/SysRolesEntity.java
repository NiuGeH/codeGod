package com.springbootjpa.codeGod.entity.sys;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_roles") // 角色表
public class SysRolesEntity extends AbstractEntity implements Serializable {

//    id bigint auto_increment comment '角色编号',
//    role varchar(100) comment '角色名称',
//    description varchar(100) comment '角色描述',
//    pid bigint comment '父节点',
//    available bool default false comment '是否锁定'

    @Column(name = "role",length = 100)
    private String role;

    @Column (name = "description" , length = 100)
    private String description;

    @Column(name = "pid", length = 100)
    private Long pid;

    @Column(name = "available")
    private boolean available = false;

}
