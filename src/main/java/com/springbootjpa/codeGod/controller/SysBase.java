package com.springbootjpa.codeGod.controller;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.repository.SysUsersRolesRepository;
import com.springbootjpa.codeGod.service.baseService.BaseDataDirctionaryService;
import com.springbootjpa.codeGod.service.baseService.SysUsersService;
import com.springbootjpa.codeGod.utils.AesUtils;
import com.springbootjpa.codeGod.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SysBase {


    protected Gson g  = new Gson();

    protected AesUtils aesUtils = new AesUtils();

    @Autowired
    protected SysUsersService sysUsersService;

    @Resource
    protected RedisUtils redisUtils = new RedisUtils();

    @Autowired
    protected SysUsersRolesRepository sysUsersRolesRepository;

    @Autowired
    protected BaseDataDirctionaryService baseDataDirctionaryService;
}
