package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.operation.OperationMedalEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.repository.Operation.OperationMedalRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.operationService.OperationMedalService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/19 10:40
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationMedalServiceImpl implements OperationMedalService {

    @Autowired
    private OperationMedalRepository operationMedalRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private SaveFileUtils saveFileUtils = new SaveFileUtils();

    /**
     * 添加勋章
     * @param name 勋章名称
     * @param medalPhotoFile 上传的勋章图标文件
     * @return
     */
    @Override
    public OperationMedalEntity addMedal(String name, MultipartFile medalPhotoFile) throws CodeGodException {
        //判断新添加的勋章名称是否存在
        OperationMedalEntity medalEntity = operationMedalRepository.findByMedalName(name);
        if(!ObjectUtils.isEmpty(medalEntity)){
            throw new CodeGodRunTimExcetion("该勋章名称已存在", this.getClass());
        }

        //添加
        OperationMedalEntity medal = new OperationMedalEntity();
        medal.setMedalName(name);
        if(!ObjectUtils.isEmpty(medalPhotoFile)){
            UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(medalPhotoFile));
            medal.setMedalPhoto(uploadFile);
        }
        medal.setState(OperationEnum.OPERATION_STATE_ZC.getIndex());
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        medal.setCreateTime(now);

        //保存
        operationMedalRepository.save(medal);

        return medal;
    }

    /**
     * 修改勋章
     * @param id 勋章id
     * @param newName 勋章新名称
     * @param medalPhotoFile 上传的勋章图标文件
     * @return
     */
    @Override
    public OperationMedalEntity updateMedal(Long id, String newName, MultipartFile medalPhotoFile) throws CodeGodException {
        //查询需要修改的勋章
        OperationMedalEntity medalEntity = operationMedalRepository.getOne(id);
        log.info("勋章修改前：" + medalEntity.toString());

        //修改勋章属性
        medalEntity.setMedalName(newName);
        if(!ObjectUtils.isEmpty(medalPhotoFile)){
            UploadFile uploadFile = medalEntity.getMedalPhoto();
            Long fileId = uploadFile.getId();
            uploadFile = saveFileUtils.saveFile(medalPhotoFile);
            uploadFile.setId(fileId);
            uploadFileRepository.save(uploadFile);
            medalEntity.setMedalPhoto(uploadFile);
        }
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        medalEntity.setModifyTime(now);
        log.info("勋章修改后：" + medalEntity.toString());

        //保存修改后的勋章
        operationMedalRepository.save(medalEntity);

        return medalEntity;
    }
}
