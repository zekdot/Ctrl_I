package com.ctrl_i.springboot.service.impl;

import com.ctrl_i.springboot.dao.CommentsDao;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.CommentsEntity;
import com.ctrl_i.springboot.service.CommentsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Create by zekdot on 19-5-20.
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    private static int PAGE_SIZE = 20;
    @Resource
    private CommentsDao commentsDao;
    @Override
    public Envelope commentArticle(int aId, String uId, String comment) {
        if(comment.length() > 200){
            return new Envelope(1,"文字篇幅过长",null);
        }
        CommentsEntity commentsEntity =new CommentsEntity();
        commentsEntity.setuId(uId);
        commentsEntity.setaId(aId);
        commentsEntity.setContent(comment);
        commentsEntity.setTime(new Timestamp(new Date().getTime()));
        try {
            commentsDao.save(commentsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        return new Envelope(commentsEntity.getjId());
    }

    @Override
    public Envelope getCommentsByaId(int aId,int lastId) {
        try {
            List<CommentsEntity> list = commentsDao.getCommentByaId(aId,lastId,PAGE_SIZE);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            return new Envelope(gson.toJson(list));
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
    }

    @Override
    public Envelope deleteComment(int jId, String uId) {
        CommentsEntity commentsEntity = null;
        try {
            commentsEntity = commentsDao.get(jId);
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        // 如果已经删除了
        if(commentsEntity == null){
            return Envelope.success;
        }
        // 权限检查
        if(!commentsEntity.getuId().equals(uId)){
            return new Envelope(1,"您不能删除他人的评论",null);
        }
        try {
            commentsDao.delete(commentsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        return Envelope.success;
    }
}
