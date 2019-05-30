package com.ctrl_i.springboot.dao.impl;

import com.ctrl_i.springboot.dao.UserDao;
import com.ctrl_i.springboot.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by zekdot on 19-4-13.
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<String, UserEntity> implements UserDao {
    @Override
    public String[] getUserArray() throws Exception {
        String hql="from UserEntity";
        List<UserEntity> uls=executeHQL(hql);
        String[] uidArray=new String[uls.size()];
        for (int i=0;i<uls.size();i++)
            uidArray[i]=uls.get(i).getuId();
        return uidArray;
    }
}
