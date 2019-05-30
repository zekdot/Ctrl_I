package com.ctrl_i.springboot.dao;

import com.ctrl_i.springboot.entity.ReadEntity;
import com.ctrl_i.springboot.entity.ReadEntityPK;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by zekdot on 19-5-30.
 */
public interface ReadDao extends BaseDao<ReadEntityPK, ReadEntity> {
    public HashMap<String, HashMap<Integer,Double>> getUserScore() throws Exception;
}
