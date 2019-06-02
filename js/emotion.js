const APIKEY = 'Bk8px0FuCnBAoEO_01E7UWevBKh1XQCD';
const APISERET = 'yFMF-1YIotz7usr1P_eOGp4fxVilK6ei';
var facepp = new FACEPP(APIKEY,APISERET,1);
function norm(arr){
    var res = 0;
    for(var i=0;i<arr.length;i++){
        res += arr[i]*arr[i];
    }
    return Math.sqrt(res);
}
window.emotion = {
	// @param _type 类型
	// @param emotion 情绪 ，七维向量
	getScore:function( _type,emotion){

		// 标准情绪向量
		var st_emotion={'国际':[14.67, 0.163, 0.163, 1.936, 68.429, 14.67, 11.633],
		 '军事':[68.429, 1.936, 11.633, 14.67, 0.163, 14.67, 0.163],
		 '国内':[14.67, 0.163, 11.633, 1.936, 0.163, 68.429, 14.67],
		 '台湾':[11.633, 1.936, 14.67, 14.67, 68.429, 0.163, 0.163],
		 '社评':[1.936, 11.633, 0.163, 68.429, 14.67, 14.67, 0.163],
		 '社会':[0.163, 14.67, 68.429, 1.936, 14.67, 11.633, 0.163],
		 '海外看中国':[14.67, 68.429, 0.163, 1.936, 11.633, 14.67, 0.163]
		 }
		var types=_type.split('&') // 获取所有类型标签
	    var standard=[0,0,0,0,0,0,0];
	    //console.log(types)
	    for(var t=0; t<types.length;t++){
	        for(var i=0;i<standard.length;i++){
	            standard[i]+=st_emotion[types[t]][i] ; //相加得新的标准情绪向量
	        }
	    }
	    var resMatrix = 0;
	    for(var i =0;i < standard.length; i++){
	        resMatrix += standard[i] *emotion[i];
	    }
	    //console.log(standard)
	    //console.log(emotion)
	    //console.log(resMatrix)
	    var cos1= resMatrix/(norm(standard)*norm(emotion));    //计算余弦相似度
	    //console.log(cos1)
	    var rate=5*(cos1+1); //映射到-10
	    //console.log(rate('军事&社会',[1,1,1,1,1,1,1]))
	    //console.log(window.curArticleType);
	    //console.log(window.curArticleId);

	    // 放置cookie
	    // ajax
	    return rate;
	},
	// 处理情绪
	dealEmotion:function(e){
		// 没有检测到人脸
		if(e.faces.length < 1){
			return;
		}else{
			var emotionMap = e.faces[0].attributes.emotion;
			var emotions = [emotionMap.anger,emotionMap.disgust,emotionMap.fear,emotionMap.happiness,emotionMap.neutral,emotionMap.sadness,emotionMap.surprise];
			console.log(JSON.stringify(emotions))
			var rate = emotion.getScore(window.curArticleType,emotions);
            console.log('评分为'+rate);
			// 构造数据
			var data = {'aId':window.curArticleId,'rate':rate};

			// 设置JSESSIONID
			document.cookie = 'JSESSIONID=' + window.curUserCookie;
			// 发送数据到后台
			$.ajax({
                url : 'http://localhost:8082/read/readArt',
                data : data,
                dataType: 'json',
                type : 'POST',
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,

                success: function(res) {
                    // alert(str)
                    // // 将返回值转换为json对象
                    // res = eval('(' + str + ')');
                    // 如果提交成功
                    if(res.code == 0){
                    	;
                        //refreshComment();
                    }else{

                        alert(res.message);
                    }
                }
            })
			//console.log(rate);
		}
		

		// getScore(); // 发送评分到后台
	},
	getEmotion:function(base64Image){
		//console.log(base64Image);
        let attributes = 'emotion';
        //上传图片,获取结果
        let dataDic = {'image_base64':base64Image,'return_landmark':0,'return_attributes':attributes};
		
		//调用接口，检测人脸
        facepp.detectFace(dataDic,emotion.dealEmotion);
	},
	run:function(){
		
	}
}
function emotionRun(){
    // 如果没有登录
    if(window.curUserInfo == null){
        // 返回
        return;
    }
	if (itrack.currentModel == null || facetracker.currentEyeRect==null || itrack.inTraining) {
		    return;
	}
	if(window.curLevel == 1){
		var canvas=document.getElementById('gastureCan');
		var canCC=canvas.getContext('2d');
		canCC.drawImage(facetracker.video,0,0,400,300);
		var base64Image=canvas.toDataURL("image/jpeg"); //获得当前帧的图片
		//window.emotion.cur_type = '社会&军事';
		emotion.getEmotion(base64Image);

		console.log('情绪识别执行中');
	}
	
}
