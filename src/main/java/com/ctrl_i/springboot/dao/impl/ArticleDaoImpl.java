package com.ctrl_i.springboot.dao.impl;

import com.ctrl_i.springboot.dao.ArticleDao;
import com.ctrl_i.springboot.entity.ArticleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by zekdot on 19-4-13.
 */
@Repository
public class ArticleDaoImpl  extends BaseDaoImpl<Integer, ArticleEntity> implements ArticleDao {
    @Override
    public List<ArticleEntity> getOnePageArticleAfterId(int lastId, int pageSize) throws Exception {
        String hql="from ArticleEntity where id>? order by id desc";
        Object [] params={lastId};
        return executeHQL(hql,params,0,pageSize);
    }
}
