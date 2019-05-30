package com.ctrl_i.springboot.dao.impl;

import com.ctrl_i.springboot.dao.ReadDao;
import com.ctrl_i.springboot.entity.ReadEntity;
import com.ctrl_i.springboot.entity.ReadEntityPK;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Create by zekdot on 19-5-30.
 */
@Repository
public class ReadDaoImpl extends BaseDaoImpl<ReadEntityPK, ReadEntity> implements ReadDao {

    @Override
    public HashMap<String, HashMap<Integer, Double>> getUserScore() throws Exception {
        String hql="from ReadEntity";
        List<ReadEntity> rls=executeHQL(hql);
        HashMap<String, HashMap<Integer, Double>> userScore=new HashMap<>();
        for (ReadEntity readEntity:rls){
            String uid=readEntity.getuId();
            String hql2="from ReadEntity where uId=?";
            Object[] params={uid};
            HashMap<Integer,Double> umap=new HashMap<>();
            List<ReadEntity> urls=executeHQL(hql2,params);
            for (ReadEntity ur:urls)
                umap.put(ur.getaId(),ur.getRate());
            userScore.put(uid,umap);
        }
        return userScore;
    }

    @Override
    public int[] getUserArticle(String uid) {

        return new int[0];
    }
}
