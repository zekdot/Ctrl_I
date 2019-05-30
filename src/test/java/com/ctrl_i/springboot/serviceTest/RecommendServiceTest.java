package com.ctrl_i.springboot.serviceTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.service.RecommendService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by zekdot on 19-5-30.
 */
public class RecommendServiceTest extends TmallApplicationTests {
    @Autowired
    private RecommendService recommendService;
    @Test
    public void testRecommend(){
        recommendService.recommend();
    }
    @Test
    public void testGetTodayRecommend(){
        System.out.println(recommendService.getTodayRecommend("zekdot"));
    }
}
