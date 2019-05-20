package com.ctrl_i.springboot.service.impl;

import com.ctrl_i.springboot.ConfigConstant;
import com.ctrl_i.springboot.dao.UserDao;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.UserEntity;
import com.ctrl_i.springboot.service.UserService;
import com.ctrl_i.springboot.util.EmailUtil;
import com.ctrl_i.springboot.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Create by zekdot on 19-4-13.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Envelope login(String username, String password) {
        if(username==null||password==null)
            return new Envelope(2,"用户名密码不能为空",null);
        UserEntity userEntity;
        try {
            userEntity=userDao.get(username);
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        if(userEntity==null||!userEntity.getPassword().equals(PasswordUtil.getMd5(password))){  //检查用户名是否为空，检查密码
            return new Envelope(1,"用户名不存在或密码错误",null);
        }
        if(userEntity.getState() == 0){
            return new Envelope(3,"用户名未激活！",null);
        }
        return new Envelope(userEntity);//返回携带用户实体的信封
    }

    @Override
    public Envelope register(String username, String password, String email) {
        if(username==null || password==null || email == null)
            return new Envelope(2,"用户名密码和邮箱不能为空",null);
        try {
            if(userDao.get(username)!=null){    //检查用户名是否已经被注册
                return new Envelope(1,"用户名已被注册",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        UserEntity userEntity=new UserEntity(); //构造实体
        userEntity.setuId(username);
        userEntity.setPassword(PasswordUtil.getMd5(password));  //密码md5加盐加密
        userEntity.setEmail(email); //设置邮箱
        userEntity.setState((byte) 0);  //设置状态为未激活
        StringBuilder linkBuilder = new StringBuilder();
        linkBuilder.append("http://").append(ConfigConstant.ip).append(":").append(ConfigConstant.fport)
        .append("/activate.html?");
        linkBuilder.append("username=").append(userEntity.getuId()).append("&");
        linkBuilder.append("code=").append(PasswordUtil.getMd5(userEntity.getPassword()));
        try {
            EmailUtil.send(userEntity.getEmail(),linkBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Envelope(3,"激活邮件发送失败！",null);
        }
        try {
            userDao.save(userEntity);   //持久化实体
            return Envelope.success;    //返回携带用户实体的信封
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
    }

    @Override
    public Envelope activateUsername(String username, String code) {
        if(username == null || code == null){
            return new Envelope(4,"关键信息不可为空",null);
        }
        UserEntity userEntity;
        try {
            userEntity = userDao.get(username); //获取用户实体
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        if(userEntity == null){
            return new Envelope(1,"用户未注册",null);
        }
        if(userEntity.getState() == 1){ //如果已经激活
            return new Envelope(2,"用户已经激活",null);
        }
        if(!code.equals(PasswordUtil.getMd5(userEntity.getPassword()))){    //如果激活码错误
            return new Envelope(3,"激活码错误！",null);
        }
        userEntity.setState((byte) 1);  //设置激活
        try {
            userDao.update(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        return Envelope.success;
    }
}
