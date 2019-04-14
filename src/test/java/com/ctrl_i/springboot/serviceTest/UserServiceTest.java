package com.ctrl_i.springboot.serviceTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by zekdot on 19-4-13.
 */
public class UserServiceTest extends TmallApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    public void testRegister(){
        String username="zekdot";
        String password="123456";
        //Assert.assertEquals(userService.register(username,password).getCode(),0);
        Assert.assertEquals(userService.register(null,null).getCode(),2);
        Assert.assertEquals(userService.register(username,password).getCode(),1);
    }

    @Test
    public void testLogin(){
        String username="zekdot";
        String password="123456";
        Assert.assertEquals(userService.login(username,password).getCode(),0);
        Assert.assertEquals(userService.login(username,"123").getCode(),1);
        Assert.assertEquals(userService.login(null,null).getCode(),2);
    }
}
