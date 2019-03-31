package com.ctrl_i.springboot.daoTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dao.TestUserDao;
import com.ctrl_i.springboot.entity.TestUserEntity;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Create by zekdot on 19-3-31.
 */

public class TestUserTest extends TmallApplicationTests {
    @Resource
    private TestUserDao testUserDao;
    @Test
    public void testSave(){
        TestUserEntity testUserEntity=new TestUserEntity();
        testUserEntity.setId("123456");
        testUserEntity.setPassword("123456");
        try {
            testUserDao.save(testUserEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGet(){
        try {
            TestUserEntity testUserEntity=testUserDao.get("123456");
            Assert.assertEquals(testUserEntity.getPassword(),"123456");
            Assert.assertEquals(testUserEntity.getId(),"123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
