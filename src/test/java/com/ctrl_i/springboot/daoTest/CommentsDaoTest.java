package com.ctrl_i.springboot.daoTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dao.CommentsDao;
import com.ctrl_i.springboot.entity.CommentsEntity;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Create by zekdot on 19-5-20.
 */
public class CommentsDaoTest extends TmallApplicationTests {
    @Resource
    private CommentsDao commentsDao;

    @Test
    public void testInsert(){
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setContent("测试内容");
        commentsEntity.setaId(1);
        commentsEntity.setuId("zekdot");
        commentsEntity.setTime(new Timestamp(new Date().getTime()));
        try {
            commentsDao.save(commentsEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGet(){
        try {
            CommentsEntity commentsEntity = commentsDao.get(1);
            Assert.assertEquals(commentsEntity.getuId(),"zekdot");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testGetList(){
        List<CommentsEntity> list = null;
        try {
            list = commentsDao.getCommentByaId(1,0,30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.size(),1);
    }
}
