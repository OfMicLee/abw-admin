

//levelNum
//根据窗口宽度调整类目显示级数
//1280窗口以下时，显示3个类目
//1280窗口或以上时，显示4个类目

function TreeSelect() {
//移动方法
    this.movenum = 0;
    this.curLevelCode = 0;
    this.levelNum=3;//初始值
    var _this =this ;
    this.changeWindowSize();
    $(window).resize(function () {
        _this.changeWindowSize();
    });
}

TreeSelect.prototype.changeWindowSize = function() {
    var windoWidth = $(window).width();
    if (windoWidth <= 1280) {
        $(".scroll").css("width", "866px");
        $(".scrollContainer .mbody").css("width", "760px");
        this.levelNum = 3;
        this.move();
    } else {
        $(".scroll").css("width", "1118px");
        $(".scrollContainer .mbody").css("width", "1010px");
        this.levelNum = 4;
        this.move();
    }
}

/**
 * 初始化时，加载一级目录。 URL读取config.
 * @param config { rowDrawCallBackFunc(liItem,rowdata)}
 */
TreeSelect.prototype.init = function(config) {
    var targetObject =$(config.targetObject); //
   // targetObject = $('#treeSeleTest') ;//TODO 测试 使用
    var datas =config.data;

    var _rowDrawCallBackFunc=config.rowDrawCallBackFunc ; //TODO, 为空时判断.

    var rows = $('<div class="categoryList-wrapper" style="margin-top: 0px; height: 284px;width:235px"><ul style="height: 284px;"></ul></div>');

    for (var i = 0; i < datas.length; i++) {
        var code = datas[i].id;
        var name = datas[i].name;
        var hasSup = datas[i].endMark;
        var setType = datas[i].priceType;
        var row  = '<li class="with-child index" tempId=' + code + ' id=' + code + ' setType=' + setType + '><a ';
        row += ' class="more"';
        row += ' >' + name + '</a></li>';

        var _row =$(row) ;
        var _dataRow =datas[i] ;
        _rowDrawCallBackFunc.call(this,_row,_dataRow); // 调用回调函数
        rows.find('ul').append(_row) ;
    }
    var content  = init1(this);

    content.find('ul#goods1level').append(rows);
    targetObject.html(content);
   //加两个事件  movLeftClick  movRightClick TODO
    // 搜索框
    this.move();

};

/**
 * 添加下一级别的selector
 * @param config
 */
TreeSelect.prototype.addNextLevelSelector = function(config) {
    var levelCode = $(config.clickLiItem).parents(".ofSerItem").attr("levelcode");
    var currentLevelCode = parseInt(levelCode) + 1 ;
    this.curLevelCode=currentLevelCode;
    var datas =config.data;
    var _rowDrawCallBackFunc=config.rowDrawCallBackFunc ; //TODO, 为空时判断.

      // 如果这个有下级selector则删除他们
    while (true) {
        $(config.clickLiItem).parents(".ofSerItem").next().remove();
        if ($(config.clickLiItem).parents(".ofSerItem").parents("ul").find(".ofSerItem").size() <= levelCode) {
            break;
        }
    }


    var liHeml = '<li class="ofSerItem S" levelcode="' + currentLevelCode + '"><div class="search" style="width: 245px; height: 20px;">' +
        '<span class="placeholder"><input id="keyword' + currentLevelCode + '" class="input-t" style="width: 206px;" type="text" value="输入名称查找" oninput="filte(' + currentLevelCode
        + ')" onpropertychange="filte(' + currentLevelCode + ')" onblur="setSearchTip(this)" onclick="clearValue(this)"/></span><a class="search-btn"></a>' +
        '</div><div class="ofSerDIV"><div class="categoryList-wrapper" style="height: 245px;"><ul style="height: 245px; position: relative;" id="nav' + currentLevelCode + '">'
        +'</ul></div></div></li>';

    var liContents=$(liHeml);

    for (var i = 0; i < datas.length; i++) {
        var row ="";

        var itemId= "";
        var itemName = "";
        if(config.dataMap){
            if(typeof(config.dataMap.itemId) != 'undefined')
             itemId=datas[i][config.dataMap.itemId];
            if(typeof(config.dataMap.itemName) != 'undefined')
            itemName=datas[i][config.dataMap.itemName];
        }else{
             itemId=  datas[i].id;
             itemName =  datas[i].name;
        }

        var pinyin = datas[i].pinyin;//全拼
        var spinyin = datas[i].spinyin;//首字母缩写


        var priceType = "" ; //datas[i].priceType; //TODO , 第二个 ， 要处理下。 这个在自己的里面处理。

        if ($.trim(itemName) != '') {
            row += '<li class="with-child index"';
            row += ' tempId="' + itemId + '" id="' + itemId + '" setType="' + priceType + '"><a';
            row += ' class="more"';
            row += '>' + itemName + '</a><samp style="display:none">' + itemName + '</samp></li>';

        }
        var _row =$(row) ;
        if($.trim(itemName) != '')

        if($.trim(itemName) != '' &&  typeof(pinyin)!="undefined"  &&   typeof(spinyin)!="undefined" &&   $.trim(pinyin) != ""  && $.trim(spinyin) != '' ){
        //TODO 添加拼音首字母功能 beg
        var firstWordFlag = false;
        var firstCharacter = itemName.substring(0, 1);
        var firstWord = pinyin.substring(0, 1).toUpperCase();
            if (judgeChiCharacter(firstCharacter)) {
                if (i == 0) {//第一个数据， 不加
                    firstWordFlag = true;
                } else {
                    var parFirstWord = datas[i-1].pinyin.substring(0, 1).toUpperCase();// TODO rowdata 原来是指向上一个， 搞清这个是什么问题。
                    if (firstWord != parFirstWord) {
                        firstWordFlag = true;
                    }
                }
            }
            if (firstWordFlag) {
                _row.find('a').prepend('<span class="index-tag">' + firstWord + '</span>');
               // liItem.removeClass("index");
            }else{

            }
             //修改后缀的。
            _row.find('samp').html(itemName + '~' + pinyin + '~' + spinyin);
        }
        //TODO 添加拼音首字母功能 end



        _rowDrawCallBackFunc.call(this,_row,datas[i],i); // 调用回调函数
        liContents.find('ul').append(_row);

    }

    $(config.clickLiItem).parents(".ofSerItem").after(liContents);
    this.move();
}



//判断是否为汉字
function judgeChiCharacter(str) {
    var re = /^[\u4E00-\u9FA5\uF900-\uFA2D]*$/;
    return (re.test(str));
}




/**
 * 初始化第一个select，但不包含select 中的选项值  返回dom
 */
function init1(_treeSelector){
    var parentHtml ='<div class="scroll">'
        +'        <div class="scrollContainer">'
        +'            <div class="mbody">'
        +'                <ul class="ofSerUl" style="margin-top: -5px;">'
        +'                    <li class="ofSerItem" levelcode="1">'
        +'                        <div class="ofSerDIV">'
        +'                            <ul id="goods1level">'

        +'                            </ul>'
        +'                        </div>'
        +'                    </li>'
        +'                </ul>'
        +'            </div>'
        +'        </div>'
        +'        <a class="abtn aleft agrayleft"></a><a class="abtn aright agrayright" ></a>'
        +'        <div class="category-selection-breadCrumb" id="path"><span class="gray">请选择标准类目</span></div>'
        +'    </div>';


    var _parentContain =$(parentHtml) ;
    _parentContain.find("a.abtn.aleft").on("click",function (){ //左移
        if (moveFlag == 0) {
            _treeSelector.moveRight();
            moveFlag = 1;
        }
    });
    _parentContain.find("a.abtn.aright").on("click",function (){ //右移
        if (moveFlag == 0) {
            moveFlag = 1;
            _treeSelector.moveLeft();
            setTimeout(function () { //这里的定时器是什么作用？
                moveFlag = 0;
            }, 1000);
        }
    });
    return _parentContain ;
}

var moveFlag = 0; //这个又是做什么用的 ？

/**
 * 通过关键字搜索
 * @param level 指定那个级别的selector，会用此参数拼成$("ul#nav" + level + " li a")
 */
TreeSelect.prototype.filte=function(level) {
    //IEbug修复
    if (navigator.userAgent.indexOf("MSIE") > 0) {
        document.getElementById("keyword" + level).focus();
    }

    var arr = $("ul#nav" + level + " li a");
    var srr = $("ul#nav" + level + " li samp");
    if (arr.length == 0) {
        return;
    }
    // 释放所有隐藏,去掉所有的颜色
    arr.css("display", "");
    arr.find("span").css("color", "");
    // 关键字
    var keyword = $("input:text#keyword" + level).val();
    if (keyword != "" && keyword != "输入名称/拼音首字母查找" && keyword != "输入名称查找") {
        for (var i = 0; i < srr.length; i++) {
            var obj = arr.eq(i);
            var sobj = srr.eq(i);
            // 不包含关键字
            if (sobj.html().indexOf(keyword) == -1) {
                // 同时未选中
                if (!obj.parent().hasClass("selected")) {
                    obj.css("display", "none");
                }
                // 包含关键字
            } else {
                obj.find("span").css("color", "red");
            }
        }
    }
}

//初始化搜索框
function initSearch(obj) {
    if ($(obj).attr('id') == "keyword2") {
        obj.value = "输入名称查找";
    } else {
        obj.value = "输入名称/拼音首字母查找";
    }
    $(obj).attr("style", "color:#ccc;");
}



/**
 * 三种情况下调用
 *    1.初始化时
 *    2.窗口大小变化
 *    3.添加子select
 */
TreeSelect.prototype.move =function() {
    //修正
    if ($(".ofSerItem").size() <= this.levelNum) {
        this.moveRight();
    }
    var liu = this.curLevelCode - this.movenum;
    if (liu == this.levelNum + 1) {
        this.moveLeft();
    } else if (liu == 1 && this.movenum != 0) {
        this.moveRight();
    }
}

TreeSelect.prototype.moveLeft =function () {
    if (this.movenum < ($(".ofSerItem").size() - this.levelNum)) {
        this.movenum++;
        $(".ofSerUl").animate({left: parseInt($(".ofSerUl").position().left) - 252}, "slow");
        this.changeState();
    }
}

TreeSelect.prototype.moveRight=function () {
    if (this.movenum > 0) {
        this.movenum--;
        var leftBtn = (parseInt($(".ofSerUl").position().left) + 252);
        $(".ofSerUl").animate({left: leftBtn}, "slow");
        this.changeState();
    }
}

TreeSelect.prototype.findAllTempIds=function () {
    var templis = $("li .selected");
    var a = "";
    for (var i = 0; i < templis.length; i++) {
        var tempId = $(templis[i]).attr("tempId");
        a = a + ";" + tempId;
    }
    $("#cid5pvs").val(a);
}

TreeSelect.prototype.changeState=function() {
    var len = $(".ofSerItem").size();
    if (this.movenum == 0) {
        if (len > this.levelNum) {
            $(".slideBtnL").hide();
            $(".slideBtnR").show();
        } else {
            $(".slideBtnL").hide();
            $(".slideBtnR").hide();
        }
    } else {
        $(".slideBtnL").show();
        $(".slideBtnR").show();
    }
}