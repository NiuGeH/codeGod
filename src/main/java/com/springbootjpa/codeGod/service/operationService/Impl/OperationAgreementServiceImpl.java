package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.operation.OperationAgreementEntity;
import com.springbootjpa.codeGod.repository.Operation.OperationAgreementRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.operationService.OperationAgreementService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/26 18:02
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationAgreementServiceImpl implements OperationAgreementService {

    @Autowired
    private OperationAgreementRepository operationAgreementRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private SaveFileUtils saveFileUtils = new SaveFileUtils();

    /**
     * 设置协议
     * @param userServiceAgreement 用户服务协议
     * @param residentNotice 驻场须知
     * @param codeGodSignAgreement 码神签约协议
     * @param workerOutsourcingAgreement 人力外包协议
     * @param projectSignAgreement 项目签约协议
     * @param parttimeAgreement 兼职协议
     * @param fulltimeAgreement 全职协议
     * @param recommendReward 推荐奖励
     * @return
     */
    @Override
    public OperationAgreementEntity setAgreement(String userServiceAgreement, String residentNotice, MultipartFile codeGodSignAgreement, MultipartFile workerOutsourcingAgreement, MultipartFile projectSignAgreement, MultipartFile parttimeAgreement, MultipartFile fulltimeAgreement, Integer recommendReward) throws CodeGodException {
        OperationAgreementEntity agreementEntity = new OperationAgreementEntity();
        List<OperationAgreementEntity> list = operationAgreementRepository.findAll();
        if(!ObjectUtils.isEmpty(list) && list.size()>0){
            agreementEntity = list.get(0);
        }

        //设置
        UploadFile uploadFile = null;
        //用户服务协议
        agreementEntity.setUserServiceAgreement(userServiceAgreement);
        //主场须知
        agreementEntity.setResidentNotice(residentNotice);
        //码神签约协议
        if(!ObjectUtils.isEmpty(codeGodSignAgreement)){
            if(codeGodSignAgreement.getSize() == 0) throw new CodeGodRunTimExcetion("上传文件'"+codeGodSignAgreement.getOriginalFilename()+"'为空", this.getClass());
            uploadFile = saveFileUtils.saveFile(codeGodSignAgreement);
            if (agreementEntity.getCodeGodSignAgreement() != null) uploadFile.setId(agreementEntity.getCodeGodSignAgreement().getId());
            agreementEntity.setCodeGodSignAgreement(uploadFileRepository.save(uploadFile));
        }else {
            agreementEntity.setCodeGodSignAgreement(null);
        }
        //人力外包协议
        if(!ObjectUtils.isEmpty(workerOutsourcingAgreement)){
            if(workerOutsourcingAgreement.getSize() == 0) throw new CodeGodRunTimExcetion("上传文件'"+workerOutsourcingAgreement.getOriginalFilename()+"'为空", this.getClass());
            uploadFile = saveFileUtils.saveFile(workerOutsourcingAgreement);
            if (agreementEntity.getWorkerOutsourcingAgreement() != null) uploadFile.setId(agreementEntity.getWorkerOutsourcingAgreement().getId());
            agreementEntity.setWorkerOutsourcingAgreement(uploadFileRepository.save(uploadFile));
        }else {
            agreementEntity.setWorkerOutsourcingAgreement(null);
        }
        //项目签约协议
        if(!ObjectUtils.isEmpty(projectSignAgreement)){
            if(projectSignAgreement.getSize() == 0) throw new CodeGodRunTimExcetion("上传文件'"+projectSignAgreement.getOriginalFilename()+"'为空", this.getClass());
            uploadFile = saveFileUtils.saveFile(projectSignAgreement);
            if (agreementEntity.getProjectSignAgreement() != null) uploadFile.setId(agreementEntity.getProjectSignAgreement().getId());
            agreementEntity.setProjectSignAgreement(uploadFileRepository.save(uploadFile));
        }else {
            agreementEntity.setProjectSignAgreement(null);
        }
        //兼职协议
        if(!ObjectUtils.isEmpty(parttimeAgreement)){
            if(parttimeAgreement.getSize() == 0) throw new CodeGodRunTimExcetion("上传文件'"+parttimeAgreement.getOriginalFilename()+"'为空", this.getClass());
            uploadFile = saveFileUtils.saveFile(parttimeAgreement);
            if (agreementEntity.getParttimeAgreement() != null) uploadFile.setId(agreementEntity.getParttimeAgreement().getId());
            agreementEntity.setParttimeAgreement(uploadFileRepository.save(uploadFile));
        }else {
            agreementEntity.setParttimeAgreement(null);
        }
        //全职协议
        if(!ObjectUtils.isEmpty(fulltimeAgreement)){
            if(fulltimeAgreement.getSize() == 0) throw new CodeGodRunTimExcetion("上传文件'"+fulltimeAgreement.getOriginalFilename()+"'为空", this.getClass());
            uploadFile = saveFileUtils.saveFile(fulltimeAgreement);
            if (agreementEntity.getFulltimeAgreement() != null) uploadFile.setId(agreementEntity.getFulltimeAgreement().getId());
            agreementEntity.setFulltimeAgreement(uploadFileRepository.save(uploadFile));
        }else {
            agreementEntity.setFulltimeAgreement(null);
        }
        //推荐奖励
        if(ObjectUtils.isEmpty(recommendReward)){
            agreementEntity.setRecommendReward(0);
        }else {
            agreementEntity.setRecommendReward(recommendReward);
        }
        //更新时间
        agreementEntity.setModifyTime(Calendar.getInstance().getTime());

        //保存
        operationAgreementRepository.save(agreementEntity);

        return agreementEntity;
    }

    /**
     * 查询全部协议
     * @return
     */
    @Override
    public OperationAgreementEntity findAll() {
        OperationAgreementEntity agreementEntity = new OperationAgreementEntity();
        List<OperationAgreementEntity> list = operationAgreementRepository.findAll();
        if(list != null && list.size()>0){
            agreementEntity = list.get(0);
        }
        return agreementEntity;
    }
}
