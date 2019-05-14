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
