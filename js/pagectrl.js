window.curLastId = 0;  //当前的最后一篇文章的id，用于翻页
window.curLevel = 0;    //当前页面层级 0-文章列表页面 1-文章具体页面
window.historyTop = 0;  //保存历史的滑动距离
window.myFavArts = [];  //保存我喜欢的文章
window.historyArts = [];    //保存历史的文章数据
window.curUserInfo = null;  //用户信息对象
window.curUserCookie = null;    //用户的cookies

window.curArticleId = 0; // 当前读取的文章id
window.curArticleType = null;   //当前读取的文章类型

jQuery(document).ready(function($) {

    var curUserInfoStr = getCookie("userInfo");
    window.curUserCookie = getCookie('loginCoo');
    //console.log(curUserInfoStr)
    //如果用户已经登录了
    if(curUserInfoStr != null){
        // 设置用户信息结构体
        window.curUserInfo = eval('(' + curUserInfoStr + ')').object;
        // href="profile.html"
        $('#user_div').html('<svg t="1559392881141" class="icon" style="fill: white;position: relative; top: 3px;" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2005" xmlns:xlink="http://www.w3.org/1999/xlink" width="16" height="16"><defs><style type="text/css"></style></defs><path d="M984.615385 846.769231v43.323077c0 51.2-43.323077 94.523077-94.523077 94.523077H133.907692C82.707692 984.615385 39.384615 941.292308 39.384615 890.092308V846.769231c0-114.215385 133.907692-185.107692 259.938462-240.246154l11.815385-5.907692c9.846154-3.938462 19.692308-3.938462 29.538461 1.96923 51.2 33.476923 108.307692 51.2 169.353846 51.2s120.123077-19.692308 169.353846-51.2c9.846154-5.907692 19.692308-5.907692 29.538462-1.96923l11.815385 5.907692C850.707692 661.661538 984.615385 730.584615 984.615385 846.769231zM512 39.384615c129.969231 0 234.338462 116.184615 234.338462 259.938462S641.969231 559.261538 512 559.261538s-234.338462-116.184615-234.338462-259.938461S382.030769 39.384615 512 39.384615z" p-id="2006"></path></svg><p class="user_text">欢迎您，</p><a style="cursor:pointer;" onclick="userCenter()" class="user_text">' + window.curUserInfo.nickname + '</a><a onclick="logout()" style="cursor:pointer;" class="small_user_text">| 登出</a>')
        //$('#user_div').html('<p class = "user_text">欢迎您，</p><a style="cursor:pointer;" onclick="userCenter()" class = "user_text">' + window.curUserInfo.nickname + '</a><span></span><a onclick="logout()" style="cursor:pointer;" class = "small_user_text">登出</a>')
    }
    //<button type="button" class="btn btn-primary btn-lg"><span class = "glyphicon glyphicon-user"></span> User</button>
            //putArt('{"code":0,"message":"success","object":[{"aId":2,"author":"张兴旺","cover":"images/b2.jpg","title":"爱情是什么","cont":"在这个世上，只要不是完全的独身主义者，爱","time":"2019-04-13 16:20:07","readNum":0},{"aId":1,"author":"孟婷1234","cover":"images/b1.jpg","title":"三十多岁，如何打理尴尬年龄里的凌乱人生","cont":"三十岁是一个分水岭，一边是骄阳似火、山花","time":"2019-04-13 16:18:28","readNum":0}]}')
});

/**
 * 进入个人中心
 *
 */
function userCenter(){
    $("#content").load('profile.html')
}


/**
 * 注销登录状态
 *
 */
function logout(){
    // 清除登录相关的cookies
    deleteCookie("userInfo");
    deleteCookie("loginCoo");
    window.location.href = window.fronthost + ':8081/index.html';
}

//window.curReadId = -1;  //当前阅读的文章id
/**
 * 获取某元素以浏览器左上角为原点的坐标
 * @param 元素
 * @return JSON对象，t--最左坐标,l--最右坐标
 */
function getPoint(obj) { 
    // 获取该元素对应父容器的上边距
    var t = obj.offsetTop; 
    // 对应父容器的上边距
    var l = obj.offsetLeft; 
    // 判断是否有父容器，如果存在则累加其边距
    while (obj = obj.offsetParent) {//等效 obj = obj.offsetParent;while (obj != undefined)
        // 叠加父容器的上边距
        t += obj.offsetTop; 
        // 叠加父容器的左边距
        l += obj.offsetLeft; 
    }
    // 返回计算结果
    return {'t':t,'l':l};
}
/**
* 跳转到某一文章页
* @param id
*/
function jumpToArt(id){
    //window.location.href='article.html?id='+id;
    // 保存历史位置
    cursor.historyTop = cursor.top;
    cursor.historyLeft = cursor.left;
    // 移动光标到左上角
    cursor.top = 0;
    cursor.left = 0;
    $('#target').css('top', cursor.top + 'px');
    $('#target').css('left', cursor.left + 'px');

    window.historyTop = $(window).scrollTop();
    // 清空滑动距离
    $('html,body').scrollTop(0);   
    // 载入文章HTML
    $("#content").load('article.html')
    //设置当前文章id
    window.articleId = id; 
    //设置当前层级位于文章具体页面
    window.curLevel = 1;    


    //window.curReadId = id; //设置当前的阅读id
}
/**
 * 返回文章列表对应锚点处
 * @param 文章id
 */
function backArtList(id){

    // 载入文章列表HTML
    $("#content").load('list.html')
        // 设置当前层级位于文章列表页面
        window.curLevel = 0;
        //alert(id);
        // console.log(id)
        // // 获取文章块对应的到顶部的距离
        // var art = document.getElementById('a_' + id);
        // console.log(art)
        // alert(art)
        // // 获取距离
        // var point = getPoint(art);
        // console.log(point)
        // console.log(point.t)
        // 载入历史位置
        cursor.top = cursor.historyTop;
        cursor.left = cursor.historyLeft;
        $('#target').css('top', cursor.top + 'px');
        $('#target').css('left', cursor.left + 'px');
            //alert(window.historyTop)
            // 滑动到对应的位置
            //console.log(window.historyTop)
        var timer = setInterval(function(){
            $('html,body').scrollTop(window.historyTop);
            //alert('fuckf')
            clearInterval(timer);
        },1000)
            
            //alert($('html,body').scrollTop())
        


}


/**
 * 将获取的文章列表填充到页面当中去
 * @param str 文章列表
 */
function putArt(arts,saved = true){
    //alert(str)
    //var arts = data.object; //拿出所有文章
    var cols = [[],[],[]];  //三列
    var colIndex = 0;
    for(var i=0; i<arts.length ;i++){
        // 如果要把此次的数据加进历史列表
        if(saved)
            // 加入数据
            historyArts.push(arts[i]);
        cols[colIndex].push(arts[i]);
        colIndex++;
        colIndex %= 3;
    }
                    //console.log(cols);
                    //$('#blog-posts-section').append();
    var wrap = $('<div class="blog-post-grids blog_grids"></div>');
                //console.log($('#blog-posts-section'));
    $('#blog-posts-section').append(wrap);
    for(var i = 0;i < 3; i++){
        var nodes = $('<div class="col-md-4 blog-posts"></div>');
        if(i == 2){
            nodes = $('<div class="col-md-4 blog-posts span_66"></div>');
        }
        wrap.append(nodes);
        for(var j = 0;j < cols[i].length; j++){
                    //console.log(cols[i][j])
            var content=$('<div class="blog-post"  id="a_'+cols[i][j].aId+'" onclick="jumpToArt('+cols[i][j].aId+')" ></div>');
            window.curLastId = cols[i][j].aId; //保存当前最后一条id
                //console.log(cols[i][j].aId)
                //alert('fuck')
            nodes.append(content);
            content.append('<a><img src="'+cols[i][j].cover+'"></a>')
            content.append('<a class="blog-title">'+cols[i][j].title+'</a>')
            content.append('<p class="sub_head">Posted by <a>'+cols[i][j].author+'</a> on '+cols[i][j].time+'</p>')
            content.append('<span></span>')
                    //content.append('<p>'+cols[i][j].cont+'...</p>')
            content.append('<p>'+cols[i][j].cont+'...</p>')
            content.append('<div class="read_num">阅读量:'+cols[i][j].readNum+'</div>')
                        
                            //console.log(content)
        }
                        
                //console.log(content)
    }
    $('#blog-posts-section').append('<div class="clearfix"></div>');
    $('#blog-posts-section').append('<div class="right_column">');
                    // console.log(wrap)
                    // //console.log(cols[1])
                    
                    // for(var i=0;i<arts.length;i++){
                    //     if(i%3==0){
                    //         if(i!=0)
                    //             $('#blog-posts-section').append(nodes)
                            
                    //     }
                    //     var node=$('<div class="col-md-4 blog-posts" id="a_'+arts[i].aId+'" onclick="jumpToArt('+arts[i].aId+')"></div>')
                        

                        
                    //     node.append(content);
                    //     nodes.append(node)
                    // }
                    // //alert(node)
                    // $('#blog-posts-section').append(nodes)
                    // nodes=$('<div class="blog-post-grids blog_grids></div>')

}
// 绑定滚动事件
window.onscroll = function(){
    //已经滚动到上面的页面高度
    var scrollTop = $(window).scrollTop();
    //页面高度
    var scrollHeight = $(document).height();
    //浏览器窗口高度
    var windowHeight = $(window).height();
    // 如果是文章列表层级
    if(window.curLevel == 0){
         //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
         if (scrollTop + windowHeight == scrollHeight) {
            //没有更多文章
            if(window.curLastId == -1){
                ;   
            }else{
                //获取下一页的数据
                getNextPage(window.curLastId); 
            }
        }
    }
};
/**
 * 处理向后台请求数据之后的回调函数
 * @str 字符串
 */
function getPageCallBack(str){
    var data=eval('('+str+')');
    if(data.code!=0){
        //
        if(data.code == 1){
            window.curLastId = -1;
            alert(data.message);
        }else{
            alert(data.message);
        }
        return;
    }else{
        putArt(data.object)
    }

}

// function getFavArtsCallBack(str){
//     var data=eval('('+str+')');
//     if(data.code!=0){
//         //
//         if(data.code == 1){
//             window.curLastId = -1;
//             alert(data.message);
//         }else{
//             alert(data.message);
//         }
//         return;
//     }else{
//         putArt(data.object)
//     }   
// }
/**
 * 获取可能喜欢的文章
 *
 */
function getFavouriteArts(){
    // 如果已经缓存了历史内容
    if(window.myFavArts.length > 0){
        //alert('已有猜您喜欢')
        //直接放置页面
        putArt(window.myFavArts,false);
        $('#blog-posts-section').append('<div class="head-section text-center"><h2>资讯列表</h2><span> </span></div>');
        getNextPage(0)
    }else{
        //alert('加载猜您喜欢')
        // 向后台请求数据
        document.cookie = 'JSESSIONID=' + window.curUserCookie;
            //console.log(document.cookie)

        $.ajax({
            url : window.backhost + ':8082/recommend/todayRec',
            dataType: 'json',
            type : 'GET',
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,

            success: function(res) {
                // alert(str)
                // // 将返回值转换为json对象
                // res = eval('(' + str + ')');
                // 如果获取成功
                if(res.code == 0){
                    // 放置历史
                    window.myFavArts = res.object;
                    putArt(myFavArts,false);
                    $('#blog-posts-section').append('<div class="head-section text-center"><h2>资讯列表</h2><span> </span></div>');
                    getNextPage(0)
                }else{

                    alert(res.message);
                }
            }
        })
    }
}
/**
 * 得到下一页文章列表
 * @param lastId
 */
function getNextPage(lastId){
    //alert(lastId)
    //console.log('lastId is '+lastId+' window.historyArts.length '+window.historyArts.length)
    // 如果请求第一页并且已经有缓存了
    if(lastId == 0 && window.historyArts.length > 0){
        putArt(window.historyArts,false);

    }else{
        //if(window.historyArts.length > 0)
        //    return;
        $.post(window.backhost + ':8082/article/list',{'lastId':lastId},getPageCallBack);        
    }

}
