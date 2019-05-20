package com.ctrl_i.springboot.utilTest;

import com.ctrl_i.springboot.TmallApplicationTests;
import com.ctrl_i.springboot.util.EmailUtil;
import org.junit.Test;

/**
 * Create by zekdot on 19-5-12.
 */
public class EmailUtilTest extends TmallApplicationTests {
    @Test
    public void testSendEmail(){
        try {
            EmailUtil.send("448402571@qq.com","测试");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
