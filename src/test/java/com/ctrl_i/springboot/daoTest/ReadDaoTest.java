package com.ctrl_i.springboot.daoTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dao.ReadDao;
import com.ctrl_i.springboot.entity.ReadEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Create by zekdot on 19-5-30.
 */
public class ReadDaoTest extends TmallApplicationTests {
    @Resource
    private ReadDao readDao;

    @Test
    public void testInsert(){
        ReadEntity readEntity = new ReadEntity();
        readEntity.setaId(1);
        readEntity.setuId("test");
        readEntity.setTime(new Timestamp(new Date().getTime()));
        readEntity.setRate(5.2);
        try {
            readDao.save(readEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getUserScoreTest(){
        try {
            Map map=readDao.getUserScore();
            System.out.println(map.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
