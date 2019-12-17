package com.springbootjpa.codeGod.controller.projectmanager;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.repository.projectmanager.PmDemandentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PmDemandBase {
    protected Gson gson = new Gson();
    @Autowired
    protected PmDemandService pmDemandService;
}