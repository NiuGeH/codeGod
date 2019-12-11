package com.springbootjpa.codeGod.controller.NiuGeH;

import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.repository.MemberentityRepository;
import com.springbootjpa.codeGod.service.NiuGeH.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "/memberController" ,description = "NiuGeH 后台用户Controller")
@Controller
@RequestMapping("/memberController")
public class MemberController {

    @Autowired
    private MemberentityRepository memberentityRepository;

    @Autowired
    private MemberService memberService;

    @RequestMapping("/doPage")
    @ResponseBody
    public PageResult<MemberEntity> doPage(PageRequestParam pages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberEntity>>() {
            @Override
            public Page<MemberEntity> invoke(Pageable var1) throws Exception {
                return memberService.findAll(var1);
            }
        });
    }

}
