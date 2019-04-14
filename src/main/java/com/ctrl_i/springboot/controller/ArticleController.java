package com.ctrl_i.springboot.controller;

import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by zekdot on 19-4-13.
 */
@Controller
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /**
     * 获取一页文章列表
     * @param lastId 最后一条文章id
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String getArticleList(@RequestParam(name = "lastId",defaultValue = "0") int lastId){
        try{
            return articleService.getArticleList(lastId).toString();
        }catch (Exception e){
            return Envelope.systemError.toString();
        }
    }

    /**
     * 获取文章详情
     * @param id 文章id
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public String getArticleInfo(int id){
        try{
            return articleService.getArticleById(id).toString();
        }catch (Exception e){
            e.printStackTrace();
            return Envelope.systemError.toString();
        }
    }
}
