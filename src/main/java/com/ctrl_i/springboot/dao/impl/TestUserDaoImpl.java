package com.ctrl_i.springboot.dao.impl;

import com.ctrl_i.springboot.dao.TestUserDao;
import com.ctrl_i.springboot.entity.TestUserEntity;
import org.springframework.stereotype.Repository;

/**
 * Create by zekdot on 19-3-31.
 */
@Repository
public class TestUserDaoImpl extends BaseDaoImpl<String, TestUserEntity> implements TestUserDao {
}
