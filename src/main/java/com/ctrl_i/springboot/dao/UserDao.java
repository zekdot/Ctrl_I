package com.ctrl_i.springboot.dao;

import com.ctrl_i.springboot.entity.UserEntity;

/**
 * Create by zekdot on 19-4-13.
 */
public interface UserDao extends BaseDao<String, UserEntity> {
    public String[] getUserArray() throws Exception;
}
