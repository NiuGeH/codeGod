package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.projectmanager.PmContractEntity;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmContractentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmProjectentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmContractService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * 合同
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmContractServiceIml implements PmContractService {
    @Autowired
    private PmContractentityRepository pmContractentityRepository;
    @Autowired
    private PmProjectentityRepository pmProjectentityRepository;
    @Autowired
    private UploadFileRepository uploadFileRepository;
    @Autowired
    private SaveFileUtils saveFileUtils = new SaveFileUtils();
    /***
     * 保存合同 修改项目状态
     * @param pmContractEntity 合同实体类
     * @param id        项目ID
     * @param status   项目状态
     * @return
     */
    @Override
    public boolean save(PmContractEntity pmContractEntity, Long id, Integer status, MultipartFile contractDocument) throws CodeGodException {
        if(!ObjectUtils.isEmpty(contractDocument)&&contractDocument.getSize()!=0){
            UploadFile save = uploadFileRepository.save(saveFileUtils.saveFile(contractDocument));
            pmContractEntity.setContractDocument(save);
        }
        PmContractEntity save = pmContractentityRepository.save(pmContractEntity);
        int i = pmProjectentityRepository.updateProjectStatus(id, status);
        if(save!=null&&i>0){
            return true;
        }
        return false;
    }

    /**
     * 根据ID获取合同
     * @param id
     * @return
     */
    @Override
    public PmContractEntity findOneById(Long id) {

        Optional<PmContractEntity> byId = pmContractentityRepository.findById(id);
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("该合同不存在",this.getClass());
        }
        return byId.get();
    }

    /**
     * 合同分页查询
     * @param pageable
     * @return
     */
    @Override
    public Page<PmContractEntity> findAll(Pageable pageable) {
        return pmContractentityRepository.findAll(pageable);
    }


}
