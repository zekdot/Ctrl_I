window.curLastId = 0;  //当前的最后一篇文章的id，用于翻页
window.curLevel = 0;    //当前页面层级 0-文章列表页面 1-文章具体页面
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
    $("#content").load('list.html');
    // 设置当前层级位于文章列表页面
    window.curLevel = 0;
    // 获取文章块对应的到顶部的距离
    var art = document.getElementById('a_' + id);
    // 获取距离
    var point = getPoint(art);
    // 滑动到对应的位置
    $('html,body').scrollTop(point);
}
/**
 * 将获取的文章json填充到页面当中去
 * @param str 文章json
 */
function putArt(str){
    //alert(str)
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
        var arts = data.object; //拿出所有文章
        var cols = [[],[],[]];  //三列
        var colIndex = 0;
        for(var i=0; i<arts.length ;i++){
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

}
/**
 * 得到下一页文章列表
 * @param lastId
 */
function getNextPage(lastId){
    //alert(lastId)
    $.post('http://localhost:8082/article/list',{'lastId':lastId},putArt);
}