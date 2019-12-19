package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationMedalRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationTeamRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.baseService.BaseDataDirctionaryService;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberentityRepository memberentityRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private BaseDataDirctionaryService baseDataDirctionaryService;

    @Autowired
    private OperationMedalRepository operationMedalRepository;

    @Autowired
    private OperationTeamRepository operationTeamRepository;

    private Specification<MemberEntity> getSpecification( String memberDisplay, String memberStationing, String memberSigningPost, String memberType, String keyWord) {
        return new Specification<MemberEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate p2 = criteriaBuilder.like(root.get("nickName"),"%zhang%");
                //是否公开
                Predicate perMemberDisplay = !ObjectUtils.isEmpty(memberDisplay) ? criteriaBuilder.equal(root.get("memberDisplay"), memberDisplay) : criteriaBuilder.like(root.get("nickName"), "%");
                Predicate perMemberStationing = !ObjectUtils.isEmpty(memberStationing) ? criteriaBuilder.equal(root.get("memberStationing"),memberStationing) : criteriaBuilder.like(root.get("nickName"),"%");
                Predicate perMemberSigningPost = !ObjectUtils.isEmpty(memberSigningPost) ? criteriaBuilder.equal(root.get("memberSigningPost"),memberSigningPost) : criteriaBuilder.like(root.get("nickName"),"%");
                Predicate perMemberType = !ObjectUtils.isEmpty(memberType) ? criteriaBuilder.equal(root.get("memberType"),memberType) : criteriaBuilder.like(root.get("nickName") , "%");
                Predicate perNickName = !ObjectUtils.isEmpty(keyWord) ? criteriaBuilder.like(root.get("nickName"),"%"+keyWord+"%") : criteriaBuilder.like(root.get("nickName"),"%");
                Predicate perRealName = !ObjectUtils.isEmpty(keyWord) ? criteriaBuilder.like(root.get("memberPricacy").get("memberRealName"), "%"+keyWord+"%") : criteriaBuilder.like(root.get("nickName"),"%");
                Predicate perMobile = !ObjectUtils.isEmpty(keyWord) ? criteriaBuilder.like(root.get("memberPricacy").get("memberMobile"),"%"+keyWord+"%") :  criteriaBuilder.like(root.get("nickName"),"%");
                Predicate or = criteriaBuilder.or(perNickName, perRealName, perMobile);
                return criteriaBuilder.and(perMemberDisplay, perMemberSigningPost, perMemberStationing, perMemberType,or);
            }
        };
    }


    /**
     *  用户列表分页
     * @param pageable pageable
     * @param memberDisplay 是否公开
     * @param memberStationing 是否驻场
     * @param memberSigningPost  所有角色
     * @param memberType 所有用户
     * @param keyWord 关键测
     * @return 集合实体
     */
    @Override
    public Page<MemberEntity> findAll(Pageable pageable, String memberDisplay, String memberStationing, String memberSigningPost, String memberType, String keyWord) {

        return memberentityRepository.findAll(getSpecification(memberDisplay, memberStationing, memberSigningPost, memberType, keyWord),pageable);
    }


    /**
     *
     * @return 返回Member 数据字典中对应的数据 HashMap
     */
    @Override
    public HashMap<String, Object> allDataFromDictionary() {
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("memberType",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERTYPE));
        hashMap.put("memberSigningPost",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGPOST));
        hashMap.put("memberSigningMode",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGMODE));
        hashMap.put("memberMedal", operationMedalRepository.findAllByState(OperationEnum.OPERATION_STATE_ZC.getIndex()));
        hashMap.put("memberStationing", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSTATIONING));
        hashMap.put("memberStatus",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSTATUS));
        hashMap.put("memberDisplay",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERDISPLAY));
        hashMap.put("memberSigningStatus",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGSTATUS));
        hashMap.put("memberTeam", operationTeamRepository.findAllByState(OperationEnum.OPERATION_STATE_ZC.getIndex()));
//        hashMap.put("")
        return hashMap;
    }



    /**
     * 封装文件的,号拆分 换成 List<UploadFile>
     * @param memberEndId 实体类
     * @return 封装后的实体
     */
    @Override
    public MemberEntity doStringConvarToList(MemberEntity memberEndId) {
        MemberPrivacyEntity memberPrivacyEntity = memberEndId.getMemberPricacy();
        if(ObjectUtils.isEmpty(memberPrivacyEntity)){
            log.debug("用户私密表为空 直接返回");
            return memberEndId;
        }
        //用户个人资料信息表 用户信息可以是多个
        String memberPersonalData = memberPrivacyEntity.getMemberPersonalData();
        if(!(StringUtils.isEmpty(memberPersonalData))){
            List<UploadFile> list = new ArrayList<>();
            String[] split = memberPersonalData.split(",");
            for (String s : split) {
                if(!StringUtils.isEmpty(s)){
                    list.add(uploadFileRepository.findById(Long.valueOf(s)).get());
                }
            }
            memberEndId.getMemberPricacy().setMemberPersonalDataList(list);
        }
        //签约协议也可以是多个
        String memberSigningAgreement = memberEndId.getMemberSigningAgreement();
        if(!(StringUtils.isEmpty(memberSigningAgreement))){
            List<UploadFile> list = new ArrayList<>();
            String[] split = memberSigningAgreement.split(",");
            for (String s : split) {
                if(!StringUtils.isEmpty(s)){
                    list.add(uploadFileRepository.findById(Long.valueOf(s)).get());
                }
            }
            memberEndId.setMemberSigningAgreementList(list);
        }
        return memberEndId;
    }
}
