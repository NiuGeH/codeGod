package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.operation.OperationAgreementEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/26 17:47
 */
public interface OperationAgreementService {

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
    OperationAgreementEntity setAgreement(String userServiceAgreement, String residentNotice, MultipartFile codeGodSignAgreement,
                                          MultipartFile workerOutsourcingAgreement, MultipartFile projectSignAgreement,
                                          MultipartFile parttimeAgreement, MultipartFile fulltimeAgreement, Integer recommendReward) throws CodeGodException;

    /**
     * 查询全部协议
     * @return
     */
    OperationAgreementEntity findAll();
}
