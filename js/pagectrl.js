window.curLastId = 0;  //当前的最后一篇文章的id，用于翻页
/**
* 跳转到某一文章页
* @param id
*/
function jumpToArt(id){
    //window.location.href='article.html?id='+id;
    $("#content").load('article.html')
    window.article_id = id;
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