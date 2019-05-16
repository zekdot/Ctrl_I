// 配置相关的属性
window.config = {
    debugCanvas : true, // 是否开启调试canvas
    eyeActive : true,     // 是否开启眼部追踪
    eyePageTurn : true, //  是否开启眼部的翻页
    eyeButton : true,   //是否开启眼部激活按钮
    eyeActiveTime : 3000,    // 开启按钮时间(ms为单位)
    gestureActive : true,    //是否开启手势
    gesturePageTurn : false,    //是否开启手势翻页，与眼部不可共存
    gestureButton : true,   //是否开启手势进入、返回功能
}
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
jQuery(document).ready(function($) {
    /* 设置配置盘的初始事件 */
    $('#debugCanvasCheck').attr('checked',config.debugCanvas);
    $('#eyeActiveCheck').attr('checked',config.eyeActive);
    $('#eyePageTurnCheck').attr('checked',config.eyePageTurn);
    $('#eyeButtonCheck').attr('checked',config.eyeButton);
    $('#eyeActiveTimeText').val(config.eyeActiveTime);
    // config.eyeActive = false;
    // 如果没有启动视线追踪
    if(!config.eyeActive){
        // 将视线相关的功能关闭
        // $('#eyeActiveTimeText').attr('disabled',true);
        // $('#eyePageTurnCheck').attr('disabled',true);
        // $('#eyeButtonCheck').attr('disabled',true);
        setEyeDisable(true);
    }
    $('#gestureActiveCheck').attr('checked',config.gestureActive);
    $('#gesturePageTurnCheck').attr('checked',config.gesturePageTurn);
    $('#gestureButtonCheck').attr('checked',config.gestureButton);
    // 如果没有启动视线追踪
    if(!config.gestureActive){
        setGestureDisable(true);
    }
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
            window.cursor.top = 400;
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
    })
});