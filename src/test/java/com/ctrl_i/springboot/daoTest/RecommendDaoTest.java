package com.ctrl_i.springboot.daoTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dao.RecommendDao;
import com.ctrl_i.springboot.entity.RecommendEntity;
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
}
