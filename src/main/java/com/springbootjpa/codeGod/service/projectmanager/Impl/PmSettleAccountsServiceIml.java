package com.springbootjpa.codeGod.service.projectmanager.Impl;


import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.projectmanager.PmSettleAccountsEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmSettleAccountsentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmSettleAccountsService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class PmSettleAccountsServiceIml implements PmSettleAccountsService {

    @Autowired
    private PmSettleAccountsentityRepository pmSettleAccountsentityRepository;
    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;
    @Autowired
    private UploadFileRepository uploadFileRepository;
    @Autowired
    private SaveFileUtils saveFileUtils;

    /**
     * 结算单查询
     * @param pageable
     * @return
     */
    @Override
    public Page<PmSettleAccountsEntity> findAll(Pageable pageable) {
        List<PmSettleAccountsEntity> all = pmSettleAccountsentityRepository.findAll();
        List<PmSettleAccountsEntity> list = new ArrayList<PmSettleAccountsEntity>();
        for (PmSettleAccountsEntity pmSettleAccountsEntity: all) {
           pmSettleAccountsEntity.setStatusString(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmSettleAccountsEntity.getStatus().toString(),DataBaseFinal.PM_SETTLE_ACCOUNTSSTATUS).getDataValue());
            list.add(pmSettleAccountsEntity);
        }
        return new PageImpl<PmSettleAccountsEntity>(list, pageable,list.size());
    }

    /**
     * 保存结算单
     * @param pmSettleAccountsEntity
     * @param certificate
     * @return
     * @throws CodeGodException
     */
    @Override
    public boolean saveSettleAccount(PmSettleAccountsEntity pmSettleAccountsEntity, MultipartFile certificate) throws CodeGodException {
        if(!StringUtils.isEmpty(certificate)){
            UploadFile save = uploadFileRepository.save(saveFileUtils.saveFile(certificate));
            pmSettleAccountsEntity.setCertificate(save);
        }
        PmSettleAccountsEntity save = pmSettleAccountsentityRepository.save(pmSettleAccountsEntity);
        if(!ObjectUtils.isEmpty(save)){
            return true;
        }
        return false;
    }

    /**
     * 保存付款凭证
     * @param pmSettleAccountsEntity
     * @param payEvidence
     * @return
     * @throws CodeGodException
     */
    @Override
    public boolean collectionConfirmation(PmSettleAccountsEntity pmSettleAccountsEntity, MultipartFile payEvidence) throws CodeGodException {
        if(!StringUtils.isEmpty(payEvidence)){
            UploadFile save = uploadFileRepository.save(saveFileUtils.saveFile(payEvidence));
            pmSettleAccountsEntity.setCertificate(save);
        }
        PmSettleAccountsEntity save = pmSettleAccountsentityRepository.save(pmSettleAccountsEntity);
        if(!ObjectUtils.isEmpty(save)){
            return true;
        }
        return false;
    }
}
