package com.springbootjpa.codeGod.controller.NiuGeH;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sys")
public class SysController {

    @RequestMapping("/pb")
    @ResponseBody
    public AjaxResult pb(String mb,String Obj , HttpServletRequest request){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                System.out.println(mb+" mb");
                System.out.println(Obj+" Obj");
                return "aaa";
            }
        });
    }


}
