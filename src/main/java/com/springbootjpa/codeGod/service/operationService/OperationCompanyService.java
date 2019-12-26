package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationCompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 10:05
 */
public interface OperationCompanyService {

    /**
     * 添加合作企业
     * @param companyName 企业名称
     * @param companyAddress 企业地址
     * @param companyPhone 企业电话
     * @param accountName 开户名称
     * @param bank 开户行
     * @param accountNumber 收款账户
     * @param companyDisplay 是否显示：0是，否
     * @return
     */
    OperationCompanyEntity addCompany(String companyName, String companyAddress, String companyPhone, String accountName, String bank, String accountNumber, Integer companyDisplay);

    /**
     * 修改合作企业信息
     * @param id 被修改的合作企业id
     * @param companyName 企业名称
     * @param companyAddress 企业地址
     * @param companyPhone 企业电话
     * @param accountName 开户名称
     * @param bank 开户行
     * @param accountNumber 收款账户
     * @param companyDisplay 是否显示：0是，否
     * @return
     */
    OperationCompanyEntity updateCompany(Long id, String companyName, String companyAddress, String companyPhone, String accountName, String bank, String accountNumber, Integer companyDisplay);

    /**
     * 查询全部合作企业分页
     * @param pageable
     * @return
     */
    Page<OperationCompanyEntity> findAll(Pageable pageable);
}
