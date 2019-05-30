package com.ctrl_i.springboot.service.impl;
import com.ctrl_i.springboot.dao.ArticleDao;
import com.ctrl_i.springboot.dao.ReadDao;
import com.ctrl_i.springboot.dao.UserDao;
import com.ctrl_i.springboot.dto.Envelope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * 基于用户的协同过滤推荐算法实现
 * @author Jemary
 *
 */
@Service
public class RecommendServiceImpl {

    @Resource
    UserDao userDao;
    ReadDao readDao;
    ArticleDao articleDao;
    public Envelope recommend() {
        /**
         * UID	     Article ID
         * 'str'	   1  2 3
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the total users number:");

        //输入用户总量
        String [] users= new String[0];  // TODO:从数据库获取用户列表
        try {
            users = userDao.getUserArray();
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }

        int N = users.length;    // 用户总数
        int[][] sparseMatrix = new int[N][N];//建立用户矩阵，用于用户相似度计算【相似度矩阵】
        HashMap<String, HashMap<Integer, Double>> userScore = null;  //用户 文章 评分存储  TODO：数据库中获取
        try {
            userScore = readDao.getUserScore();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Integer> userArticleLength = new HashMap<>();//存储每一个用户阅读的文章总数  eg: '用户1' 3
        Map<Integer, Set<String>> articleUserCollection = new HashMap<>();//建立文章到用户的倒排表 eg: 1 '用户1' '用户2'
        Set<Integer> articles = new HashSet<>();//辅助存储文章集合
        Map<String, Integer> userID = new HashMap<>();//辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();//辅助存储每一个ID对应的用户映射


        for(int i = 0; i < N ; i++){//依次处理N个用户
            Integer[] user_article={1,2,3};  // TODO: 从数据库获取当前用户的文章列表
            for(Integer article:user_article){
                double score = 10; //TODO: 获取到每个用户对每个文章的评分
                userScore.get(users[i]).put(article,score);
            }
            int length=user_article.length;
            userArticleLength.put(users[i], length);//eg: A 3
            userID.put(users[i], i);//用户ID与稀疏矩阵建立对应关系
            idUser.put(i, users[i]);
            //建立文章--用户倒排表
            for(int j = 1; j < length; j ++){
                if(articles.contains(user_article[j])){//如果已经包含对应的文章--用户映射，直接添加对应的用户
                    articleUserCollection.get(user_article[j]).add(users[i]);
                }else{//否则创建对应物文章--用户集合映射
                    articles.add(user_article[j]);
                    articleUserCollection.put(user_article[j], new HashSet<String>());//创建文章--用户倒排关系
                    articleUserCollection.get(user_article[j]).add(users[i]);
                }
            }
        }
        System.out.println(articleUserCollection.toString());
        //计算相似度矩阵【稀疏】
        Set<Entry<Integer, Set<String>>> entrySet = articleUserCollection.entrySet();
        Iterator<Entry<Integer, Set<String>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {
                for (String user_v : commonUsers) {
                    if(user_u.equals(user_v)){
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;//计算用户u与用户v共同阅读的文章总数
                }
            }
        }
        System.out.println(userArticleLength.toString());

        String recommendUser = "A USER"; // TODO:网页逻辑 获取当前用户ID
        System.out.println(userID.get(recommendUser));
        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userID.get(recommendUser);
        for (int j = 0;j < sparseMatrix.length; j++) {
            if(j != recommendUserId){
                System.out.println(idUser.get(recommendUserId)+"--"+idUser.get(j)+"相似度:"+sparseMatrix[recommendUserId][j]
                        /Math.sqrt(userArticleLength.get(idUser.get(recommendUserId))*userArticleLength.get(idUser.get(j))));
            }
        }

        //计算指定用户recommendUser的文章推荐度
        for(Integer article: articles){//遍历每一篇文章
            Set<String> curUsers = articleUserCollection.get(article);//得到阅读过当前文章的所有用户集合
            if(!curUsers.contains(recommendUser)){//如果被推荐用户没读过，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for(String user: curUsers){
                    itemRecommendDegree += userScore.get(user).get(article) * sparseMatrix[userID.get(recommendUser)][userID.get(user)]
                            /Math.sqrt(userArticleLength.get(recommendUser)*userArticleLength.get(user));// 用户对文章评分 * 用户相似度
                }
                System.out.println("The article "+article+" for "+recommendUser +"'s recommended degree:"+itemRecommendDegree);
            }
        }
        scanner.close();
        return null;
    }
}

