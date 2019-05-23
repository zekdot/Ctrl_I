package com.ctrl_i.springboot.controller;

import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.UserEntity;
import com.ctrl_i.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Create by zekdot on 19-4-13.
 */
@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param username 用户名
     * @param password  密码
     * @param email 邮箱
     * @return  结果
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public String register(String username, String password, String email){
        try{
            return userService.register(username,password,email).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }

    /**
     * 登录
     * @param username 用户名
     * @param password  密码
     * @return  结果
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpSession session,String username,String password){
        try{
            Envelope envelope = userService.login(username,password);
            if(envelope.getCode() == 0){    //如果验证成功
                session.setAttribute("user",envelope.getObj()); //设置session
                envelope.setObj(session.getId());   //返回sessionid
            }
            return envelope.toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }
    /**
     * 激活
     * @param username 用户名
     * @param code 激活码
     * @return  结果
     */
    @RequestMapping(value = "/activate",method = RequestMethod.POST)
    @ResponseBody
    public String activate(String username,String code){
        try{
            return userService.activateUsername(username,code).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String info(HttpSession session){
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        if(userEntity == null)
            return Envelope.unLogin.toString();
        try{
            return userService.getUserInfo(userEntity.getuId()).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }

    /**
     * 修改用户信息
     * @param session
     * @param nickname 昵称
     * @return
     */
    @RequestMapping(value = "/changeInfo",method = RequestMethod.POST)
    @ResponseBody
    public String changeInfo(HttpSession session,String nickname){
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        if(userEntity == null)
            return Envelope.unLogin.toString();
        try{
            return userService.changeUserInfo(userEntity.getuId(),nickname).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }
}
