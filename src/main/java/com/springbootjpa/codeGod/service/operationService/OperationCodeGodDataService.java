package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationCodeGodDataEntity;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/24 17:03
 */
public interface OperationCodeGodDataService {

    /**
     * 设置码神资料
     * @param amountProject 已完成项目数量
     * @param grossAccount 累计合同金额
     * @param amountSignContract 签约码神数量
     * @param amountTeam 全国服务团队数量
     * @return
     */
    OperationCodeGodDataEntity setCodeGodData(Long amountProject, Long grossAccount, Long amountSignContract,Long amountTeam);

    /**
     * 查询码神资料
     * @return
     */
    OperationCodeGodDataEntity findAll();

}
