package com.springbootjpa.codeGod.service.humanResourcesService;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.humanResources.MemberContractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEmployEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * 雇佣需求
 * @author NiuGeH
 */
public interface MemberEmployService {

    /**
     * 雇佣需求 分页
     * @param pageable 页容量和页数
     * @return Page<MemberEmployEntity>
     */
    Page<MemberEmployEntity> doPage(Pageable pageable);

    /**
     * 拒绝
     * @param id 需求Id
     * @param employReason 拒绝原因
     * @return 处理后的实体
     */
    MemberEmployEntity doRefused(Long id,String employReason);

    /**
     * 签约
     * @param memberContractEntity 签约实体
     * @param contractPactMultipartFile 合同文件
     * @return 签约后合同实体
     */
    MemberContractEntity doSignEmploy(MemberContractEntity memberContractEntity,String contractAddressId, MultipartFile contractPactMultipartFile) throws CodeGodException;
}
