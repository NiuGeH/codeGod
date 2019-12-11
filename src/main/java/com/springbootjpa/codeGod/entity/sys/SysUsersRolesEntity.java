package com.springbootjpa.codeGod.entity.sys;

import com.springbootjpa.codeGod.entity.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "sys_users_roles") //用户角色
public class SysUsersRolesEntity extends AbstractEntity implements Serializable {
    //user_id bigint comment '用户编号',
    //  role_id bigint comment '角色编号',

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysUsersEntity userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SysRolesEntity roleId;



}
