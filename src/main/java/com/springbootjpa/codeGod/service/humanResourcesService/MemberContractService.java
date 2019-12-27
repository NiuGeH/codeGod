package com.springbootjpa.codeGod.service.humanResourcesService;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.humanResources.MemberContractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEmployPersonnelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * 人力外包管理Service
 */
public interface MemberContractService {

    /**
     * 人力外包管理 分页
     * @param pageable 页容量和页数
     * @return Page<MemberContractEntity>
     */
    Page<MemberContractEntity> doPage(Pageable pageable);

    /**
     * 创建合同
     * @param memberContractEntity 实体
     * @param contractPactMultipartFile 合同文件
     * @return MemberContractEntity
     */
    MemberContractEntity doAddContract(MemberContractEntity memberContractEntity, MultipartFile contractPactMultipartFile) throws CodeGodException;

    /**
     * 编辑合同
     * @param memberContractEntity 实体
     * @param contractPactMultipartFile 合同文件
     * @return 实体
     * @throws CodeGodException 自定义异常
     */
    MemberContractEntity doEditContract(MemberContractEntity memberContractEntity, MultipartFile contractPactMultipartFile) throws CodeGodException;
}
