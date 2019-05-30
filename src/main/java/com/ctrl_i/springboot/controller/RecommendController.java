package com.ctrl_i.springboot.controller;

import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.UserEntity;
import com.ctrl_i.springboot.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Create by zekdot on 19-5-30.
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;
    /**
     * 获取今日推荐
     * @param session
     * @return
     */
    @RequestMapping("/todayRec")
    @ResponseBody
    public String getTodayRecommend(HttpSession session){
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        if (userEntity == null){
            return Envelope.unLogin.toString();
        }
        try{
            return recommendService.getTodayRecommend(userEntity.getuId()).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }

    }
}
