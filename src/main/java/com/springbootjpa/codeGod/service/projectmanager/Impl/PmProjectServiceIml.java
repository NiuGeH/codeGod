package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.alibaba.druid.util.StringUtils;
import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmDemandEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmDemandentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmProjectentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmProjectService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 项目
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmProjectServiceIml implements PmProjectService {
    @Autowired
    private PmProjectentityRepository pmProjectentityRepository;
    @Autowired
    private UploadFileRepository uploadFileRepository;
    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;
    @Autowired
    private MemberentityRepository memberentityRepository;
    @Autowired
    private PmDemandentityRepository pmDemandentityRepository;
    @Autowired
    private SaveFileUtils saveFiles = new SaveFileUtils();

    @Override
    public Page<PmProjectEntity> findAll() {
        return null;
    }

    /**
     * 获取产品经理下的项目
     *
     * @param projectManagerId 产品经理ID
     * @return 项目数量
     */
    @Override
    public int getCount(Long projectManagerId) {
        return pmProjectentityRepository.findAllByProjectManagerId(projectManagerId);
    }

    /**
     * 保存项目
     *
     * @param pmProjectEntity
     * @return
     */
    @Override
    public boolean saveProject(PmProjectEntity pmProjectEntity, MultipartFile[] requirementDocument) throws CodeGodException {
        if (!ObjectUtils.isEmpty(requirementDocument) && requirementDocument.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < requirementDocument.length; i++) {
                MultipartFile file = requirementDocument[i];
                //保存文件
                if (!(ObjectUtils.isEmpty(file)) && file.getSize() != 0) {
                    UploadFile uploadFile = uploadFileRepository.save(saveFiles.saveFile(file));
                    sb.append(uploadFile.getId());
                    if(i!=(requirementDocument.length-1)){
                        sb.append(",");
                    }
                }
                if (!org.springframework.util.StringUtils.isEmpty(sb.toString())) {
                    pmProjectEntity.setRequirementDocument(sb.toString());
                }
            }
        }
        MemberEntity memberEntity = memberentityRepository.findById(pmProjectEntity.getProductManagerId()).orElseThrow(() -> new CodeGodRunTimExcetion("该项目经理不存在", this.getClass()));
        PmDemandEntity pmDemandEntity = pmDemandentityRepository.findById(pmProjectEntity.getDemandId()).orElseThrow(() -> new CodeGodRunTimExcetion("该需求不存在！", this.getClass()));
        pmProjectEntity.setMemberEntity(memberEntity);
        pmProjectEntity.setPmDemandEntity(pmDemandEntity);
        PmProjectEntity save = pmProjectentityRepository.save(pmProjectEntity);
        if (save != null) {
            return true;
        }
        return false;
    }

    /**
     * 项目查询
     *
     * @param pageable      分页
     * @param projectStuats 项目状态
     * @return 返回项目列表
     */
    @Override
    public Page<PmProjectEntity> doPage(Pageable pageable, Integer projectStuats) {
        Specification sp = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(projectStuats.toString())) {
                    list.add(criteriaBuilder.equal(root.get("projectStatus"), projectStuats.toString()));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<PmProjectEntity> all = pmProjectentityRepository.findAll(sp, pageable);
        ArrayList<PmProjectEntity> list = new ArrayList<>();
        for (PmProjectEntity pmProjectEntity : all) {
            BaseDataDictionaryEntity value = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmProjectEntity.getProjectStatus().toString(), "pm_project.project_status");
            pmProjectEntity.setProjectStatus1(value.getDataValue());
            list.add(pmProjectEntity);
        }
        return new PageImpl<PmProjectEntity>(list, pageable, list.size());
    }

    /**
     * 单个项目数据
     *
     * @param id 项目iD
     * @return 项目信息
     */
    @Override
    public PmProjectEntity findOne(Long id) {
        Optional<PmProjectEntity> byId = pmProjectentityRepository.findById(id);
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("该项目不存在",this.getClass());
        }
        PmProjectEntity pmProjectEntity = byId.get();
        List<UploadFile> list = new ArrayList<>();
        String[] split = pmProjectEntity.getRequirementDocument().split(",");
        for (int i = 0;i < split.length ; i++){
            Optional<UploadFile> uploadFile = uploadFileRepository.findById(Long.valueOf(split[i]));
            if(ObjectUtils.isEmpty(uploadFile)){
                throw new CodeGodRunTimExcetion("该项目的需求文档不存在",this.getClass());
            }
            list.add(uploadFile.get());
        }
        pmProjectEntity.setRequirementDocumentList(list);
        return pmProjectEntity;
    }

    /**
     * 修改项目状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public boolean updateStatus(Long id, Integer status) {
        int i = pmProjectentityRepository.updateProjectStatus(id, status);
        if (i > 0) {
            return true;
        }
        return false;
    }
}
