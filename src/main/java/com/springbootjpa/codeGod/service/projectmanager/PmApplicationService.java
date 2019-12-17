package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PmApplicationService {

    Page<PmApplicationEntity> doPage(Pageable pageable, Long demandId);

}
