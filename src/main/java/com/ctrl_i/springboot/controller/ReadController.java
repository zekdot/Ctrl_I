package com.ctrl_i.springboot.controller;

import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.UserEntity;
import com.ctrl_i.springboot.service.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Create by zekdot on 19-5-30.
 */
@Controller
@RequestMapping("/read")
public class ReadController {
    @Autowired
    private ReadService readService;

    /**
     *
     * @param rate 评分
     * @param aId 文章id
     * @param session 保存用户登录信息
     * @return
     */
    @RequestMapping(value = "/readArt",method = RequestMethod.POST)
    @ResponseBody
    public String readOneArt(double rate,int aId,HttpSession session){
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        try{
            //System.out.println(rate);
            //System.out.println(aId);
            //System.out.println(userEntity.getuId());
            //return Envelope.success.toString();
            return readService.rateWhileRead(userEntity.getuId(),aId,rate).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }
}
