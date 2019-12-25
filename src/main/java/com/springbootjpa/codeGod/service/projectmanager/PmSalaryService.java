package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmSalaryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PmSalaryService {

    boolean saveSalary(PmSalaryEntity pmSalaryEntity);

    boolean deleteSalary(Long id);

    Page<PmSalaryEntity> findOne(Pageable pageable,PmSalaryEntity pmSalaryEntity);

    PmSalaryEntity findOne(Long id);
}
