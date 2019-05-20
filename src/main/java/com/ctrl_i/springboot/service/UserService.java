package com.ctrl_i.springboot.service;

import com.ctrl_i.springboot.dto.Envelope;

/**
 * Create by zekdot on 19-4-13.
 */
public interface UserService {
    /**
     * 根据用户名和密码进行登录
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public Envelope login(String username,String password);

    /**
     * 根据用户名密码进行注册
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return 结果
     */
    public Envelope register(String username,String password,String email);

    /**
     * 激活用户状态
     * @param username 用户名
     * @param code 激活码
     * @return 是否激活
     */
    public Envelope activateUsername(String username,String code);
}
