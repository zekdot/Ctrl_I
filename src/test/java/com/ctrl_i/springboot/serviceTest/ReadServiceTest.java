package com.ctrl_i.springboot.serviceTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.service.ReadService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ReadServiceTest extends TmallApplicationTests {
    @Autowired
    ReadService readService;
    @Test
    public void rateWhileRead(){
        //System.out.println("new entity");
        Envelope e=readService.rateWhileRead("lrc",1,9);
        //System.out.println("new entity");
        Assert.assertEquals(0,e.getCode());
        e=readService.rateWhileRead("lrc",1,7);
        Assert.assertEquals(0,e.getCode());
    }
}
