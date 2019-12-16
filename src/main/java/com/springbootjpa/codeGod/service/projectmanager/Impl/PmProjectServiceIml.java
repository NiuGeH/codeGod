package com.springbootjpa.codeGod.service.projectmanager.Impl;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class PmProjectServiceIml {



}
