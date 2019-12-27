package com.springbootjpa.codeGod.service.humanResourcesService;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.repository.HumanResources.MemberPrivacyentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberSignContractentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface MemberSignContractService {


    /**
     * 根据验证码和签约结果分页
     * @param pageable 分页实体
     * @param validationCode 验证码
     * @param siginEnd 签约结果
     * @return Page<MemberSignContractEntity>
     */
    Page<MemberSignContractEntity> doPageByValidationCodeAndSiginEnd(Pageable pageable,Integer validationCode,Integer siginEnd);

    /**
     * 根据Id 删除签约结果
     * @param id memberSignContract的 Id
     */
    void byIdDelMemberSignContract(Long id);

    /**
     * 签约设置保存 ===> 签约成功
     * @param memberSignContractEntity 用户签约审核实体类
     * @param memberEntity 用户基础信息表
     * @param memberPrivacyEntity   用户私密信息表
     * @param memberPhotoFileMultipartFile 形象照
     * @param memberPhotoHeadPortraitMultipartFile 头像
     * @param memberPersonalDataMultipartFile 个人资料（可多个）
     * @param memberCardFrontMultipartFile 身份证正面
     * @param memberCardReverseSideMultipartFile 身份证反面
     * @param siginAgreementMultipartFile 签约协议（可多个）
     * @param request 请求req
     * @return MemberSignContractEntity
     */
    MemberSignContractEntity signSetting(MemberSignContractEntity memberSignContractEntity,MemberEntity memberEntity, MemberPrivacyEntity memberPrivacyEntity,
                                          MultipartFile memberPhotoFileMultipartFile, MultipartFile memberPhotoHeadPortraitMultipartFile,
                                          MultipartFile[] memberPersonalDataMultipartFile, MultipartFile memberCardFrontMultipartFile,
                                          MultipartFile memberCardReverseSideMultipartFile, MultipartFile[] siginAgreementMultipartFile,String delKey
            , HttpServletRequest request) throws CodeGodException;
}
