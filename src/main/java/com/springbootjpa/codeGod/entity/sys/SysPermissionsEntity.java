package com.springbootjpa.codeGod.entity.sys;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "sys_permissions") // 权限表
public class SysPermissionsEntity extends AbstractEntity implements Serializable {
    //permission varchar(100) comment '权限编号',
    //  description varchar(100) comment '权限描述',
    //  available bool default false comment '是否锁定'

    @Column(name = "permission", length = 100)
    private String permission;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "available")
    private boolean available = false ;

}
