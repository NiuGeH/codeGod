package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.projectmanager.PmSettleAccountsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PmSettleAccountsService {


    Page<PmSettleAccountsEntity> findAll(Pageable pageable);

    boolean saveSettleAccount(PmSettleAccountsEntity pmSettleAccountsEntity,MultipartFile certificate) throws CodeGodException;

    boolean collectionConfirmation(PmSettleAccountsEntity pmSettleAccountsEntity,MultipartFile payEvidence) throws CodeGodException;


}
