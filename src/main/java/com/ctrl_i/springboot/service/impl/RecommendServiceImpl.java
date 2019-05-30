package com.ctrl_i.springboot.service.impl;
import com.ctrl_i.springboot.dao.ArticleDao;
import com.ctrl_i.springboot.dao.ReadDao;
import com.ctrl_i.springboot.dao.RecommendDao;
import com.ctrl_i.springboot.dao.UserDao;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.ArticleEntity;
import com.ctrl_i.springboot.entity.ReadEntity;
import com.ctrl_i.springboot.entity.RecommendEntity;
import com.ctrl_i.springboot.service.ArticleService;
import com.ctrl_i.springboot.service.RecommendService;
import com.ctrl_i.springboot.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

/**
 * 基于用户的协同过滤推荐算法实现
 * @author Jemary
 *
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    private static int K = 6;
    class ArtDeg{
        int artId;  // 文章id
        double degree;  //相似度
        ArtDeg(int artId,double degree){
            this.artId = artId;
            this.degree = degree;
        }
    }
    @Resource
    UserDao userDao;
    @Resource
    ReadDao readDao;
    @Resource
    private RecommendDao recommendDao;
    @Resource
    ArticleDao articleDao;

    public Envelope recommend() {
        /**
         * UID	     Article ID
         * 'str'	   1  2 3
         */
        //Scanner scanner = new Scanner(System.in);
        System.out.println("Input the total users number:");

        // 输入用户总量
        //String [] users = null;  // TODO:从数据库获取用户列表 done
        List<String> users = null;
        try {
            // 从数据库中获取用户id列表
            users = readDao.getUserId();//userDao.getUserArray();
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        // 获取用户总数
        int N = users.size();
        //建立用户矩阵，用于用户相似度计算【相似度矩阵】
        int[][] sparseMatrix = new int[N][N];
        //用户 文章 评分存储  TODO：数据库中获取 done
        HashMap<String, HashMap<Integer, Double>> userScore = null;
        try {
            userScore = readDao.getUserScore();
        } catch (Exception e) {
            e.printStackTrace();
            return  Envelope.dbError;
        }
        //存储每一个用户阅读的文章总数  eg: '用户1' 3
        Map<String, Integer> userArticleLength = new HashMap<>();
        //建立文章到用户的倒排表 eg: 1 '用户1' '用户2'
        Map<Integer, Set<String>> articleUserCollection = new HashMap<>();
        //辅助存储文章集合
        Set<Integer> articles = new HashSet<>();
        //辅助存储每一个用户的用户ID映射
        Map<String, Integer> userID = new HashMap<>();
        //辅助存储每一个ID对应的用户映射
        Map<Integer, String> idUser = new HashMap<>();
        //int count = 0;
        //依次处理N个用户
        for(int i = 0; i < N ; i++){
            // 如果用户还没有读过任何文章
            if(userScore.get(users.get(i)) == null){
                // 跳过
                continue;
            }
            // TODO: 从数据库获取当前用户的文章列表 done
            Object[] user_article= userScore.get(users.get(i)).keySet().toArray();
            //TODO: 获取到每个用户对每个文章的评分 done
            for(Object article:user_article){
                double score = userScore.get(users.get(i)).get(article);
                userScore.get(users.get(i)).put((Integer) article,score);
            }
            int length=user_article.length;
            //eg: A 3
            userArticleLength.put(users.get(i), length);//eg: A 3
            //用户ID与稀疏矩阵建立对应关系
            userID.put(users.get(i), i);
            idUser.put(i, users.get(i));
            //建立文章--用户倒排表
            for(int j = 1; j < length; j ++){
                //如果已经包含对应的文章--用户映射，直接添加对应的用户
                if(articles.contains(user_article[j])){
                    articleUserCollection.get(user_article[j]).add(users.get(i));
                }else{//否则创建对应物文章--用户集合映射
                    articles.add((Integer) user_article[j]);
                    articleUserCollection.put((Integer) user_article[j], new HashSet<String>());//创建文章--用户倒排关系
                    articleUserCollection.get(user_article[j]).add(users.get(i));
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
        System.out.println(users.toString());
        // users为全部用户id
        // 遍历所有用户id
        for(String recommendUser:users){
            // 用于保存 文章id--相似度
            List<ArtDeg> artDegs = new ArrayList<>();
            //String recommendUser = "A USER"; // TODO:网页逻辑 获取当前用户ID
            System.out.println(userID.get(recommendUser));
            System.out.println(recommendUser);
            if(userID.get(recommendUser) == null){
                continue;
            }
            //计算用户之间的相似度【余弦相似性】
            int recommendUserId = userID.get(recommendUser);
            for (int j = 0;j < sparseMatrix.length; j++) {
                if(j != recommendUserId){
                    //if(userID)
                    System.out.println(idUser.toString());
                    System.out.println("______idUser get" + idUser.get(j));
                    System.out.println(idUser.get(recommendUserId)+
                            "--"+idUser.get(j)+"相似度:"
                            +sparseMatrix[recommendUserId][j]
                            /Math.sqrt(userArticleLength.get(idUser.get(recommendUserId))
                            *userArticleLength.get(
                                    idUser.get(j))));

                }
            }

            //计算指定用户recommendUser的文章推荐度
            for(Integer article: articles){//遍历每一篇文章
                Set<String> curUsers = articleUserCollection.get(article);//得到阅读过当前文章的所有用户集合
                if(!curUsers.contains(recommendUser)){//如果被推荐用户没读过，则进行推荐度计算
                    double itemRecommendDegree = 0.0;
                    for(String user: curUsers){
                        System.out.println("cur user is "+user);
                        System.out.println(userArticleLength.get(recommendUser));
                        //System.out.println(Integer.valueOf(sparseMatrix[userID.get(recommendUser)]));
                        System.out.println(userArticleLength.get(user));
                        itemRecommendDegree += userScore.get(user).get(article) * sparseMatrix[userID.get(recommendUser)][userID.get(user)]
                                /Math.sqrt(userArticleLength.get(recommendUser)*userArticleLength.get(user));// 用户对文章评分 * 用户相似度
                    }
                    artDegs.add(new ArtDeg(article,itemRecommendDegree));

                }
            }

            // 排序
            Collections.sort(artDegs, new Comparator<ArtDeg>() {
                @Override
                public int compare(ArtDeg o1, ArtDeg o2) {
                    return (o1.degree - o2.degree < 0 ? 1 : -1);
                }
            });
            JSONArray jsonArray = new JSONArray();
            for(ArtDeg artDeg : artDegs.subList(0,6 < artDegs.size() ? 6 : artDegs.size())){
                // 放置一个文章id
                jsonArray.add(artDeg.artId);
//                System.out.println("The article "+artDeg.artId+" for "+recommendUser +"'s recommended degree:"+artDeg.degree);
            }
            RecommendEntity recommendEntity = new RecommendEntity();
            recommendEntity.setDate(new java.sql.Date(new Date().getTime()));
            recommendEntity.setuId(recommendUser);
            recommendEntity.setContent(jsonArray.toString());
            try {
                recommendDao.saveOrUpdate(recommendEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return Envelope.dbError;
            }
        }
        //scanner.close();
        return Envelope.success;
    }
    /**
     * 获取文字内容
     * @param content 内容
     * @return 文字内容
     */
    private String getTextContent(String content){
        int pIndex = content.indexOf("<p>");
        if(pIndex == -1)
            return content;
        return content.substring(pIndex);
    }
    @Override
    public Envelope getTodayRecommend(String uId) {
        RecommendEntity recommendEntity;
        try {
            // 首先从推荐数据库中抽取出推荐的JSONArray
            recommendEntity = recommendDao.get(uId);
        } catch (Exception e) {
            e.printStackTrace();
            return Envelope.dbError;
        }
        // 获取抽取出的列表
        JSONArray jsonArray = JSONArray.fromObject(recommendEntity.getContent());
        // 新建list用于存放文章列表
        List<ArticleEntity> articles = new ArrayList<>();
        // 首先获取推荐的
        for(int i = 0;i < jsonArray.size(); i++){
            try {
                ArticleEntity articleEntity = articleDao.get(jsonArray.getInt(i));
                if(articleEntity != null)
                    articles.add(articleEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return Envelope.dbError;
            }
        }
        // 剩下的不足的从最新文章挑几篇
        if(articles.size() < K) {
            try {
                articles.addAll(articleDao.getOnePageArticleAfterId(0, K - articles.size()));
            } catch (Exception e) {
                e.printStackTrace();
                return Envelope.dbError;
            }
        }
        JSONArray resArray=new JSONArray();
        JSONObject jsonObject;
        for(ArticleEntity article:articles){
            jsonObject=new JSONObject();
            jsonObject.put("aId",article.getaId());   //文章id
            jsonObject.put("author",article.getAuthor()); //文章作者
            if(article.getCover() == null || article.getCover().equals("")){    //如果没有封面
                jsonObject.put("cover", ArticleService.PIC_URL+"noCover.png");
            }else{
                jsonObject.put("cover",article.getCover());   //文章封面
            }
            jsonObject.put("type",article.getType());    //文章类型
            jsonObject.put("title",article.getTitle());   //文章标题
            String content = getTextContent(article.getContent());
            jsonObject.put("cont",content.substring(0,ArticleService.WORD_LIMIT > content.length() ? content.length() : ArticleService.WORD_LIMIT));
            jsonObject.put("time", DateUtil.dateTime2Str(article.getTime())); //文章时间
            jsonObject.put("readNum",article.getReadNum());   //阅读数
            resArray.add(jsonObject);
        }
        return new Envelope(resArray);
    }
}

