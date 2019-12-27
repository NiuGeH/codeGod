package com.springbootjpa.codeGod.service.humanResourcesService;

import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberWageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberWageService {

    /**
     * 造工资
     * @param memberWageEntity 实体
     * @return MemberWageEntity
     */
    MemberWageEntity doMakeSalary(MemberWageEntity memberWageEntity);
    /**
     * 根据验证码和签约结果分页
     * @param pageable 分页实体
     * @param contractId 合同Id
     * @return Page<MemberSignContractEntity>
     */
    Page<MemberWageEntity> doPageByContractId(Pageable pageable, Long contractId);

    /**
     * 编辑 发工资
     * @param wageEntity 发工资实体
     * @return MemberWageEntity
     */
    MemberWageEntity doEditWage(MemberWageEntity wageEntity);
}
