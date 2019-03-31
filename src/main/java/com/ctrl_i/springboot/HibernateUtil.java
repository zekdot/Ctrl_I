package com.ctrl_i.springboot;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class HibernateUtil {
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();//ThreadLocal对象
    private static SessionFactory sessionFactory = null;//SessionFactory对象
    //静态块
    static {
        try {
            // 加载Hibernate配置文件
            //Configuration cfg = new Configuration().configure();
            //sessionFactory = cfg.buildSessionFactory();
            sessionFactory=new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("创建会话工厂失败");
            e.printStackTrace();
        }
    }
    /**
     *	获取Session
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        if (session == null || !session.isOpen()) {
            if (sessionFactory == null) {
                rebuildSessionFactory();
            }
            session = (sessionFactory != null) ? sessionFactory.openSession(): null;
            //sessionFactory
            threadLocal.set(session);
        }

        return session;
    }
    /**
     * 重建会话工厂
     */
    public static void rebuildSessionFactory() {
        try {
            // 加载Hibernate配置文件
            Configuration cfg = new Configuration().configure();
            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("创建会话工厂失败");
            e.printStackTrace();
        }
    }
    /**
     * 获取SessionFactory对象
     * @return SessionFactory对象
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    /**
     *	关闭Session
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);
        if (session != null) {
            session.close();//关闭Session
        }
    }

    /**
     * 执行更新操作
     * @param hql
     */
    public static void executeUpdate(String hql) {

        Session session = getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);;
            System.out.println("query influenced: " + query.getQueryString());
            int n = query.executeUpdate();
            System.out.println("query influence: " + n);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


}