package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmRecruitmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PmRecruitmentService {


    Page<PmRecruitmentEntity> dopage(Pageable pageable);


    boolean saveRecruitment(PmRecruitmentEntity pmRecruitmentEntity);

    PmRecruitmentEntity findOne(Long id);
}
