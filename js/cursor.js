window.cursor = {
    left: 0,
    top: 0,
    historyLeft : 0,
    historyTop : 0,
    // 轨迹队列
    trackQueue : [],
    // 默认位置
    trackPoint : null
}
const DOWN_DISTANCE = 30;
const OPEN_TIME = 3000;
//计时器
var openClock;  
//剩余时间
var clockLeft;  
//当前元素
var curElement = null; 
//当前元素的原始颜色
var oriColor;   

/**
 * 处理按钮，同时背景从R转换到G
 * @param ele 文章元素
 * @param fun 午时到了之后的回调函数
 */
function handleButton(ele,fun){
    return function(){
        // 时间减去100ms
        clockLeft -= 100;
        //获得最左和最上的坐标
        var point = getPoint(ele); 
        //获得最左   
        var divx1 = point.l; 
        //获得最上
        var divy1 = point.t;  
        // 获得最右
        var divx2 = divx1 + curElement.offsetWidth; 
        // 获得最下
        var divy2 = divy1 + curElement.offsetHeight; 
        // 如果当前光标位于处理元素之外
        if( cursor.left < divx1 || cursor.left > divx2 || cursor.top < divy1 || cursor.top > divy2){
            // 清除计时器
            clearInterval(openClock);
            // 恢复原来的颜色
            curElement.style.background = oriColor;
            // 清除当前处理元素
            curElement = null;
            // 退出处理函数
            return;
        }
        //如果午时已到
        if(clockLeft <= 0){ 
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
            
            // 执行回调函数
            fun();
            //清除计时器
            clearInterval(openClock);
            // 清除当前处理元素
            curElement = null;
        }
        // 计算颜色值
        curColorValue = parseInt(clockLeft / config.eyeActiveTime * 255);    
        // 设置颜色值
        ele.style.background = 'rgb(' + curColorValue +', ' + (255 - curColorValue) + ', 0)';
    }
}
/**
 * 返回，同时返回图片从无色变深
 * @param fun 午时到了之后的回调函数
 */
function handleBack(fun){
    return function(){
        // 时间减去100ms
        clockLeft -= 100;
        // //获得最左和最上的坐标
        // var point = getPoint(ele); 
        // //获得最左   
        // var divx1 = point.l; 
        // //获得最上
        // var divy1 = point.t;  
        // // 获得最右
        // var divx2 = divx1 + curElement.offsetWidth; 
        // // 获得最下
        // var divy2 = divy1 + curElement.offsetHeight; 
        // // 如果当前光标位于处理元素之外
        // if( cursor.left < divx1 || cursor.left > divx2 || cursor.top < divy1 || cursor.top > divy2){
        //     // 清除计时器
        //     clearInterval(openClock);
        //     // 恢复原来的颜色
        //     curElement.style.background = oriColor;
        //     // 清除当前处理元素
        //     curElement = null;
        //     // 退出处理函数
        //     return;
        // }
        // 如果回到了文章页面
        if(cursor.left > $(window).width()*0.20 && cursor.left < $(window).width()*0.80){
            // 清除计时器
            clearInterval(openClock);
            // 恢复元素的opacity
            $('.back img').css('opacity',0);
            // 清除当前处理元素
            curElement = null;
            return;
        }
        //如果午时已到
        if(clockLeft <= 0){ 
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
            
            // 执行回调函数
            fun();
            //清除计时器
            clearInterval(openClock);
            // 恢复透明值
            $('.back img').css('opacity',0);
            // 清除当前处理元素
            curElement = null;
        }
        // 计算颜色值
        var curOpacity = clockLeft / config.eyeActiveTime;    
        // 设置颜色值
        $('.back img').css('opacity',1 - curOpacity);
    }
}
/**
 * 根据类名获取特定的父亲节点
 * @param ele 元素
 * @param parName 父亲类名
 */
function getParentByClassName(ele,parName){
    parNode = ele;
    while(parNode != null && parNode.nodeName != '#document'){  //如果当前元素不为空并且也没有到顶级
        if(parNode.className == parName){   //如果找到了需要的父亲节点
            return parNode; //返回父亲节点
        }
        parNode = parNode.parentNode;  //获取父节点
        //console.log(parNode)
    }
    return null;    //没有找到父亲节点
}
/**
 * 根据类名获取特定的父亲节点
 * @param ele 元素
 * @param parName 父亲类名
 */
function getParentById(ele,parName){
    parNode = ele;
    while(parNode != null && parNode.nodeName != '#document'){  //如果当前元素不为空并且也没有到顶级
        if(parNode.id == parName){   //如果找到了需要的父亲节点
            return parNode; //返回父亲节点
        }
        parNode = parNode.parentNode;  //获取父节点
        //console.log(parNode)
    }
    return null;    //没有找到父亲节点
}

function getDisSqua(a,b){
    return (a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]);
}

function getDis(a,b,index){
    return (a[index] - b[index]) * (a[index] - b[index])
}
/**
 * 移动光标
 */
function moveTarget() {

    // Move the model target to where we predict the user is looking to
    if (!window.config.eyeActive || itrack.currentModel == null || itrack.inTraining || facetracker.currentEyeRect == null) {
        return;
    }
    // 如果检测到了眼睛
    if(facetracker.isDetect == false){
        console.log('没有检测到眼睛')
        cursor.left = $('body').width()/2;
        cursor.top = $('body').height()/2;
        $('#target').css('left', cursor.left + 'px');
        $('#target').css('top', cursor.top + 'px');
        return;
    }
    // 获取预测结果
    var prediction = itrack.getPrediction();
    // var lambda = 0.5;
    // 如果不足10个
    // if (window.cursor.trackQueue.length < 2){
    //     // 直接入队
    //     window.cursor.trackQueue.push(prediction);
    // }else{
    //     //console.log(window.cursor.trackQueue)
    //     if(getDisSqua(prediction,window.cursor.trackQueue[1]) < 0.2 * 0.2){
            
    //     }else{

    //         // 头部出队
    //         window.cursor.trackQueue.shift();
    //         // 入队
    //         window.cursor.trackQueue.push(prediction);

    //         // x方向的delta
    //         var deltaX = 0; 
    //         // y方向的delta
    //         var deltaY = 0;

    //         //console.log(window.cursor.trackQueue[0][1])
    //         // 计算delta
    //         for(var i=1 ;i < window.cursor.trackQueue.length;i++){
    //             // 增加x方向的delta
    //             deltaX += window.cursor.trackQueue[i][0] - window.cursor.trackQueue[i-1][0];
    //             // 增加y方向的delta
    //             deltaY += window.cursor.trackQueue[i][1] - window.cursor.trackQueue[i-1][1];
    //         }
    //         //计算新的prediction
    //         prediction[0] = window.cursor.trackQueue[0][0] + lambda * deltaX;
    //         prediction[1] = window.cursor.trackQueue[0][1] + deltaY;
    //     }

    // }
    // 如果没有历史点
    if(window.cursor.trackPoint == null){
        // 设置一个历史点
        window.cursor.trackPoint = prediction;
        return;
    }

    //
    //console.log('当前距离为' + getDisSqua(prediction,window.cursor.trackPoint))
    var dis = getDisSqua(prediction,window.cursor.trackPoint)
    // 如果移动范围不够大
    if(dis < 0.2 * 0.2 || dis > 0.8 * 0.8 ){
        // 覆盖预测
        prediction = window.cursor.trackPoint;
    }else{
        // 更新位置
        window.cursor.trackPoint = prediction;
    }
    // if((getDis(prediction,window.cursor.trackPoint,0) < 0.01) || (getDis(prediction,window.cursor.trackPoint,1) < 0.01)){
    //     // 覆盖预测
    //     prediction = window.cursor.trackPoint;
    // }

    
    //console.log(prediction)

    // cursor.left = prediction[0] * ($('body').width() - $('#target').outerWidth());
    // cursor.top = prediction[1] * ($('body').height() - $('#target').outerWidth());
    cursor.left = prediction[0] * ($('body').width() - $('#target').outerWidth());
    cursor.top = (prediction[1])* ($(window).height() - $('#target').outerWidth()) + $(window).scrollTop();

    //if(cursor.top)
    //console.log(left)
    //console.log(right)=
    $('#target').css('left', cursor.left + 'px');
    $('#target').css('top', cursor.top + 'px');
    // console.log('cur x is '+cursor.left+' y is '+cursor.top);
    // console.log($(window).height()*0.80 + $(window).scrollTop())
    // console.log($(window).height()*0.40 + $(window).scrollTop())
    // if(cursor.top > $(window).height()*0.80 + $(window).scrollTop()))
    
    //用于上下滚动页面的代码
    if(config.eyePageTurn && cursor.top > $(window).height()*0.70 + $(window).scrollTop()){   //向下移动的逻辑
        //console.log("down");
       //   //已经滚动到上面的页面高度
       //  var scrollTop = $(this).scrollTop();
       //  //页面高度
       //  var scrollHeight = $(document).height();
       // //浏览器窗口高度
       //  var windowHeight = $(this).height();
       //  // 如果是文章列表层级
       //  if(window.curLevel == 0){
       //      //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
       //      if (scrollTop + windowHeight == scrollHeight) {
       //          //没有更多文章
       //          if(window.curLastId == -1){
       //              ;   
       //          }else{
       //              //获取下一页的数据
       //              getNextPage(window.curLastId); 
       //          }
       //      }
       //  }
        // 获取当前的滚动条位置
        var temp=$('html,body').scrollTop();//.animate({scrollTop:cursor.top+$(window).height()/1024*1024},'slow')
        //向下移动一段距离
        $('html,body').scrollTop(temp + DOWN_DISTANCE);     
        //如果有元素在计时
        if(curElement != null){ 
            //恢复原来的颜色
            curElement.style.background = oriColor; 
            //清空当前元素
            curElement = null;  
            //清除历史计时器
            clearInterval(openClock);   
        }
        // 如果移动就不再判断当前元素了
        return ;
    }
    if(config.eyePageTurn && cursor.top < $(window).height()*0.30 + $(window).scrollTop()){
        //console.log('up')
        var temp=$('html,body').scrollTop()//.animate({scrollTop:cursor.top+$(window).height()/1024*1024},'slow');
        $('html,body').scrollTop(temp-DOWN_DISTANCE);
        if(curElement != null){ //如果有元素在计时
                curElement.style.background = oriColor; //恢复原来的颜色
                curElement = null;  //清空当前元素
                clearInterval(openClock);   //清除历史计时器
             }
        return ;
    }
    //如果当前有元素正在处理之中
    if(!config.eyeButton || curElement != null){ 
        //直接退出
        return; 
    }
    //获取当前元素
    var ele = document.elementFromPoint(cursor.left,cursor.top - $(window).scrollTop());    
   //console.log('left:'+cursor.left+' '+'top:'+cursor.top)
    //console.log(ele)
    // 如果当前位于文章标题列表页面
    if(window.curLevel == 0){
        // 找到顶层父亲
        ele = getParentByClassName(ele,'blog-post');    
        //如果元素不为空
        if(ele != null){    
            //设置剩余时间
            clockLeft = config.eyeActiveTime;
            //保存原来的颜色
            oriColor = ele.style.background;    
            //设置当前处理元素
            curElement = ele;
            //开启处理，每100ms检查一次
            openClock = setInterval(handleButton(curElement,function(){
                //跳转到文章页面
                jumpToArt(curElement.id.substr(2));
            }),100);   
        }
    }
    // 如果当前位于文章详情页面
    if(window.curLevel == 1){
        //console.log('当前位于页面')

        // 如果当前光标位于页面左空白或者右空白
        if(cursor.left < $(window).width()*0.20 || cursor.left > $(window).width()*0.80){
            //$('.back img').css('opacity',1)
            //console.log('退出')
            // 如果当前有元素正在处理
            if(curElement != null){
                // 退出
                return ; 
            }
            //设置当前处理元素
            curElement = true;
            //设置剩余时间
            clockLeft = config.eyeActiveTime;   
            // 开启处理，每100ms检查一次
            openClock = setInterval(handleBack(function(){
                // 跳转到文章列表页面
                backArtList(window.articleId);
            }),100);
        }

        // 返回键逐渐显示

        // 大返回键返回
         // 找到顶层父亲
        // ele = getParentByClassName(ele,'back');

        // //ele.style.background = 'red';
        // //console.log(ele);
        // //如果元素不为空
        // if(ele != null){    
        //     //alert('in')
        //     //设置剩余时间
        //     clockLeft = config.eyeActiveTime;   

        //     // if(curElement != null){ //如果有元素在计时
        //     //     curElement.style.background = oriColor; //恢复原来的颜色
        //     // }
        //     //保存原来的颜色
        //     oriColor = ele.style.background;    
        //     //设置当前处理元素
        //     curElement = ele;
        //     //console.log(curElement)
        //     //clearInterval(openClock);   //清除历史计时器

        //     //开启处理，每100ms检查一次
        //     openClock = setInterval(handleButton(curElement,function(){
        //         // 跳转到文章列表页面
        //         backArtList(window.articleId);
        //     }),100);   

        //     //curElement = ele;   //重新设置当前计时的标签

        
        //     //console.log(ele)
        //     //console.log(ele.className);
        //     //ele.style.background = 'yellow';
        // }
    }
    
    //console.log(ele)
    // if(curElement != null){
    //     var divx1 = curElement.offsetLeft;
    //     var divy1 = curElement.offsetTop;
    //     var divx2 = curElement.offsetLeft + curElement.offsetWidth;
    //     var divy2 = curElement.offsetTop + curElement.offsetHeight;
    //     if( cursor.left < divx1 || cursor.left > divx2 || cursor.top < divy1 || cursor.top > divy2){
    //         //如果离开，则执行。。
    //         //curElement = null;
    //         ;
    //     }else{
    //         return;
    //     }
    // }
    // //console.log(curElement)
    // if(ele != null){    //如果元素不为空
        
    //     if(curElement != ele){   //如果是标题并且没有在计时当中
    //         clockLeft = OPEN_TIME;   //设置剩余时间
    //         if(curElement != null){ //如果有元素在计时
    //             curElement.style.background = oriColor; //恢复原来的颜色
    //         }
    //         oriColor = ele.style.background;    //保存原来的颜色
    //         clearInterval(openClock);   //清除历史计时器
    //         openClock = setInterval(openArticle(ele),100);   //每100ms检查一次

    //         curElement = ele;   //重新设置当前计时的标签

    //     }
    //         //console.log(ele)
    //         //console.log(ele.className);
    //         //ele.style.background = 'yellow';
    // }
    //  else{
    //          if(curElement != null){ //如果有元素在计时
    //             curElement.style.background = oriColor; //恢复原来的颜色
    //             curElement = null;  //清空当前元素
    //             clearInterval(openClock);   //清除历史计时器
    //          }
             
    //      }
    
}
