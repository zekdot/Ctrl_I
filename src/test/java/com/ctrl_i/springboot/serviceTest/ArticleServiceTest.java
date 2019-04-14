package com.ctrl_i.springboot.serviceTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.service.ArticleService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by zekdot on 19-4-13.
 */
public class ArticleServiceTest extends TmallApplicationTests {
    @Autowired
    private ArticleService articleService;

    @Test
    public void testGetList(){
        System.out.println(articleService.getArticleList(0));
        Assert.assertEquals(articleService.getArticleList(65535).getCode(),1);
    }

    @Test
    public void testGetArt(){
        System.out.println(articleService.getArticleById(2));
        Assert.assertEquals(articleService.getArticleById(65535).getCode(),1);
    }
}
