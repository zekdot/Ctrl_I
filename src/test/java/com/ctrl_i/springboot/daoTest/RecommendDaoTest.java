package com.ctrl_i.springboot.daoTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dao.RecommendDao;
import com.ctrl_i.springboot.dao.UserDao;
import com.ctrl_i.springboot.entity.RecommendEntity;
import org.apache.catalina.User;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Create by zekdot on 19-5-30.
 */
public class RecommendDaoTest extends TmallApplicationTests {
    @Resource
    private RecommendDao recommendDao;
    @Resource
    private UserDao userDao;
    @Test
    public void testInsert(){
        RecommendEntity recommendEntity = new RecommendEntity();
        recommendEntity.setContent("{'5':'测试'}");
        recommendEntity.setDate(new java.sql.Date(new Date().getTime()));
        recommendEntity.setuId("test");
        try {
            recommendDao.save(recommendEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getUidArrayTest(){
        try {
            String[] uls=userDao.getUserArray();
            for (String s:uls)
                System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
