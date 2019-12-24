package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import com.springbootjpa.codeGod.entity.operation.OperationResourceSkillEntity;
import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.repository.Operation.OperationResourceRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationResourceSkillRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationSkillRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.operationService.OperationResourceService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/23 18:07
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationResourceServiceImpl implements OperationResourceService {

    @Autowired
    private OperationResourceRepository operationResourceRepository;

    @Autowired
    private OperationSkillRepository operationSkillRepository;

    @Autowired
    private OperationResourceSkillRepository operationResourceSkillRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private SaveFileUtils saveFileUtils = new SaveFileUtils();

    /**
     * 添加资源类型
     * @param name 资源类型名称
     * @param resourcePhotoFile 上传的图标文件
     * @param amount 人数
     * @param order 资源排序
     * @param skillMap 关联技术栈map
     * @param display 是否显示：0是，1否
     * @return
     * @throws CodeGodException
     */
    @Override
    public OperationResourceEntity addResource(String name, MultipartFile resourcePhotoFile, Long amount, Long order, Map<Long,String> skillMap, Integer display) throws CodeGodException {
        //参数验证
        if(ObjectUtils.isEmpty(name)){
            throw new CodeGodRunTimExcetion("资源类型名称不能为空", this.getClass());
        }
        //判断资源类型是否存在
        OperationResourceEntity re = operationResourceRepository.findByResourceName(name);
        if(!ObjectUtils.isEmpty(re)){
            throw new CodeGodRunTimExcetion("该资源类型名称已存在", this.getClass());
        }
        //添加
        OperationResourceEntity resourceEntity = new OperationResourceEntity();
        resourceEntity.setResourceName(name);
        if(!ObjectUtils.isEmpty(resourcePhotoFile)){
            UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(resourcePhotoFile));
            resourceEntity.setResourcePhoto(uploadFile);
        }
        if(ObjectUtils.isEmpty(amount)){
            resourceEntity.setAmount((long)OperationEnum.OPERATION_RESOURCE_AMOUNT.getIndex());
        }else {
            resourceEntity.setAmount(amount);
        }
        if(ObjectUtils.isEmpty(order)){
            resourceEntity.setResourceOrder(operationResourceRepository.findMaxResourceOrder()+1);
        }else {
            resourceEntity.setResourceOrder(order);
        }
        if(ObjectUtils.isEmpty(display)){
            resourceEntity.setDisplay(OperationEnum.OPERATION_DISPLAY_YES.getIndex());
        }else {
            resourceEntity.setDisplay(display);
        }
        resourceEntity.setCreateTime(Calendar.getInstance().getTime());

        //保存资源类型
        OperationResourceEntity resource = operationResourceRepository.save(resourceEntity);

        //保存关联技术栈
        OperationResourceSkillEntity rse = new OperationResourceSkillEntity();
        Set<Long> skillIds = skillMap.keySet();
        for(Long skillId : skillIds){
            OperationSkillEntity skillEntity = operationSkillRepository.findById(skillId).orElseThrow(()->new CodeGodRunTimExcetion(skillMap.get(skillId)+"技术栈不存在",this.getClass()));
            rse.setResource(resource);
            rse.setSkill(skillEntity);
            operationResourceSkillRepository.save(rse);
        }

        return resource;
    }
}
