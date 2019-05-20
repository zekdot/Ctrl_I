package com.ctrl_i.springboot.dao.impl;

import com.ctrl_i.springboot.dao.CommentsDao;
import com.ctrl_i.springboot.entity.CommentsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by zekdot on 19-5-20.
 */
@Repository
public class CommentsDaoImpl  extends BaseDaoImpl<Integer, CommentsEntity> implements CommentsDao {
    @Override
    public List<CommentsEntity> getCommentByaId(int aId,int lastjId,int pageSize) throws Exception {
        StringBuilder hqlBuilder = new StringBuilder().append("from CommentsEntity where aId = ? ");
        Object params[];
        // 为0获取全部数据
        if(lastjId != 0){
            params = new Object[2];
            // 增加评论id的限制
            hqlBuilder.append("and jId < ? ");
            // 赋值
            params[1] = lastjId;
        }else{
            params = new Object[1];
        }
        // 设定文章id
        params[0] = aId;
        hqlBuilder.append("order by jId desc");
        return executeHQL(hqlBuilder.toString(),params,0,pageSize);
    }
}
