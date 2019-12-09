package com.springbootjpa.codeGod.entity;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_roles_permissions")
public class SysRolesRermissionsEntity extends AbstractEntity implements Serializable {
    // role_id bigint comment '角色编号',
    //  permission_id bigint comment '权限编号',
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysRolesEntity roleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysPermissionsEntity permissionId;
}
