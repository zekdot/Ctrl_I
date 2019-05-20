package com.ctrl_i.springboot.dao;

import com.ctrl_i.springboot.entity.CommentsEntity;

import java.util.List;

/**
 * Create by zekdot on 19-5-20.
 */
public interface CommentsDao extends BaseDao<Integer, CommentsEntity> {
    /**
     * 根据aId获取评论id小于某个值的列表
     * @param aId 文章id
     * @param lastjId 最后一条评论id
     * @param pageSize 页数长度
     * @return 评论列表
     */
    public List<CommentsEntity> getCommentByaId(int aId,int lastjId,int pageSize) throws Exception;
}
