package com.springbootjpa.codeGod.service.humanResourcesService;

import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberSignContractService {

    Page<MemberSignContractEntity> doPageByValidationCodeAndSiginEnd(Pageable pageable,Integer validationCode,Integer siginEnd);

}
