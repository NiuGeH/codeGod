package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.projectmanager.PmContractEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PmContractService {

    boolean save(PmContractEntity pmContractEntity, Long id, Integer status,MultipartFile contractDocument) throws CodeGodException;


}
