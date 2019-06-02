package com.ctrl_i.springboot.service;

import com.ctrl_i.springboot.ConfigConstant;
import com.ctrl_i.springboot.dto.Envelope;

/**
 * Create by zekdot on 19-4-13.
 */
public interface ArticleService {
    public static String PIC_URL="http://"+ ConfigConstant.ip+":"+ConfigConstant.fport+ "/images/"; //文章的图片URL
    public static int WORD_LIMIT = 70;    //缩略文字数
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
