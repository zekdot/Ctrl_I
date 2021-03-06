package com.ctrl_i.springboot.service.impl;

import com.ctrl_i.springboot.dao.ArticleDao;
import com.ctrl_i.springboot.dao.ReadDao;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.ArticleEntity;
import com.ctrl_i.springboot.entity.ReadEntity;
import com.ctrl_i.springboot.entity.ReadEntityPK;
import com.ctrl_i.springboot.service.ReadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
@Service
public class ReadServiceImpl implements ReadService {
    @Resource
    ReadDao readDao;
    @Resource
    private ArticleDao articleDao;
    @Override
    public Envelope rateWhileRead(String uid, int aid, double rate) {

        ReadEntityPK ua=new ReadEntityPK();
        ua.setuId(uid);
        ua.setaId(aid);
        //System.out.println("new entity");
        try {
            ReadEntity readEntity=readDao.get(ua);
            if (readEntity==null){
                readEntity=new ReadEntity();

                readEntity.setaId(aid);
                readEntity.setuId(uid);
                readEntity.setTime(new Timestamp(System.currentTimeMillis()));
                readEntity.setRate(rate);
                readDao.save(readEntity);
                try {
                    // 读取文章
                    ArticleEntity articleEntity = articleDao.get(aid);
                    // 阅读数加一
                    articleEntity.setReadNum(articleEntity.getReadNum()+1);
                    // 更新文章阅读数
                    articleDao.update(articleEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Envelope.dbError;
                }
                return Envelope.success;
            }
            double oldRate=readEntity.getRate();
            readEntity.setRate((oldRate+rate)/2.0);
            readDao.saveOrUpdate(readEntity);
            return Envelope.success;
        } catch (Exception e) {

            return Envelope.dbError;
        }

    }
}
