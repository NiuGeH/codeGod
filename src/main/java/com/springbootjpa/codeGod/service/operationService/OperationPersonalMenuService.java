package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.operation.OperationPersonalMenuEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 11:46
 */
public interface OperationPersonalMenuService {

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
    OperationPersonalMenuEntity setMenuIcon(MultipartFile myInvitation, MultipartFile demandList, MultipartFile recommendProject, MultipartFile publishOrder, MultipartFile myAgent, MultipartFile publishDemand, MultipartFile operationDemand) throws CodeGodException;

    /**
     * 查询个人中心菜单图标
     * @return
     */
    OperationPersonalMenuEntity findAll();
}
