package com.springbootjpa.codeGod.service.NiuGeH;

import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    Page<MemberEntity> findAll(Pageable pageable);
}
