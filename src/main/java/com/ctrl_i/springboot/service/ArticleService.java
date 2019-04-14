package com.ctrl_i.springboot.service;

import com.ctrl_i.springboot.dto.Envelope;

/**
 * Create by zekdot on 19-4-13.
 */
public interface ArticleService {
    /**
     * 获取文章列表
     * @param lastId 上一页最后一条的id
     * @return
     */
    public Envelope getArticleList(int lastId);

    /**
     * 根据id获取文章详情
     * @param id 文章id
     * @return
     */
    public Envelope getArticleById(int id);
}
