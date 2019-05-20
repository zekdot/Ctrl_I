package com.ctrl_i.springboot.controller;

import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.UserEntity;
import com.ctrl_i.springboot.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Create by zekdot on 19-5-21.
 */
@Controller
@CrossOrigin
@RequestMapping("/comment")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    /**
     * 评论一篇文章
     * @param aId 文章id
     * @param content 内容
     * @param session 保存了用户信息
     * @return 结果字符串
     */
    @RequestMapping(value = "/comArt",method = RequestMethod.POST)
    @ResponseBody
    public String commentArticle(int aId,String content,HttpSession session){
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        try{
            return commentsService.commentArticle(aId,content,userEntity.getuId()).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }

    /**
     * 根据文章id获取评论列表
     * @param aId 文章id
     * @param lastId 当前最后一条id
     * @return 评论列表
     */
    @RequestMapping("/getComByaId")
    @ResponseBody
    public String getCommentListByaId(int aId,@RequestParam(defaultValue = "0") int lastId){
        try{
            return commentsService.getCommentsByaId(aId,lastId).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }

    /**
     * 根据评价id删除评价
     * @param jId 评价id
     * @param session 用户信息
     * @return 结果字符串
     */
    @RequestMapping(value = "/delComByjId",method = RequestMethod.POST)
    @ResponseBody
    public String deleteCommentByjId(int jId,HttpSession session){
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        try{
            return commentsService.deleteComment(jId,userEntity.getuId()).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }
}
