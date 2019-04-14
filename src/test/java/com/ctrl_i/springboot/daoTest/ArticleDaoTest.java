package com.ctrl_i.springboot.daoTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dao.ArticleDao;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Create by zekdot on 19-4-13.
 */
public class ArticleDaoTest extends TmallApplicationTests {
    @Resource
    private ArticleDao articleDao;
    @Test
    public void testReadOnePage(){
        try {
            Assert.assertEquals(articleDao.getOnePageArticleAfterId(0,20).size(),2);
            Assert.assertEquals(articleDao.getOnePageArticleAfterId(0,20).get(0).getaId(),2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
