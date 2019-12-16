package com.springbootjpa.codeGod.service.projectmanager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PmApplicationService {

    Page<PmApplicationService> doPageByDemandId(Pageable pageable, Long demandId);

}
