window.backhost = 'http://localhost';
window.fronthost = 'http://localhost';
/**
 * 设置canvas的display
 * @param value display的属性值
 */
function setCanvas(value){
    $('#face').css('display',value);
    $('#eyes').css('display',value);
    $('#webcam').css('display',value);
    $('#overlay').css('display',value);
}
/**
 * 设置眼部功能的状态
 * @param value 状态值
 */
function setEyeDisable(value){
    $('#eyeActiveTimeText').attr('disabled',value);
    $('#eyePageTurnCheck').attr('disabled',value);
    $('#eyeButtonCheck').attr('disabled',value);
}
/**
 * 设置姿势的状态
 * @param value 状态值
 */
function setGestureDisable(value){
    $('#gesturePageTurnCheck').attr('disabled',value);
    $('#gestureButtonCheck').attr('disabled',value);
}

// 配置相关的属性
window.config = {
    debugCanvas : true, // 是否开启调试canvas
    eyeActive : false,     // 是否开启眼部追踪
    eyePageTurn : true, //  是否开启眼部的翻页
    eyeButton : true,   //是否开启眼部激活按钮
    eyeActiveTime : 3000,    // 开启按钮时间(ms为单位)
    gestureActive : true,    //是否开启手势
    gesturePageTurn : false,    //是否开启手势翻页，与眼部不可共存
    gestureButton : true,   //是否开启手势进入、返回功能
    printConfig : function(){   // 打印配置
        console.log('debugCanvas : ' + config.debugCanvas);
        console.log('eyeActive : ' + config.eyeActive);
        console.log('eyePageTurn : ' + config.eyePageTurn);
        console.log('eyeButton : ' + config.eyeButton);
        console.log('eyeActiveTime : ' + config.eyeActiveTime);
        console.log('gestureActive : ' + config.gestureActive);
        console.log('gesturePageTurn : ' + config.gesturePageTurn);
        console.log('gestureButton : ' + config.gestureButton)
    },
    saveConfig : function(){    // 保存配置
        setCookie('debugCanvas',config.debugCanvas);
        setCookie('eyeActive',config.eyeActive);
        setCookie('eyePageTurn',config.eyePageTurn);
        setCookie('eyeButton',config.eyeButton);
        setCookie('eyeActiveTime',config.eyeActiveTime);
        setCookie('gestureActive',config.gestureActive);
        setCookie('gesturePageTurn',config.gesturePageTurn);
        setCookie('gestureButton',config.gestureButton);
        // 标识是否有预定义的配置
        setCookie('savedConfig',true);  
        console.log('配置已经存储');
    },
    loadConfig : function(){    // 读取配置
        if(!getCookie('savedConfig')){
            console.log('没有发现预先定义的配置');
        }else{
            config.debugCanvas = eval(getCookie('debugCanvas'));
            config.eyeActive = eval(getCookie('eyeActive'));
            config.eyePageTurn = eval(getCookie('eyePageTurn'));
            config.eyeButton = eval(getCookie('eyeButton'));
            config.eyeActiveTime = eval(getCookie('eyeActiveTime'));
            config.gestureActive = eval(getCookie('gestureActive'));
            config.gesturePageTurn = eval(getCookie('gesturePageTurn'));
            config.gestureButton = eval(getCookie('gestureButton'));

        }
        config.printConfig();
        //$('html,body').scrollTop(window.historyTop);
            //alert('fuckf')
        //如果没有开启画板
        if(!config.debugCanvas){ 
            // 隐藏画板   
            setCanvas('none');
        }
        /* 设置配置盘的初始事件 */
        $('#debugCanvasCheck').attr('checked',config.debugCanvas);
        //alert(config.debugCanvas)
            //alert(config.eyeActive);
        $('#eyeActiveCheck').attr('checked',config.eyeActive);
        $('#eyePageTurnCheck').attr('checked',config.eyePageTurn);
        $('#eyeButtonCheck').attr('checked',config.eyeButton);
        $('#eyeActiveTimeText').val(config.eyeActiveTime);
                // config.eyeActive = false;
                //console.log(config.eyeActive)
        // 如果没有启动视线追踪
        if(!config.eyeActive){
                    // 将视线相关的功能关闭
                    // $('#eyeActiveTimeText').attr('disabled',true);
                    // $('#eyePageTurnCheck').attr('disabled',true);
                    // $('#eyeButtonCheck').attr('disabled',true);
            setEyeDisable(true);
        }
            //alert($('#eyeActiveCheck').attr('checked'))
        $('#gestureActiveCheck').attr('checked',config.gestureActive);
        $('#gesturePageTurnCheck').attr('checked',config.gesturePageTurn);
        $('#gestureButtonCheck').attr('checked',config.gestureButton);
                // 如果没有启动视线追踪
        if(!config.gestureActive){
            setGestureDisable(true);
        }

        // /* 设置配置盘的初始事件 */
        // $('#debugCanvasCheck').attr('checked',config.debugCanvas);
        // $('#eyeActiveCheck').attr('checked',config.eyeActive);
        // $('#eyePageTurnCheck').attr('checked',config.eyePageTurn);
        // $('#eyeButtonCheck').attr('checked',config.eyeButton);
        // $('#eyeActiveTimeText').val(config.eyeActiveTime);
        //     // config.eyeActive = false;
        //     //console.log(config.eyeActive)
        // // 如果没有启动视线追踪
        // if(!config.eyeActive){
        //         // 将视线相关的功能关闭
        //         // $('#eyeActiveTimeText').attr('disabled',true);
        //         // $('#eyePageTurnCheck').attr('disabled',true);
        //         // $('#eyeButtonCheck').attr('disabled',true);
        //     setEyeDisable(true);
        // }
        // $('#gestureActiveCheck').attr('checked',config.gestureActive);
        // $('#gesturePageTurnCheck').attr('checked',config.gesturePageTurn);
        // $('#gestureButtonCheck').attr('checked',config.gestureButton);
        //     // 如果没有启动视线追踪
        // if(!config.gestureActive){
        //     setGestureDisable(true);
        // }
        console.log('配置已经加载');
    }
} 

jQuery(document).ready(function($) {
    // 加载配置
    config.loadConfig();
    /* 增加配置盘的监听事件 */
    // 调试视频开启选项
    $('#debugCanvasCheck').click(function(){
        // 如果没有开启调试视频
        if(!$('#debugCanvasCheck').attr('checked')){
            // 设置没有选中
            config.debugCanvas = false;
            //console.log('关闭视频画面');
            setCanvas('none');
        }else{
            // 设置选中
            config.debugCanvas = true;
            //console.log('开启视频画面');
            setCanvas('block');
        }
        // 保存配置到cookies中
        config.saveConfig();
    })
    // 视线追踪开启选项
    $('#eyeActiveCheck').click(function(){
        // 如果没有开启视线追踪
        if(!$('#eyeActiveCheck').attr('checked')){
            // 关闭实现追踪功能
            config.eyeActive = false;
            setEyeDisable(true);
            //console.log('关闭视线追踪');
            // 归零坐标
            window.cursor.left = 0;
            // 以备手势调用
            window.cursor.top = $('html,body').scrollTop()+$(window).height() / 2;
            $('#target').css('left', '0px');
            $('#target').css('top', '0px');
            //setCanvas('none');
        }else{
            // 开启实现追踪功能
            config.eyeActive = true;
            setEyeDisable(false);
            //console.log('开启视线追踪');
            //setCanvas('block');
        }
        // 保存配置到cookies中
        config.saveConfig();
    });
    // 视线翻页开启选项
    $('#eyePageTurnCheck').click(function(){
        // 如果没有开启视线追踪
        if(!$('#eyePageTurnCheck').attr('checked')){
            // 关闭视线翻页功能
            config.eyePageTurn = false;
        }else{
            // 开启实现追踪功能
            config.eyePageTurn = true;
        }
        // 保存配置到cookies中
        config.saveConfig();
    });
    // 视线点击开启选项
    $('#eyeButtonCheck').click(function(){
        // 如果没有开启视线点击
        if(!$('#eyeButtonCheck').attr('checked')){
            // 关闭视线点击功能
            config.eyeButton = false;
        }else{
            // 开启视线点击功能
            config.eyeButton = true;
        }
        // 保存配置到cookies中
        config.saveConfig();
    });
    // 按钮激活时间设置
    $('#eyeActiveTimeText').blur(function(){
        // 设置按钮激活时间
        config.eyeActiveTime = $('#eyeActiveTimeText').val();
        // 保存配置到cookies中
        config.saveConfig();
    });
    // 手势功能开启选项
    $('#gestureActiveCheck').click(function(){
        // 如果没有开启手势功能
        if(!$('#gestureActiveCheck').attr('checked')){
            // 关闭手势功能
            config.gestureActive = false;
            setGestureDisable(true);
            //console.log('关闭视线追踪');
            //setCanvas('none');
        }else{
            // 开启手势功能
            config.gestureActive = true;
            setGestureDisable(false);
            //console.log('开启视线追踪');
            //setCanvas('block');
        }
        // 保存配置到cookies中
        config.saveConfig();
    })
    // 手势翻页开启选项
    $('#gesturePageTurnCheck').click(function(){
        // 如果没有开启手势功能
        if(!$('#gesturePageTurnCheck').attr('checked')){
            // 关闭手势翻页功能
            config.gesturePageTurn = false;
        }else{
            // 开启手势翻页功能
            config.gesturePageTurn = true;
        }
        // 保存配置到cookies中
        config.saveConfig();
    })
    // 手势按钮开启选项
    $('#gestureButtonCheck').click(function(){
        // 如果没有开启手势按钮功能
        if(!$('#gestureButtonCheck').attr('checked')){
            // 关闭手势按钮功能
            config.gestureButton = false;
        }else{
            // 开启手势按钮功能
            config.gestureButton = true;
        }
        // 保存配置到cookies中
        config.saveConfig();
    })
});
