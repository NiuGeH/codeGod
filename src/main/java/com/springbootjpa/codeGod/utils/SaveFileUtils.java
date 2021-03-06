package com.springbootjpa.codeGod.utils;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.controller.HumanResources.MemberSignContractController;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.service.baseService.BaseDataDirctionaryService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@PropertySource({"classpath:application.properties", "classpath:application-dev.properties"})
public class SaveFileUtils {

    private static Logger logger = LoggerFactory.getLogger(MemberSignContractController.class);

    @Value("${file.savePath}")
    private String flieSavePath;

    @Autowired
    private BaseDataDirctionaryService baseDataDirctionaryService;

    public UploadFile saveFile(MultipartFile file) throws CodeGodException {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                String nowDay = new SimpleDateFormat("yyyyMMdd").format(new Date());
                File fileDir = new File(flieSavePath + nowDay);
                //上传的文件名
                String[] fileOldName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
                if (!fileDir.exists()) {
                    fileDir.mkdir();// 创建文件根目录
                }
                BaseDataDictionaryEntity bd = baseDataDirctionaryService.findByDataKeyAndColumReturnDataValue(fileOldName[fileOldName.length-1], DataBaseFinal.UPLOADFILE_FILETYPE);
                if (!(ObjectUtils.isEmpty(bd))) {
                    File file_type = new File(fileDir.getPath() + File.separator + bd.getDataKey());
                    if (!file_type.exists()) {
                        file_type.mkdir();// 创建文件根目录
                    }
                    String flieName = UUID.randomUUID().toString() + "." + fileOldName[fileOldName.length-1];
                    // 文件保存路径
                    String filePath = file_type.getPath() + File.separator + flieName;
                    // 转存文件
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath));
                    UploadFile uploadFile = new UploadFile();
                    uploadFile.setDisplayName(file.getOriginalFilename());
                    uploadFile.setCreateTime(DateTimeUtils.getNowTimeNormalString());
                    uploadFile.setFileUrl(nowDay + File.separator + fileOldName[fileOldName.length-1] + File.separator + flieName);
                    return uploadFile;
                } else {
                    throw new CodeGodException("上传文件失败 ======>不支持" + fileOldName[fileOldName.length-1] + " 类型文件上传", this.getClass());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.error("上传文件为空 SaveFileUtils.saveFile");
        return null;
    }

}
