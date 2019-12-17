package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.repository.HumanResources.MemberPrivacyentityRepository;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberPrivacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberPrivacyServiceImpl implements MemberPrivacyService {
}
