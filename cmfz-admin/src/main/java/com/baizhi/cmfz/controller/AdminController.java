package com.baizhi.cmfz.controller;

import com.baizhi.cmfz.entity.Admin;
import com.baizhi.cmfz.service.AdminService;
import com.baizhi.cmfz.service.impl.AdminServiceImpl;
import com.baizhi.cmfz.util.CreateValidateCode;
import com.baizhi.cmfz.util.NewValidateCodeUtils;
import org.apache.commons.codec.net.URLCodec;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/7/4.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService as;

    @RequestMapping("/login")
    public String loginAdmin(String adName ,String adPassword,String enCode,HttpSession session,HttpServletResponse response,boolean checkbox) throws Exception{

        Admin admin = as.queryAdmin(adName, adPassword);

        //System.out.println(session.getAttribute("code"));

        if(session.getAttribute("code").equals(enCode)&&admin!=null){
            if(checkbox){
                Cookie cookie = new Cookie(URLEncoder.encode("name","utf-8"),URLEncoder.encode("adName","utf-8"));
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            return "index";
        }else{
            return "error";
        }


    }
    @RequestMapping("/getValidCode")
    public void getValidCode(HttpServletResponse response, HttpSession session) throws Exception{
        NewValidateCodeUtils getCode = new NewValidateCodeUtils(80, 30, 2);
        session.setAttribute("code",getCode.getCode());
        System.out.println(session.getAttribute("code"));
        getCode.write(response.getOutputStream());

    }
}
