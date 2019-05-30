package com.ctrl_i.springboot.service;

import com.ctrl_i.springboot.dto.Envelope;

/**
 * Create by zekdot on 19-5-30.
 */
public interface RecommendService {
    /**
     * 给每一个用户推荐文章
     * @return
     */
    public Envelope recommend();

    /**
     * 获取今日推荐
     * @param uId 用户id
     * @return 今日推荐列表
     */
    public Envelope getTodayRecommend(String uId);
}
