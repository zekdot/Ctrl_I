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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("from ArticleEntity ");
        Object[] params = null;
        if(lastId != 0){    //如果说获取非第一页的文章
            stringBuilder.append("where id < ? ");
            params = new Object[1];
            params[0] = lastId;
        }
        stringBuilder.append("order by id desc");
        //Object [] params={lastId};
        return executeHQL(stringBuilder.toString(),params,0,pageSize);
    }
}
