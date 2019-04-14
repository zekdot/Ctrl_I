package com.ctrl_i.springboot.dao.impl;

import com.ctrl_i.springboot.dao.UserDao;
import com.ctrl_i.springboot.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * Create by zekdot on 19-4-13.
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<String, UserEntity> implements UserDao {
}
