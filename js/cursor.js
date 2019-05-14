window.cursor = {
    left: 0,
    top: 0
}
const OPEN_TIME = 3000;
var openClock;  //计时器
var clockLeft;  //剩余时间
var curElement; //当前元素
var oriColor;   //当前元素的原始颜色
function openArticle(ele){
    return function(){
        // 从r转换到g

        clockLeft -= 100;
        if(clockLeft <= 0){ //如果午时已到
            //alert(ele.parentNode.id.substr(2))
            // aId = 
            // $.ajax({
            //     type: "GET",
            //     url: "http://localhost:8081/article.html",
            //     dataType: "html", //表示返回值类型，不必须
            //     data: {'id':aId},
            //     success: function(result){
            //         //alert(result)
            //         $('#content').html(result);
            //     }
            // });
            // TODO 跳转页面
            $("#content").load('article.html')
            window.article_id = ele.id.substr(2);
            clearInterval(openClock);//清除计时器
        }
        curColorValue = parseInt(clockLeft / OPEN_TIME * 255);    //计算颜色值
        ele.style.background = 'rgb(' + curColorValue +', ' + (255 - curColorValue) + ', 0)';
    }
}

function getPostParent(ele){
    parNode = ele;
    while(parNode != null && parNode.nodeName != '#document'){
        if(parNode.className == 'blog-post'){
            return parNode;
        }
        parNode = parNode.parentNode;  //获取父节点
        //console.log(parNode)
    }
    return null;
}

function moveTarget() {

    // Move the model target to where we predict the user is looking to
    if (itrack.currentModel == null || facetracker.currentEyeRect==null || itrack.inTraining) {
        return;
    }
    const prediction = itrack.getPrediction();
    //console.log(prediction)

    // cursor.left = prediction[0] * ($('body').width() - $('#target').outerWidth());
    // cursor.top = prediction[1] * ($('body').height() - $('#target').outerWidth());
    cursor.left = prediction[0] * ($('body').width() - $('#target').outerWidth());
    cursor.top = (prediction[1]+0)* ($(window).height() - $('#target').outerWidth()) + $(window).scrollTop();
    //console.log(left)
    //console.log(right)=
    $('#target').css('left', cursor.left + 'px');
    $('#target').css('top', cursor.top + 'px');
    // console.log('cur x is '+cursor.left+' y is '+cursor.top);
    // console.log($(window).height()*0.80 + $(window).scrollTop())
    // console.log($(window).height()*0.40 + $(window).scrollTop())

    
    //用于上下滚动页面的代码
    if(cursor.top > $(window).height()*0.80 + $(window).scrollTop()){   //向下移动的逻辑
        //console.log("down");
         //已经滚动到上面的页面高度
        var scrollTop = $(this).scrollTop();
        //页面高度
        var scrollHeight = $(document).height();
       //浏览器窗口高度
        var windowHeight = $(this).height();
        //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
        if (scrollTop + windowHeight == scrollHeight) {
            if(window.curLastId == -1){
                ;   //没有更多文章
            }else{
                getNextPage(window.curLastId); //获取下一页的数据
            }
        }
        var temp=$('html,body').scrollTop();//.animate({scrollTop:cursor.top+$(window).height()/1024*1024},'slow');
        $('html,body').scrollTop(temp+30)
        if(curElement != null){ //如果有元素在计时
            curElement.style.background = oriColor; //恢复原来的颜色
            curElement = null;  //清空当前元素
            clearInterval(openClock);   //清除历史计时器
        }
        return ;
    }
    if(cursor.top < $(window).height()*0.20 + $(window).scrollTop()){
        //console.log('up')
        var temp=$('html,body').scrollTop()//.animate({scrollTop:cursor.top+$(window).height()/1024*1024},'slow');
        $('html,body').scrollTop(temp-30)
        if(curElement != null){ //如果有元素在计时
                curElement.style.background = oriColor; //恢复原来的颜色
                curElement = null;  //清空当前元素
                clearInterval(openClock);   //清除历史计时器
             }
        return ;
    }
    var ele = document.elementFromPoint(cursor.left,cursor.top - $(window).scrollTop());    //获取元素
   //console.log('left:'+cursor.left+' '+'top:'+cursor.top)
    //console.log(ele)
    ele = getPostParent(ele);
    //console.log(ele)
    if(curElement != null){
        var divx1 = curElement.offsetLeft;
        var divy1 = curElement.offsetTop;
        var divx2 = curElement.offsetLeft + curElement.offsetWidth;
        var divy2 = curElement.offsetTop + curElement.offsetHeight;
        if( cursor.left < divx1 || cursor.left > divx2 || cursor.top < divy1 || cursor.top > divy2){
            //如果离开，则执行。。
            //curElement = null;
            ;
        }else{
            return;
        }
    }
    //console.log(curElement)
    if(ele != null){    //如果元素不为空
        
        if(curElement != ele){   //如果是标题并且没有在计时当中
            clockLeft = OPEN_TIME;   //设置剩余时间
            if(curElement != null){ //如果有元素在计时
                curElement.style.background = oriColor; //恢复原来的颜色
            }
            oriColor = ele.style.background;    //保存原来的颜色
            clearInterval(openClock);   //清除历史计时器
            openClock = setInterval(openArticle(ele),100);   //每100ms检查一次

            curElement = ele;   //重新设置当前计时的标签

        }
            //console.log(ele)
            //console.log(ele.className);
            //ele.style.background = 'yellow';
    }
     else{
             if(curElement != null){ //如果有元素在计时
                curElement.style.background = oriColor; //恢复原来的颜色
                curElement = null;  //清空当前元素
                clearInterval(openClock);   //清除历史计时器
             }
             
         }
    
}
