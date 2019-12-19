package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.repository.HumanResources.MemberPrivacyentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberSignContractentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberSignContractService;
import com.springbootjpa.codeGod.utils.DateTimeUtils;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberSignContractServiceImpl implements MemberSignContractService {

    @Autowired
    private MemberSignContractentityRepository memberSignContractentityRepository;

    @Autowired
    private MemberPrivacyentityRepository memberPrivacyentityRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private MemberentityRepository memberentityRepository;

    @Autowired
    private SaveFileUtils saveFileUtils = new SaveFileUtils();

    /**
     * 根据验证码和签约结果分页
     *
     * @param pageable       分页实体
     * @param validationCode 验证码
     * @param siginEnd       签约结果
     * @return Page<MemberSignContractEntity>
     */
    @Override
    public Page<MemberSignContractEntity> doPageByValidationCodeAndSiginEnd(Pageable pageable, Integer validationCode, Integer siginEnd) {

        return memberSignContractentityRepository.findAll(getSpecification(validationCode, siginEnd), pageable);
    }

    /**
     * 根据Id 删除签约结果
     *
     * @param id memberSignContract的 Id
     */
    @Override
    public void byIdDelMemberSignContract(Long id) {
        Optional<MemberSignContractEntity> byId = memberSignContractentityRepository.findById(id);
        if (ObjectUtils.isEmpty(byId)) {
            throw new CodeGodRunTimExcetion("需要删除的Id不存在 byIdDelMemberSignContract", this.getClass());
        }
        MemberSignContractEntity entity = byId.get();
        if(ObjectUtils.isEmpty(entity.getSiginResults())){
            throw new CodeGodRunTimExcetion("签约删除的状态为空" , this.getClass());
        }
        //未申请状态删除
        if(entity.getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_WSQ.getIndex()){
            memberSignContractentityRepository.delete(entity);
        }else if(entity.getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YJJ.getIndex()){
            //已拒绝删除方式
            MemberEntity memberEndId = entity.getMemberEndId();
            memberSignContractentityRepository.delete(entity);
            //删除私密信息表
            memberPrivacyentityRepository.delete(memberPrivacyentityRepository.findAllByMemberId(memberEndId.getId()));
            //删除用户信息表
            memberentityRepository.delete(memberEndId);

        }
    }

    /**
     * 签约设置保存 ===> 签约成功 / 签约失败
     *
     * @param memberSignContractEntity             用户签约审核实体类
     * @param memberEntity                         用户基础信息表
     * @param memberPrivacyEntity                  用户私密信息表
     * @param memberPhotoFileMultipartFile         形象照
     * @param memberPhotoHeadPortraitMultipartFile 头像
     * @param memberPersonalDataMultipartFile      个人资料（可多个）
     * @param memberCardFrontMultipartFile         身份证正面
     * @param memberCardReverseSideMultipartFile   身份证反面
     * @param siginAgreementMultipartFile          签约协议（可多个）
     * @param request                              请求req
     * @return MemberSignContractEntity
     */
    @Override
    public MemberSignContractEntity signSetting(MemberSignContractEntity memberSignContractEntity, MemberEntity memberEntity, MemberPrivacyEntity memberPrivacyEntity,
                                                MultipartFile memberPhotoFileMultipartFile, MultipartFile memberPhotoHeadPortraitMultipartFile, MultipartFile[] memberPersonalDataMultipartFile,
                                                MultipartFile memberCardFrontMultipartFile, MultipartFile memberCardReverseSideMultipartFile, MultipartFile[] siginAgreementMultipartFile,
                                                HttpServletRequest request) throws CodeGodRunTimExcetion, CodeGodException {

        Optional<MemberSignContractEntity> byId = memberSignContractentityRepository.findById(memberSignContractEntity.getId());
        if (ObjectUtils.isEmpty(byId)) {
            throw new CodeGodRunTimExcetion("Id 不存在", this.getClass());
        }
        //签约表
        MemberSignContractEntity jdbc_SignContrac = byId.get();
        if (jdbc_SignContrac.getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_DDSH.getIndex()) {
            //设置签约结果
            jdbc_SignContrac.setSiginResults(memberEntity.getMemberSigningStatus());

            //如果是待审核状态，此时用户已经在pc前端申请过了 所有 MemberSignContract 中的 memberEndId 是用户申请的信息
            MemberEntity jdbc_memberEntity = jdbc_SignContrac.getMemberEndId();
            //修改时间
            jdbc_memberEntity.setUpdateTime(new Date());
            jdbc_memberEntity.setNickName(memberEntity.getNickName());
            jdbc_memberEntity.setMemebrCity(memberEntity.getMemebrCity());
            jdbc_memberEntity.setMemberWord(memberEntity.getMemberWord());
            jdbc_memberEntity.setMemberIntroduce(memberEntity.getMemberIntroduce());
            jdbc_memberEntity.setMemberType(memberEntity.getMemberType());
            jdbc_memberEntity.setMemberSigningPost(memberEntity.getMemberSigningPost());
            jdbc_memberEntity.setMemberSigningMode(memberEntity.getMemberSigningMode());
            jdbc_memberEntity.setMemberMedal(memberEntity.getMemberMedal());
            jdbc_memberEntity.setMemberStationing(memberEntity.getMemberStationing());
            jdbc_memberEntity.setMemberStatus(memberEntity.getMemberStatus());
            jdbc_memberEntity.setMemberDisplay(memberEntity.getMemberDisplay());
            jdbc_memberEntity.setMemberSigningStatus(memberEntity.getMemberSigningStatus());
            jdbc_memberEntity.setMemberLongRange(memberEntity.getMemberLongRange());
            jdbc_memberEntity.setMemberOnSiteDevelopment(memberEntity.getMemberOnSiteDevelopment());
            //用户私人信息表
            MemberPrivacyEntity jdbc_memberPrivacy = memberPrivacyentityRepository.findAllByMemberId(memberEntity.getId());
            jdbc_memberPrivacy.setMemberRealName(memberPrivacyEntity.getMemberRealName());
            jdbc_memberPrivacy.setMemberEmail(memberPrivacyEntity.getMemberEmail());
            jdbc_memberPrivacy.setMemberQq(memberPrivacyEntity.getMemberQq());
            jdbc_memberPrivacy.setMemberWechat(memberPrivacyEntity.getMemberWechat());
            jdbc_memberPrivacy.setMemberContactAddress(memberPrivacyEntity.getMemberContactAddress());
            jdbc_memberPrivacy.setMemberCashWithdrawal(memberPrivacyEntity.getMemberCashWithdrawal());
            jdbc_memberPrivacy.setMemberCardno(memberPrivacyEntity.getMemberCardno());
            jdbc_memberPrivacy.setMemberMobile(memberPrivacyEntity.getMemberMobile());
            jdbc_memberPrivacy.setMemberPwd(memberPrivacyEntity.getMemberPwd());

                //形象照
                if(!memberPhotoFileMultipartFile.isEmpty()){
                    UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberPhotoFileMultipartFile));
                    jdbc_memberPrivacy.setMemberPhotoFile(uploadFile);
                }
                //头像
                if(!(ObjectUtils.isEmpty(memberPhotoHeadPortraitMultipartFile)) && memberPhotoHeadPortraitMultipartFile.getSize() != 0){
                    UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberPhotoHeadPortraitMultipartFile));
                    jdbc_memberPrivacy.setMemberPhotoHeadPortrait(uploadFile);
                }
                //个人资料（可多个）
                if(!ObjectUtils.isEmpty(memberPersonalDataMultipartFile)&& memberPersonalDataMultipartFile.length>0){
                    //循环获取file数组中得文件
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0;i<memberPersonalDataMultipartFile.length;i++){
                        MultipartFile file = memberPersonalDataMultipartFile[i];
                        //保存文件
                        if(!(ObjectUtils.isEmpty(file)) && file.getSize() != 0){
                            UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(file));
                            sb.append(uploadFile.getId()).append(",");
//                            System.out.println(save.toString());
                        }
                    }
                    if(!StringUtils.isEmpty(sb.toString())){
                        sb.delete(sb.length()-1,sb.length());
                        jdbc_memberPrivacy.setMemberPersonalData(sb.toString());
                    }
                }
                //身份证正面
                if(!(ObjectUtils.isEmpty(memberCardFrontMultipartFile)) && memberCardFrontMultipartFile.getSize() != 0){
                    UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberCardFrontMultipartFile));
                    jdbc_memberPrivacy.setMemberCardFront(uploadFile);
                }
                //身份证反面
                if(!(ObjectUtils.isEmpty(memberCardReverseSideMultipartFile)) && memberCardReverseSideMultipartFile.getSize() != 0){
                    UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberCardReverseSideMultipartFile));
                    jdbc_memberPrivacy.setMemberCardReverseSide(uploadFile);
                }
                //签约协议(可多个)
                if(siginAgreementMultipartFile!=null&&siginAgreementMultipartFile.length>0){
                    //循环获取file数组中得文件
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0;i<siginAgreementMultipartFile.length;i++){
                        MultipartFile file = siginAgreementMultipartFile[i];
                        //保存文件
                        if(!(ObjectUtils.isEmpty(file)) && file.getSize() != 0){
                            UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(file));
                            sb.append(uploadFile.getId()).append(",");
                        }
                    }
                    if(!StringUtils.isEmpty(sb.toString())){
                        sb.delete(sb.length()-1,sb.length());
                        jdbc_memberEntity.setMemberSigningAgreement(sb.toString());
                    }
                }

            return memberSignContractEntity;

        }else{
            throw new CodeGodRunTimExcetion("签约状态不为待审核状态",this.getClass());
        }
    }

    /**
     * 根据关键词进行分页
     *
     * @param validationCode 验证码
     * @param siginEnd       签约结果
     * @return Specification<MemberSignContractEntity>
     */
    private Specification<MemberSignContractEntity> getSpecification(Integer validationCode, Integer siginEnd) {
        return new Specification<MemberSignContractEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberSignContractEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate preValida = !ObjectUtils.isEmpty(validationCode) ? criteriaBuilder.equal(root.get("siginVerificationCode"), validationCode) : criteriaBuilder.like(root.get("siginJobs"), "%");
                Predicate preSigin = !ObjectUtils.isEmpty(siginEnd) ? criteriaBuilder.equal(root.get("siginResults"), siginEnd) : criteriaBuilder.like(root.get("siginJobs"), "%");
                return criteriaBuilder.and(preValida, preSigin);
            }
        };
    }


}
