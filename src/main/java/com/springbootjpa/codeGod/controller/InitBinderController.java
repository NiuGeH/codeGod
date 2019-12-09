package com.springbootjpa.codeGod.controller;

import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.utils.AesUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

@ControllerAdvice
public class InitBinderController {

//    private AesUtils aesUtils = new AesUtils();
//    @InitBinder
//    public void init1(WebDataBinder binder){
//        List<Validator> allowedFields =binder.getValidators();
//        for (Validator allowedField : allowedFields) {
//            System.out.println(allowedField.toString());
//        }
//        //        binder.registerCustomEditor(String.class,
////                aesUtils.deCode(AjaxUtils.RSA_PUBLICAKEY));
//
//
//
//    }
}
