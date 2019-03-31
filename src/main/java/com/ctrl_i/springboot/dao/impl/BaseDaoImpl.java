package com.ctrl_i.springboot.dao.impl;

import com.ctrl_i.springboot.HibernateUtil;
import com.ctrl_i.springboot.dao.BaseDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class BaseDaoImpl<K extends Serializable,T> implements BaseDao<K,T> {
    private Class<T> entityClass;
    public BaseDaoImpl() {
        this.entityClass = null;
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[1];
        }
    }
    @Override
    public T get(K id) {
        Session session= HibernateUtil.getSession();
        try{
            //Transaction transaction=session.beginTransaction();
            return (T) session.get(entityClass,id);
        }catch (Exception e) {
            throw e;
        }finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public K save(T object) throws Exception {
        K id;
        Session session=HibernateUtil.getSession();
        try{
            executeBySql("SET NAMES 'utf8mb4'");
            Transaction transaction=session.beginTransaction();
            id= (K) session.save(object);
            transaction.commit();
        }catch (Exception e) {
            throw e;
        }finally {
            HibernateUtil.closeSession();
        }
        return id;
    }

    @Override
    public void update(T object) {
        Session session=HibernateUtil.getSession();
        try{
            Transaction transaction=session.beginTransaction();
            session.update(object);
            transaction.commit();
        }catch (Exception e) {
            throw e;
        }finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public void delete(T object) {
        Session session=HibernateUtil.getSession();
        try{
            Transaction transaction=session.beginTransaction();
            session.delete(object);
            transaction.commit();
        }catch (Exception e) {
            throw e;
        }finally {
            HibernateUtil.closeSession();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> executeHQL(String hql,Object[] params,int limit,int start,int length)throws Exception{
        List<T> list=null;
        try{
            Session session=HibernateUtil.getSession();
            Transaction transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            if(limit!=-1)
                query.setFirstResult(limit);
            if(params!=null)
                for(int i=0;i<params.length;i++){//设置参数
                    query.setParameter(i,params[i]);
                }
            if(start>0){
                query.setFirstResult(start);
            }
            if(length>0){
                query.setMaxResults(length);
            }
            list=query.list();
            transaction.commit();
            return list;
        }catch (Exception e) {
            throw e;
        }finally {
            HibernateUtil.closeSession();
        }
    }
    public List<T> executeHQL(String hql,Object[] params,int start,int length)throws Exception{
        return executeHQL(hql, params, -1, start, length);
    }
    public List<T> executeHQL(String hql,Object[] params)throws Exception{
        return executeHQL(hql, params,-1,-1,-1);
    }
    public List<T> executeHQL(String hql)throws Exception{
        return executeHQL(hql,null);
    }
    public List<T> executeHQL(String hql,Object[] params,int limit)throws Exception{
        return executeHQL(hql, params,limit,-1,-1);
    }
    public List<T> executeHQL(String hql,int limit)throws Exception{
        return executeHQL(hql, null,limit);
    }
    public List<Object[]> queryBySql(String sql) {
        List<Object[]> list = HibernateUtil.getSession().createSQLQuery(sql).list();
        return list;
    }
    public int executeBySql(String sql) {
        int result ;
        Session session=HibernateUtil.getSession();
        Transaction transaction=session.beginTransaction();
        SQLQuery query = session.createSQLQuery(sql);
        result = query.executeUpdate();
        transaction.commit();
        return result;
    }

    public void saveOrUpdate(T object) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(object);
            transaction.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }

    }

}
