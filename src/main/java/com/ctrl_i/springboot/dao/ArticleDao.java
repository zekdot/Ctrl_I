package com.ctrl_i.springboot.dao;

import com.ctrl_i.springboot.entity.ArticleEntity;

import java.util.List;

/**
 * Create by zekdot on 19-4-13.
 */
public interface ArticleDao extends BaseDao<Integer, ArticleEntity> {
    /**
     * 获取一页新的文章
     * @param lastId 最后一条文章id
     * @param pageSize 页大小
     * @return
     */
    public List<ArticleEntity> getOnePageArticleAfterId(int lastId,int pageSize) throws Exception;
}
