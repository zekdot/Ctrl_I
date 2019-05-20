package com.ctrl_i.springboot.service;

import com.ctrl_i.springboot.dto.Envelope;

/**
 * Create by zekdot on 19-5-20.
 */
public interface CommentsService {

    /**
     * 评论一篇文章
     * @param aId 文章id
     * @param uId 用户id
     * @param comment 内容
     * @return 评论结果
     */
    public Envelope commentArticle(int aId,String uId,String comment);

    /**
     * 根据文章id获取评论列表
     * @param aId 文章id
     * @param lastId 最后一条id
     * @return 评论列表
     */
    public Envelope getCommentsByaId(int aId,int lastId);

    /**
     * 根据评价id删除评论
     * @param jId 评论id
     * @param uId 用户id
     * @return 删除结果
     */
    public Envelope deleteComment(int jId,String uId);
}
