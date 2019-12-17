package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.repository.HumanResources.MemberSignContractentityRepository;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberSignContractService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberSignContractServiceImpl implements MemberSignContractService {

    @Autowired
    private MemberSignContractentityRepository memberSignContractentityRepository;

    private SaveFileUtils saveFileUtils = new SaveFileUtils();

    /**
     * 根据验证码和签约结果分页
     * @param pageable 分页实体
     * @param validationCode 验证码
     * @param siginEnd 签约结果
     * @return Page<MemberSignContractEntity>
     */
    @Override
    public Page<MemberSignContractEntity> doPageByValidationCodeAndSiginEnd(Pageable pageable, Integer validationCode, Integer siginEnd) {

        return memberSignContractentityRepository.findAll(getSpecification(validationCode,siginEnd),pageable);
    }

    /**
     * 根据Id 删除签约结果
     * @param id memberSignContract的 Id
     */
    @Override
    public void byIdDelMemberSignContract(Long id) {
        Optional<MemberSignContractEntity> byId = memberSignContractentityRepository.findById(id);
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("需要删除的Id不存在 byIdDelMemberSignContract",this.getClass());
        }
        memberSignContractentityRepository.delete(byId.get());
    }

    /**
     * 签约设置保存 ===> 签约成功 / 签约失败
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
    @Override
    public MemberSignContractEntity signSetting(MemberSignContractEntity memberSignContractEntity, MemberEntity memberEntity, MemberPrivacyEntity memberPrivacyEntity,
                                                MultipartFile memberPhotoFileMultipartFile, MultipartFile memberPhotoHeadPortraitMultipartFile, MultipartFile[] memberPersonalDataMultipartFile,
                                                MultipartFile memberCardFrontMultipartFile, MultipartFile memberCardReverseSideMultipartFile, MultipartFile[] siginAgreementMultipartFile,
                                                HttpServletRequest request) {
        
        return null;
    }

    /**
     * 根据关键词进行分页
     * @param validationCode 验证码
     * @param siginEnd 签约结果
     * @return Specification<MemberSignContractEntity>
     */
    private Specification<MemberSignContractEntity> getSpecification(Integer validationCode,Integer siginEnd) {
        return new Specification<MemberSignContractEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberSignContractEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate p2 = criteriaBuilder.like(root.get("nickName"),"%zhang%");
                Predicate preValida = !ObjectUtils.isEmpty(validationCode) ?  criteriaBuilder.equal(root.get("siginVerificationCode"),validationCode) : criteriaBuilder.like(root.get("siginJobs"),"%");
                Predicate preSigin = !ObjectUtils.isEmpty(siginEnd) ? criteriaBuilder.equal(root.get("siginResults"),siginEnd) : criteriaBuilder.like(root.get("siginJobs"),"%");
                return criteriaBuilder.and(preValida,preSigin);
            }
        };
    }


}
