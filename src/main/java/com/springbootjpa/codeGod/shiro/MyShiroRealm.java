package com.springbootjpa.codeGod.shiro;


import com.springbootjpa.codeGod.entity.sys.SysRolesRermissionsEntity;
import com.springbootjpa.codeGod.entity.sys.SysUsersEntity;
import com.springbootjpa.codeGod.entity.sys.SysUsersRolesEntity;
import com.springbootjpa.codeGod.repository.SysRolesRermissionsRepository;
import com.springbootjpa.codeGod.repository.SysUsersRepository;
import com.springbootjpa.codeGod.repository.SysUsersRolesRepository;
import com.springbootjpa.codeGod.utils.RedisUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUsersRepository sysUsersRepository;

    @Autowired
    private SysUsersRolesRepository sysUsersRolesRepository;

    @Autowired
    private SysRolesRermissionsRepository sysRolesRermissionsRepository;

    @Resource
    private RedisUtils redisUtils = new RedisUtils();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUsersEntity userEntity = (SysUsersEntity) principals.getPrimaryPrincipal();

        if(ObjectUtils.isEmpty(userEntity.getId())){
            redisUtils.set("loginUser","");
        }else{
            // 用户拥有的角色
            List<SysUsersRolesEntity> byUserId = sysUsersRolesRepository.findByUserId(userEntity);
//            redisUtils.set("loginUser"+userEntity.getId())
            for (int i = 0; i < byUserId.size(); i++) {
                SysUsersRolesEntity sysUsersRolesEntity = byUserId.get(i);
                // 用户拥有的权限
                List<SysRolesRermissionsEntity> byRoleId = sysRolesRermissionsRepository.findByRoleId(sysUsersRolesEntity.getRoleId());
                for (int j = 0; j < byRoleId.size(); j++) {
                    SysRolesRermissionsEntity sysRolesRermissionsEntity = byRoleId.get(j);
                    authorizationInfo.addRole(sysRolesRermissionsEntity.getPermissionId().getPermission());
                    System.out.println(sysRolesRermissionsEntity.getPermissionId().getPermission() +'s');
                }
            }
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();

        SysUsersEntity user = sysUsersRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                null,
                getName()
        );
        return authenticationInfo;
    }


}