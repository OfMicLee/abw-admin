/** **************************************************弹出框部分-开始******************************************** */
var ofDialog = function() {
};

/**
 * 打开弹框
 */
ofDialog.open = function(url, title, width, height) {
    /* alert(url); */
    ofDialog.artDialog = art.dialog.open(url, {
        modal : true,
        height : height+'px',
        width : width+'px',
        title : title,
        lock : true,
        resizable : false,
        draggable : false
    }, false);
};
ofDialog.follow = function(url,title,width,height){
    ofDialog.artDialog = art.dialog.open(url, {
        modal : true,
        height : height+'px',
        width : width+'px',
        follow:$(this),
        title : title,
        lock : false,
        resizable : false,
        draggable : false
    }, false);
};
/**
 * 打开弹框
 */
ofDialog.ajaxOpen = function(url, title, data, width, height) {
    var dialog = art.dialog({
        modal : true,
        height : height+'px',
        width : width+'px',
        lock : true,
        id:"dialog",
        title : title,
        resizable : false,
        closeOnEscape : true,
        draggable : false
    });
    $.ajax({
        url : url,
        success : function(data) {
            dialog.content(data);
        },
        cache : false
    });
};

/**
 * 弹出 alert
 *
 * @param content
 */
ofDialog.alert = function(content) {
    art.dialog({
        window : 'top',
        title : '操作确认',
        id : 'Alert',
        icon : 'warn',
        fixed : true,
        lock : true,
        opacity : .1,
        content : content
    });
};

/**
 * 确认
 *
 * @param {String}
 *            消息内容
 * @param {Function}
 *            确定按钮回调函数
 * @param {Function}
 *            取消按钮回调函数
 */
ofDialog.confirm = function(content, yes, no) {
    return art.dialog({
        window : 'top',
        title : '操作确认',
        id : 'Confirm',
        icon : 'question',
        fixed : true,
        lock : true,
        opacity : .1,
        content : content,
        ok : function(here) {
            return yes.call(this, here);
        },
        cancel : function(here) {
            return no && no.call(this, here);
        }
    });
};

/**
 * 短暂提示
 *
 * @param {String}
 *            提示内容
 * @param {Number}
 *            显示时间 (默认1.5秒)
 */
ofDialog.tips = function(content, time) {
    return art.dialog({
        id : 'Tips',
        title : false,
        cancel : false,
        fixed : true,
        lock : true,
        close:function(){
            ofDialog.callBack();
            return true;
        }
    }).content('<div style="padding: 0 1em;">' + content + '</div>').time(
            time || 1.5);
};

/**
 * 关闭弹框
 */
ofDialog.close = function(callBack,delay) {
    //ofDialog.artDialog.close();
    //art.dialog.close();
    delay = 1;
    if(arguments.length == 1){
        if(!isNaN(arguments[0])){
            delay = arguments[0]
        }
    }
    if(delay == 0){
        ofDialog.artDialog.close();
    }else{
        ofDialog.artDialog.time(delay);
    }
    if (callBack) {
        callBack();
    }

};
/**
 * 关闭弹框
 */
ofDialog.inClose = function(callBack) {
    art.dialog.list["dialog"].close();
    //art.dialog.close();
    if (callBack) {
        callBack();
    }

};

/**
 * 提示消息, msg:消息类容 type:消息类型 【succeed，warning，question，error】 title:消息标题 不填
 * 则为“消息” time:自动关闭时间,为空则不自动关闭 示例：showMessage("提示信息内容。", "", "","");
 */
ofDialog.msg = function(msg, type, title, time) {
    var iconvalue;
    switch (type) {
        case 'succeed':
            mtitle = title || "成功";
            iconvalue = 'succeed';
            break;
        case 'warning':
            mtitle = title || "警示";
            iconvalue = 'warning';
            break;
        case 'question':
            mtitle = title || "提示";
            iconvalue = 'question';
            break;
        case 'error':
            mtitle = title || "错误";
            iconvalue = 'error';
            break;
        default:
            mtitle = title || "消息";
            iconvalue = 'warning';
            break;
    }
    art.dialog({
        id : 'globalMsgFrame',
        title : mtitle,
        icon : iconvalue,
        width : 320,
        height : 80,
        fixed : true,
        drag : false,
        resize : false,
        content : msg,
        button : [ {
            name : '关闭'
        } ]
    });
    time = time || "";
    if (time != "") {
        art.dialog.list['globalMsgFrame'].title(mtitle + " - " + time
            + "秒后自动关闭");
        art.dialog.list['globalMsgFrame'].time(time);
    }
};
/*
 * function showMsg(msg, type, time) { art.dialog({ content : msg, button : [ {
 * name : '关闭' } ] }); }
 */
ofDialog.errorMsg = function(msg, time) {
    art.dialog({
        content : msg,
        button : [ {
            name : '关闭'
        } ]
    });
};

ofDialog.callBack = function() {
    //window.frames["main"].flushCurPage();
};

ofDialog.addcloud = function() {
    var bodyWidth = document.documentElement.clientWidth;
    var bodyHeight = Math.max(document.documentElement.clientHeight, document.body.scrollHeight);
    var bgObj = document.createElement("div" );
    bgObj.setAttribute( 'id', 'bgDiv' );
    bgObj.style.position = "absolute";
    bgObj.style.top = "0";
    bgObj.style.background = "#000000";
    bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75" ;
    bgObj.style.opacity = "0.5";
    bgObj.style.left = "0";
    bgObj.style.width = bodyWidth + "px";
    bgObj.style.height = bodyHeight + "px";
    bgObj.style.zIndex = "10000"; //设置它的zindex属性，让这个div在z轴最大，用户点击页面任何东西都不会有反应|
    document.body.appendChild(bgObj); //添加遮罩
    var loadingObj = document.createElement("div");
    loadingObj.setAttribute( 'id', 'loadingDiv' );
    loadingObj.style.position = "absolute";
    loadingObj.style.top = bodyHeight / 2 - 32 + "px";
    loadingObj.style.left = bodyWidth / 2 + "px";
    loadingObj.style.background = "url(/images/loadingAnimation.gif)" ;
    loadingObj.style.width = "124px";
    loadingObj.style.height = "124px";
    loadingObj.style.zIndex = "10000";
    document.body.appendChild(loadingObj); //添加loading动画-
};
ofDialog.removecloud = function() {
    $( "#loadingDiv").remove();
    $( "#bgDiv").remove();
};
//esup框架相关
var esupFrame = function() {
};
esupFrame.hideTab = function(){
    $("#tab_frame_ul").hide();
};
esupFrame.showTab = function(){
    $("#tab_frame_ul").show();
};

/**
 * 右下角弹出公共
 * add by fengsen
 */
artDialog.notice = function (options) {
    var opt = options || {},
        api, aConfig, hide, wrap, top,
        duration = 800;

    var config = {
        id: 'Notice',
        top: '100%',
        left: '98%',
        fixed: true,
        drag: false,
        resize: false,
        follow: null,
        lock: false,
        init: function(here){
            api = this;
            aConfig = api.config;
            wrap = api.DOM.wrap;
            top = parseInt(wrap[0].style.top);
            hide = top + wrap[0].offsetHeight;

            wrap.css('top', hide + 'px')
                .animate({top: top + 'px'}, duration, function () {
                    opt.init && opt.init.call(api, here);
                });
        },
        close: function(here){
            wrap.animate({top: hide + 'px'}, duration, function () {
                opt.close && opt.close.call(this, here);
                aConfig.close = $.noop;
                api.close();
            });

            return false;
        }
    };

    for (var i in opt) {
        if (config[i] === undefined) config[i] = opt[i];
    };

    return artDialog(config);
};