const APIKEY = 'Bk8px0FuCnBAoEO_01E7UWevBKh1XQCD';
const APISERET = 'yFMF-1YIotz7usr1P_eOGp4fxVilK6ei';
var facepp = new FACEPP(APIKEY,APISERET,1);
window.emotion = {

	dealEmotion:function(e){
		console.log(JSON.stringify(e,null,"\t"));
	},
	getEmotion:function(base64Image){
		//console.log(base64Image);
        let attributes = 'emotion';
        //上传图片,获取结果
        let dataDic = {'image_base64':base64Image,'return_landmark':2,'return_attributes':attributes};
		
		//调用接口，检测人脸
        facepp.detectFace(dataDic,emotion.dealEmotion);
	},
	getScore:function(){
		import numpy as np
		#标准情绪向量
		st_emotion={'国际':[14.67, 0.163, 0.163, 1.936, 68.429, 14.67, 11.633],
		 '军事':[68.429, 1.936, 11.633, 14.67, 0.163, 14.67, 0.163],
		 '国内':[14.67, 0.163, 11.633, 1.936, 0.163, 68.429, 14.67],
		 '台湾':[11.633, 1.936, 14.67, 14.67, 68.429, 0.163, 0.163],
		 '社评':[1.936, 11.633, 0.163, 68.429, 14.67, 14.67, 0.163],
		 '社会':[0.163, 14.67, 68.429, 1.936, 14.67, 11.633, 0.163],
		 '海外看中国':[14.67, 68.429, 0.163, 1.936, 11.633, 14.67, 0.163]}

		def rate(_type,emotion):#计算评分
		    emotion=np.array(emotion)
		    types=_type.split('&')#获取所有类型标签
		    standard=0
		    for t in types:
		        standard+=np.array(st_emotion[t])#相加得新的标准情绪向量
		    cos=np.dot(standard,emotion)/(np.linalg.norm(standard)*np.linalg.norm(emotion))#计算余弦相似度
		    rate=5*(cos+1)#映射到-10
		    return rate
		#print(rate('军事&社会',[1,1,1,1,1,1,1]))


	}
	run:function(){
		
	}
}
function emotionRun(){
	if (itrack.currentModel == null || facetracker.currentEyeRect==null || itrack.inTraining) {
		    return;
	}
	var canvas=document.getElementById('gastureCan');
	var canCC=canvas.getContext('2d');
	canCC.drawImage(facetracker.video,0,0,400,300);
	var base64Image=canvas.toDataURL("image/jpeg"); //获得当前帧的图片
	emotion.getEmotion(base64Image)

	console.log('情绪识别执行中');
}