package com.ctrl_i.springboot.service.impl;

import com.ctrl_i.springboot.dao.UserDao;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.UserEntity;
import com.ctrl_i.springboot.service.UserService;
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
        return new Envelope(userEntity);//返回携带用户实体的信封
    }

    @Override
    public Envelope register(String username, String password) {
        if(username==null||password==null)
            return new Envelope(2,"用户名密码不能为空",null);
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
        try {
            userDao.save(userEntity);   //持久化实体
            return new Envelope(userEntity);    //返回携带用户实体的信封
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
    }
}
