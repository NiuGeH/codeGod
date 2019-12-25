package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.projectmanager.PmRatingEntity;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmRatingentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmRatingService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.HashMap;

/**
 * 项目评价
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmEvaluationServiceIml implements PmRatingService {

    @Autowired
    private PmRatingentityRepository pmRatingentityRepository;
    @Autowired
    private UploadFileRepository uploadFileRepository;
    @Autowired
    private SaveFileUtils saveFileUtils;

    /**
     * 保存项目评价
     * @param performance 成果
     * @param photos    展示图片
     * @param map
     * @return
     * @throws CodeGodException
     */
    @Override
    public boolean saveRating(MultipartFile[] performance, MultipartFile photos, HashMap<String, String> map) throws CodeGodException {
        PmRatingEntity pmRatingEntity = new PmRatingEntity();
        pmRatingEntity.setGrade(Integer.valueOf(map.get("grade")));
        pmRatingEntity.setEvaluate(map.get("evaluate"));
        pmRatingEntity.setShows(Integer.valueOf(map.get("shows")));
        pmRatingEntity.getProjectEntity().setId(Long.valueOf(map.get("id")));
        if (!photos.isEmpty()) {
            UploadFile save = uploadFileRepository.save(saveFileUtils.saveFile(photos));
            pmRatingEntity.setPhotos(save);
        }
        if (performance.length > 0 && !ObjectUtils.isEmpty(performance)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < performance.length; i++) {
                MultipartFile file = performance[i];
                if (!(ObjectUtils.isEmpty(file)) && file.getSize() != 0) {
                    UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(file));
                    sb.append(uploadFile.getId()).append(",");
                }
                if (!org.springframework.util.StringUtils.isEmpty(sb.toString())) {
                    sb.delete(sb.length() - 1, sb.length());
                    pmRatingEntity.setPerformance(sb.toString());
                }
            }
        }
        PmRatingEntity save = pmRatingentityRepository.save(pmRatingEntity);
        if(!ObjectUtils.isEmpty(save)){
            return true;
        }
        return false;
    }
}
