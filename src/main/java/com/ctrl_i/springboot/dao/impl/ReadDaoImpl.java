package com.ctrl_i.springboot.dao.impl;

import com.ctrl_i.springboot.dao.ReadDao;
import com.ctrl_i.springboot.entity.ReadEntity;
import com.ctrl_i.springboot.entity.ReadEntityPK;
import org.springframework.stereotype.Repository;

/**
 * Create by zekdot on 19-5-30.
 */
@Repository
public class ReadDaoImpl extends BaseDaoImpl<ReadEntityPK, ReadEntity> implements ReadDao {
}
