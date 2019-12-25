package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.operation.OperationCompanyEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationCompanyRepository;
import com.springbootjpa.codeGod.service.operationService.OperationCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Calendar;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 10:13
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationCompanyServiceImpl implements OperationCompanyService {

    @Autowired
    private OperationCompanyRepository operationCompanyRepository;

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 添加合作企业
     * @param companyName 企业名称
     * @param companyAddress 企业地址
     * @param teamPhone 企业电话
     * @param accountName 开户名称
     * @param bank 开户行
     * @param accountNumber 收款账户
     * @param companyDisplay 是否显示：0是，否
     * @return
     */
    @Override
    public OperationCompanyEntity addCompany(String companyName, String companyAddress, String teamPhone, String accountName, String bank, String accountNumber, Integer companyDisplay) {
        //参数验证
        if(ObjectUtils.isEmpty(companyName)) throw new CodeGodRunTimExcetion("企业名称不能为空", this.getClass());

        //判断企业名称是否已存在
        OperationCompanyEntity ce = operationCompanyRepository.findAllByCompanyName(companyName);
        if(!ObjectUtils.isEmpty(ce)) throw new CodeGodRunTimExcetion("该企业名称已存在", this.getClass());

        //添加
        OperationCompanyEntity companyEntity = new OperationCompanyEntity();
        //企业名称
        companyEntity.setCompanyName(companyName);
        //企业地址
        companyEntity.setCompanyAddress(companyAddress);
        //企业电话
        companyEntity.setTeamPhone(teamPhone);
        //开户名称
        companyEntity.setAccountName(accountName);
        //开户行
        companyEntity.setBank(bank);
        //收款账户
        companyEntity.setAccountNumber(accountNumber);
        //是否显示
        if(ObjectUtils.isEmpty(companyDisplay)){
            companyEntity.setCompanyDisplay(OperationEnum.OPERATION_DISPLAY_YES.getIndex());
        }else {
            companyEntity.setCompanyDisplay(companyDisplay);
        }
        //创建时间
        companyEntity.setCreateTime(Calendar.getInstance().getTime());

        //保存
        operationCompanyRepository.save(companyEntity);

        return companyEntity;
    }

    /**
     * 修改合作企业信息
     * @param id 被修改的合作企业id
     * @param companyName 企业名称
     * @param companyAddress 企业地址
     * @param teamPhone 企业电话
     * @param accountName 开户名称
     * @param bank 开户行
     * @param accountNumber 收款账户
     * @param companyDisplay 是否显示：0是，否
     * @return
     */
    @Override
    public OperationCompanyEntity updateCompany(Long id, String companyName, String companyAddress, String teamPhone, String accountName, String bank, String accountNumber, Integer companyDisplay) {
        //参数验证
        if(ObjectUtils.isEmpty(id)) throw new CodeGodRunTimExcetion("被修改的合作企业id不能为空", this.getClass());
        if(ObjectUtils.isEmpty(companyName)) throw new CodeGodRunTimExcetion("企业名称不能为空", this.getClass());
        //查询被修改的合作企业
        OperationCompanyEntity companyEntity = operationCompanyRepository.findById(id).orElseThrow(()->new CodeGodRunTimExcetion("被修改的企业id可能错误，未查到匹配的合作企业", this.getClass()));
        //修改
        if (!companyEntity.getCompanyName().equals(companyName)) {
            OperationCompanyEntity ce = operationCompanyRepository.findAllByCompanyName(companyName);
            if(!ObjectUtils.isEmpty(ce)) throw new CodeGodRunTimExcetion("该企业名称已存在",this.getClass());
            companyEntity.setCompanyName(companyName);
        }
        companyEntity.setCompanyAddress(companyAddress);
        companyEntity.setTeamPhone(teamPhone);
        companyEntity.setAccountName(accountName);
        companyEntity.setBank(bank);
        companyEntity.setAccountNumber(accountNumber);
        if (!ObjectUtils.isEmpty(companyDisplay)) {
            companyEntity.setCompanyDisplay(companyDisplay);
        }
        companyEntity.setModifyTime(Calendar.getInstance().getTime());

        //保存
        operationCompanyRepository.save(companyEntity);

        return companyEntity;
    }

    /**
     * 查询全部合作企业分页
     * @param pageable
     * @return
     */
    @Override
    public Page<OperationCompanyEntity> findAll(Pageable pageable) {
        Specification<OperationCompanyEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Page<OperationCompanyEntity> all = operationCompanyRepository.findAll(specification, pageable);
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationCompanyEntity operationCompanyEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationCompanyEntity.getCompanyDisplay().toString(), DataBaseFinal.OPERATION_COMPANY_DISPLAY);
                operationCompanyEntity.setCompanyDisplayStr(bdd.getDataValue());
            }
        }
        return all;
    }
}
