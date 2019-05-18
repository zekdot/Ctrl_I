/*
    功能列表
    数字1 One
    数字5 Five
    拳头  Fist
    OK  OK
    祈祷  Prayer
    作揖  Congratulation
    作别  Honour
    单手比心    Heart_single
    点赞  Thumb_up
    Diss    Thumb_down
    我爱你 ILY
    掌心向上    Palm_up
    双手比心1   Heart_1
    双手比心2   Heart_2
    双手比心3   Heart_3
    数字2 Two
    数字3 Three
    4 6 7 8 9
    Rock Rock
    竖中指 Insult
*/

const access_token = '24.e801c23f58cde5dad6988c08adff82a2.2592000.1559395919.282335-16158116';
// 一次向下移动是多少
const GES_DOWN_DISTANCE = 200;

function dealGesture(json) {
    //TODO 1 2 3分别代表三列
    //TODO 4代表上 5代表下
    //TODO 点赞代表打开，8代表返回
    //TODo 可能性大于40%时执行
    for(var i = 0; i < json.result_num; i++) {
        // 如果是脸部信息
        if(json.result[i].classname == 'Face'){
            //跳过
            continue;   
        // 如果不为脸
        }else{
            // 如果确定性小于40%
            if(json.result[i].probability < 0.4){
                // 跳过
                continue;
            }
            console.log(json.result[i].classname+' '+json.result[i].probability);
            // 如果是4，代表向上
            if(config.gesturePageTurn && json.result[i].classname == 'Four'){
                // 获取当前的滚动条位置
                var temp=$('html,body').scrollTop();
                //向下移动一段距离
                $('html,body').scrollTop(temp - GES_DOWN_DISTANCE); 
                /// 光标也需要移动 
                window.cursor.top = $('html,body').scrollTop() + $(window).height() / 2;
                $('#target').css('top', cursor.top + 'px');
            }
            // 如果是5，代表向下
            if(config.gesturePageTurn && json.result[i].classname == 'Five'){

                
                // += GES_DOWN_DISTANCE;
                
                // 获取当前的滚动条位置
                var temp=$('html,body').scrollTop();
                //向下移动一段距离
                $('html,body').scrollTop(temp + GES_DOWN_DISTANCE);  
                // 光标也需要移动 
                window.cursor.top = $('html,body').scrollTop() + $(window).height() / 2;
                $('#target').css('top', cursor.top + 'px');
            }
            // 如果当前位于文章标题列表页面
            if(window.curLevel == 0){
                // 移动到第一列
                if(json.result[i].classname == 'One'){
                    // 移动光标到第一列的位置
                    cursor.left = 506;
                    $('#target').css('left', cursor.left + 'px');
                }
                // 移动到第二列
                if(json.result[i].classname == 'Two'){
                    // 移动光标到第二列的位置
                    cursor.left = 906;
                    $('#target').css('left', cursor.left + 'px');
                }
                // 移动到第三列
                if(json.result[i].classname == 'Three'){
                    // 移动光标到第三列的位置
                    cursor.left = 1306;
                    $('#target').css('left', cursor.left + 'px');
                }
                // 如果是点赞
                if(config.gestureButton && json.result[i].classname == 'Thumb_up'){
                    //获取当前元素
                    var ele = document.elementFromPoint(cursor.left,cursor.top - $(window).scrollTop());
                    // 找到顶层父亲
                    ele = getParentByClassName(ele,'blog-post'); 
                    //console.log(ele)   
                    //如果元素不为空
                    if(ele != null){
                        
                        //跳转到文章页面
                        jumpToArt(ele.id.substr(2));
                    }            
                }
            }else if(window.curLevel == 1){
                // 如果是数字8
                if(config.gestureButton && json.result[i].classname == 'Eight'){
                    // 返回文章列表页面
                    backArtList(window.articleId)
                }           
            }
        }
    }
}
// 手势识别
window.gesture = {
    getGesture:function(base64Image) {
        
        //console.log(base64Image);
        var image;
        if(base64Image.substr(0,4) == 'data')
            image = base64Image.substr(22, base64Image.length);
        else
            image = base64Image;

        $.ajax({
            type: "POST",
            url: "https://aip.baidubce.com/rest/2.0/image-classify/v1/gesture",
            contentType: "application/x-www-form-urlencoded", 
            dataType: "json", //表示返回值类型，不必须
            data: {'access_token': access_token, 'image' : image},
            success: dealGesture
        });
    }
}

function gestureRun(){
    if (!window.config.gestureActive || itrack.currentModel == null || facetracker.currentEyeRect==null || itrack.inTraining) {
            return;
    }
    var canvas=document.getElementById('gastureCan');
    var canCC=canvas.getContext('2d');
    canCC.drawImage(facetracker.video,0,0,400,300);
    var base64Image=canvas.toDataURL("image/jpeg"); //获得当前帧的图片
    gesture.getGesture(base64Image)

    //console.log('情绪识别执行中');
}
