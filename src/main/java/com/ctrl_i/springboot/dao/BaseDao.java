package com.ctrl_i.springboot.dao;

import java.io.Serializable;
/**
 * Dao基接口
 * 提供删存查改
 */
public interface BaseDao<K extends Serializable,T> {
    /**
     * 根据ID获取实体对象.
     * @param id
     * @return 实体对象
     */
    public T get(K id) throws Exception;
    /**
     * 保存实体对象.
     * @param object
     * @return ID
     */
    public K save(T object) throws Exception;

    /**
     * 根据实体对象更新.
     * @param object 对象
     */
    public void update(T object) throws Exception;

    /**
     * 删除记录.
     * @param object
     */
    public void delete(T object) throws Exception;

    /**
     * 保存或者更新实体
     * @param object
     * @throws Exception
     */
    public void saveOrUpdate(T object) throws Exception;
}
