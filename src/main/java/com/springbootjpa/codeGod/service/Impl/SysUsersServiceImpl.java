package com.springbootjpa.codeGod.service.Impl;

import com.springbootjpa.codeGod.entity.sys.SysUsersEntity;
import com.springbootjpa.codeGod.service.SysUsersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class SysUsersServiceImpl implements SysUsersService {


    @Override
    public Subject login(SysUsersEntity sysUsersEntity) {
        UsernamePasswordToken token = new UsernamePasswordToken(sysUsersEntity.getUsername(),sysUsersEntity.getPassword());
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }
        subject.login(token);
        return subject;
    }
}
