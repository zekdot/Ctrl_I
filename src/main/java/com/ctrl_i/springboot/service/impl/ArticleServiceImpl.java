package com.ctrl_i.springboot.service.impl;

import com.ctrl_i.springboot.dao.ArticleDao;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.ArticleEntity;
import com.ctrl_i.springboot.service.ArticleService;
import com.ctrl_i.springboot.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create by zekdot on 19-4-13.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;
    private static int PAGE_SIZE=18;   //每页18条
    private static String PIC_URL="images/"; //文章的图片URL
    @Override
    public Envelope getArticleList(int lastId) {
        List<ArticleEntity> list;
        try {
            list=articleDao.getOnePageArticleAfterId(lastId,PAGE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        if(list.size()==0){
            return new Envelope(1,"没有更多文章！",null);
        }
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject;
        for(ArticleEntity articleEntity:list){
            jsonObject=new JSONObject();
            jsonObject.put("aId",articleEntity.getaId());   //文章id
            jsonObject.put("author",articleEntity.getAuthor()); //文章作者
            jsonObject.put("cover",PIC_URL+articleEntity.getCover());   //文章封面
            jsonObject.put("title",articleEntity.getTitle());   //文章标题
            jsonObject.put("cont",articleEntity.getContent().substring(0,100));
            jsonObject.put("time", DateUtil.dateTime2Str(articleEntity.getTime())); //文章时间
            jsonObject.put("readNum",articleEntity.getReadNum());   //阅读数
            jsonArray.add(jsonObject);
        }
        return new Envelope(jsonArray);
    }

    @Override
    public Envelope getArticleById(int id) {
        ArticleEntity articleEntity;
        try {
            articleEntity=articleDao.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        if(articleEntity==null){
            return new Envelope(1,"没有找到该文章!",null);
        }
        articleEntity.setCover(PIC_URL+articleEntity.getCover());
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return new Envelope(gson.toJson(articleEntity));    //返回序列化的文章内容
    }


    public static void main(String args[]){

        //System.out.println(gson.toJson(new Date()));
    }
}
