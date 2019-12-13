package com.springbootjpa.codeGod.service.baseService;


import com.springbootjpa.codeGod.entity.sys.SysUsersEntity;
import org.apache.shiro.subject.Subject;

public interface SysUsersService {
    Subject login(SysUsersEntity sysUsersEntity);

    void updatePwd(SysUsersEntity sysUsersEntity);
}
