package com.ctrl_i.springboot.util;

import org.springframework.util.DigestUtils;

/**
 * 获取字符串的md5摘要
 *
 * @author zekdot
 */
public class PasswordUtil {
    private static String salt="shudaixiong";   //盐
    public static String getMd5(String value) {
        value=new StringBuffer().append(salt).append(value).toString(); //加盐做md5
        return DigestUtils.md5DigestAsHex(value.getBytes());
    }
}
