package com.ctrl_i.springboot.daoTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dao.UserDao;
import com.ctrl_i.springboot.entity.UserEntity;
import com.ctrl_i.springboot.util.PasswordUtil;
import org.apache.catalina.User;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Create by zekdot on 19-5-12.
 */
public class UserDaoTest extends TmallApplicationTests {
    @Resource
    private UserDao userDao;
    @Test
    public void testInsert(){
        UserEntity userEntity = new UserEntity();
        userEntity.setuId("zekdot");
        userEntity.setPassword(PasswordUtil.getMd5("123456"));
        userEntity.setEmail("zekdot@qq.com");
        userEntity.setState((byte) 0);
        try {
            userDao.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGet(){
        try {
            UserEntity userEntity = userDao.get("zekdot");
            System.out.println(userEntity.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetUserArray(){
        try {
            String[] userIds = userDao.getUserArray();
            for(String userId:userIds){
                System.out.println(userId);
            }
            //System.out.println(userDao.getUserArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
