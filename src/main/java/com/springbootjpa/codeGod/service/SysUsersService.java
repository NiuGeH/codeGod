package com.springbootjpa.codeGod.service;


import com.springbootjpa.codeGod.entity.SysUsersEntity;
import org.apache.shiro.subject.Subject;

public interface SysUsersService {
    Subject login(SysUsersEntity sysUsersEntity);
}
