const access_token = '24.e801c23f58cde5dad6988c08adff82a2.2592000.1559395919.282335-16158116';
// 手势识别
window.gesture = {
    dealGesture:function(json) {
        //TODO 1 2 3分别代表三列
        //TODO 4代表上 5代表下
        //TODO 点赞代表打开，8代表返回
        //TODo 可能性大于40%时执行
        for(var i = 0; i < json.result_num; i++) {
            console.log(json.result[i].classname+' '+json.result[i].probability);
        }
    },
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
            success: gesture.dealGesture
        });
    }
}

function gestureRun(){
    if (itrack.currentModel == null || facetracker.currentEyeRect==null || itrack.inTraining) {
            return;
    }
    var canvas=document.getElementById('gastureCan');
    var canCC=canvas.getContext('2d');
    canCC.drawImage(facetracker.video,0,0,400,300);
    var base64Image=canvas.toDataURL("image/jpeg"); //获得当前帧的图片
    gesture.getGesture(base64Image)

    //console.log('情绪识别执行中');
}
/*
    数字1 One
    数字5 Five
    拳头  Fist
    OK  OK
    祈祷  Prayer
    作揖  Congratulation
    作别  Honour
    单手比心    Heart_single
    点赞  Thump_up
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