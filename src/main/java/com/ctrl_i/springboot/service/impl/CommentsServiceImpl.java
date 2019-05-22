package com.ctrl_i.springboot.service.impl;

import com.ctrl_i.springboot.dao.CommentsDao;
import com.ctrl_i.springboot.dao.UserDao;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.CommentsEntity;
import com.ctrl_i.springboot.entity.UserEntity;
import com.ctrl_i.springboot.service.CommentsService;
import com.ctrl_i.springboot.util.DateUtil;
import com.ctrl_i.springboot.util.WebFormatUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    @Resource
    private UserDao userDao;
    @Override
    public Envelope commentArticle(int aId, String uId, String comment) {
        if(comment.length() > 200){
            return new Envelope(1,"文字篇幅过长",null);
        }
        CommentsEntity commentsEntity =new CommentsEntity();
        commentsEntity.setuId(uId);
        commentsEntity.setaId(aId);
        // 将文本转义
        comment = WebFormatUtil.txtToHtml(comment);
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
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject;
            List<CommentsEntity> list = commentsDao.getCommentByaId(aId,lastId,PAGE_SIZE);
            for(CommentsEntity commentsEntity:list){
                jsonObject = new JSONObject();
                jsonObject.put("uId",commentsEntity.getuId());
                jsonObject.put("jId",commentsEntity.getjId());
                jsonObject.put("content",commentsEntity.getContent());
                jsonObject.put("time", DateUtil.dateTime2Str(commentsEntity.getTime()));
                UserEntity userEntity = userDao.get(commentsEntity.getuId());
                if(userEntity == null){
                    jsonObject.put("nickname","用户已注销");
                }else{
                    jsonObject.put("nickname",userEntity.getNickname());
                }
                jsonArray.add(jsonObject);
            }
            return new Envelope(jsonArray);
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
