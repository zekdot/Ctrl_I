package com.ctrl_i.springboot.daoTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dao.ArticleDao;
import com.ctrl_i.springboot.entity.ArticleEntity;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create by zekdot on 19-4-13.
 */
public class ArticleDaoTest extends TmallApplicationTests {
    @Resource
    private ArticleDao articleDao;
    @Test
    public void testReadOnePage(){
        try {
            //List<ArticleEntity> list = ;
            Assert.assertEquals(articleDao.getOnePageArticleAfterId(349,30).get(0).getaId(),348);
            Assert.assertEquals(articleDao.getOnePageArticleAfterId(0,30).get(0).getaId(),370);
//            Assert.assertEquals(articleDao.getOnePageArticleAfterId(0,20).size(),2);
//            Assert.assertEquals(articleDao.getOnePageArticleAfterId(0,20).get(0).getaId(),2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
