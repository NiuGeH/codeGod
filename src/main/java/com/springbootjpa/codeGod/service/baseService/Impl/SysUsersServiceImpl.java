package com.springbootjpa.codeGod.service.baseService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.sys.SysUsersEntity;
import com.springbootjpa.codeGod.repository.SysUsersRepository;
import com.springbootjpa.codeGod.service.baseService.SysUsersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class SysUsersServiceImpl implements SysUsersService {


    @Autowired
    private SysUsersRepository sysUsersRepository;

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

    @Override
    public void updatePwd(SysUsersEntity sysUsersEntity) {
        Optional<SysUsersEntity> byId = sysUsersRepository.findById(sysUsersEntity.getId());
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("该管理员不存在",this.getClass());
        }else {
            SysUsersEntity jpaEnt = byId.orElse(null);
            assert jpaEnt != null;
            if(sysUsersEntity.getPassword().equals(jpaEnt.getPassword())){
                jpaEnt.setPassword(sysUsersEntity.getNewPwd());
                sysUsersRepository.save(jpaEnt);

            }else{
                throw new CodeGodRunTimExcetion("原密码不正确",this.getClass());
            }
        }
//        return false;
    }


}
