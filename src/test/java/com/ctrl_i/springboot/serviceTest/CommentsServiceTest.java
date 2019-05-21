package com.ctrl_i.springboot.serviceTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.CommentsEntity;
import com.ctrl_i.springboot.service.CommentsService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Create by zekdot on 19-5-21.
 */
public class CommentsServiceTest extends TmallApplicationTests {
    @Autowired
    private CommentsService commentsService;
    @Test
    public void testInsert(){

        StringBuilder longTextBuilder = new StringBuilder();
        for(int i=0;i<202;i++)
            longTextBuilder.append("哦");
        Envelope envelope = commentsService.commentArticle(1,"zekdot","测试");
        Assert.assertEquals(envelope.getCode(),0);
        envelope = commentsService.commentArticle(1,"zekdot",longTextBuilder.toString());
        Assert.assertEquals(envelope.getCode(),1);
    }
    @Test
    public void testGet(){
        //Assert.assertEquals(((List<CommentsEntity>)(commentsService.getCommentsByaId(1,0).getObj())).size(),2);
        System.out.println(commentsService.getCommentsByaId(369,0));
    }
    @Test
    public void testDelete(){
        Assert.assertEquals(commentsService.deleteComment(2,"zekdot").getCode(),0);
        Assert.assertEquals(commentsService.deleteComment(1,"test").getCode(),1);
    }
}
