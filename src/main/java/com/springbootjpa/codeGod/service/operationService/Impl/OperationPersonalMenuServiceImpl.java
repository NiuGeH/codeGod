package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.operation.OperationPersonalMenuEntity;
import com.springbootjpa.codeGod.repository.Operation.OperationPersonalMenuRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.operationService.OperationPersonalMenuService;
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
 * @date 2019/12/25 11:52
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationPersonalMenuServiceImpl implements OperationPersonalMenuService {

    @Autowired
    private OperationPersonalMenuRepository operationPersonalMenuRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private SaveFileUtils saveFileUtils = new SaveFileUtils();

    /**
     * 设置个人中心菜单图标
     * @param myInvitation 我的邀请图标
     * @param demandList 需求列表图标
     * @param recommendProject 推荐项目图标
     * @param publishOrder 发起工单图标
     * @param myAgent 我的经纪人图标
     * @param publishDemand 发布需求图标
     * @param operationDemand 运维需求图标
     * @return
     * @throws CodeGodException
     */
    @Override
    public OperationPersonalMenuEntity setMenuIcon(MultipartFile myInvitation, MultipartFile demandList, MultipartFile recommendProject, MultipartFile publishOrder, MultipartFile myAgent, MultipartFile publishDemand, MultipartFile operationDemand) throws CodeGodException {
        //参数验证
        if(ObjectUtils.isEmpty(myInvitation) || myInvitation.getSize()==0) throw new CodeGodRunTimExcetion("'我的邀请'图标不能为空", this.getClass());
        if(ObjectUtils.isEmpty(demandList) || demandList.getSize()==0) throw new CodeGodRunTimExcetion("'需求列表'图标不能为空", this.getClass());
        if(ObjectUtils.isEmpty(recommendProject) || recommendProject.getSize()==0) throw new CodeGodRunTimExcetion("'推荐项目'图标不能为空", this.getClass());
        if(ObjectUtils.isEmpty(publishOrder) || publishOrder.getSize()==0) throw new CodeGodRunTimExcetion("'发起工单'图标不能为空", this.getClass());
        if(ObjectUtils.isEmpty(myAgent) || myAgent.getSize()==0) throw new CodeGodRunTimExcetion("'我的经纪人'图标不能为空", this.getClass());
        if(ObjectUtils.isEmpty(publishDemand) || publishDemand.getSize()==0) throw new CodeGodRunTimExcetion("'发布需求'图标不能为空", this.getClass());
        if(ObjectUtils.isEmpty(operationDemand) || operationDemand.getSize()==0) throw new CodeGodRunTimExcetion("'运维需求'图标不能为空", this.getClass());

        OperationPersonalMenuEntity menuEntity = new OperationPersonalMenuEntity();
        List<OperationPersonalMenuEntity> list = operationPersonalMenuRepository.findAll();
        if(!ObjectUtils.isEmpty(list) && list.size()>0){
            menuEntity = list.get(0);
        }
        UploadFile uploadFile = null;
        //我的邀请图标
        uploadFile = saveFileUtils.saveFile(myInvitation);
        if(menuEntity.getMyInvitation() != null) uploadFile.setId(menuEntity.getMyInvitation().getId());
        menuEntity.setMyInvitation(uploadFileRepository.save(uploadFile));
        //需求列表图标
        uploadFile = saveFileUtils.saveFile(demandList);
        if(menuEntity.getDemandList() != null) uploadFile.setId(menuEntity.getDemandList().getId());
        menuEntity.setDemandList(uploadFileRepository.save(uploadFile));
        //推荐项目图标
        uploadFile = saveFileUtils.saveFile(recommendProject);
        if(menuEntity.getRecommendProject() != null) uploadFile.setId(menuEntity.getRecommendProject().getId());
        menuEntity.setRecommendProject(uploadFileRepository.save(uploadFile));
        //发起工单图标
        uploadFile = saveFileUtils.saveFile(publishOrder);
        if(menuEntity.getPublishOrder() != null) uploadFile.setId(menuEntity.getPublishOrder().getId());
        menuEntity.setPublishOrder(uploadFileRepository.save(uploadFile));
        //我的经纪人图标
        uploadFile = saveFileUtils.saveFile(myAgent);
        if(menuEntity.getMyAgent() != null) uploadFile.setId(menuEntity.getMyAgent().getId());
        menuEntity.setMyAgent(uploadFileRepository.save(uploadFile));
        //发布需求图标
        uploadFile = saveFileUtils.saveFile(publishDemand);
        if(menuEntity.getPublishDemand() != null) uploadFile.setId(menuEntity.getPublishDemand().getId());
        menuEntity.setPublishDemand(uploadFileRepository.save(uploadFile));
        //运维需求图标
        uploadFile = saveFileUtils.saveFile(operationDemand);
        if(menuEntity.getOperationDemand() != null) uploadFile.setId(menuEntity.getOperationDemand().getId());
        menuEntity.setOperationDemand(uploadFileRepository.save(uploadFile));
        //更新时间
        menuEntity.setModifyTime(Calendar.getInstance().getTime());

        operationPersonalMenuRepository.save(menuEntity);

        return menuEntity;
    }

    /**
     * 查询个人中心菜单那图标
     * @return
     */
    @Override
    public OperationPersonalMenuEntity findAll() {
        OperationPersonalMenuEntity menuEntity = new OperationPersonalMenuEntity();
        List<OperationPersonalMenuEntity> list = operationPersonalMenuRepository.findAll();
        if(list != null && list.size()>0){
            menuEntity = list.get(0);
        }
        return menuEntity;
    }
}
