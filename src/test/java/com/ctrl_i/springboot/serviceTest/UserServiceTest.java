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
        Assert.assertEquals(userService.register(null,null,null).getCode(),2);
        Assert.assertEquals(userService.register(username,password,"452526076@qq.com").getCode(),1);
        Assert.assertEquals(userService.register("test",password,"452526076@qq.com").getCode(),0);
        Assert.assertEquals(userService.register("test1",password,"452526076@qq.com").getCode(),0);
    }
    @Test
    public void testActivate(){
        Assert.assertEquals(userService.activateUsername(null,null).getCode(),4);
        Assert.assertEquals(userService.activateUsername("zekdot","444").getCode(),2);
        Assert.assertEquals(userService.activateUsername("zekdotd","444").getCode(),1);
        Assert.assertEquals(userService.activateUsername("test","dfsaf").getCode(),3);
        Assert.assertEquals(userService.activateUsername("test","ae9e1c91731b3509e327cb72af5bb488").getCode(),0);
    }
    @Test
    public void testLogin(){
        String username="zekdot";
        String password="123456";
        Assert.assertEquals(userService.login(username,password).getCode(),0);
        Assert.assertEquals(userService.login(username,"123").getCode(),1);
        Assert.assertEquals(userService.login(null,null).getCode(),2);
        Assert.assertEquals(userService.login("test1","123456").getCode(),3);
    }
}
