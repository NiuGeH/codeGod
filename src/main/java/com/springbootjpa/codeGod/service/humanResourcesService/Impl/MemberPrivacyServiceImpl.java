package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.service.humanResourcesService.MemberPrivacyService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberPrivacyServiceImpl implements MemberPrivacyService {
}
