<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>操作日志</title>
    <link rel="stylesheet" href="http://pic.ofcard.com/ofss/css/page.css">
    <!-- 线上CSS -->
    <link rel="stylesheet" type="text/css" href="/css/crm-msg.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui-1.10.3.custom.min.css"/>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="http://pic.ofcard.com/ofss/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/js/jquery.dataTables.init.js"></script>
    <script type="text/javascript" src="/js/handlebars1.3.0.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/ofcs.js"></script>
    <script type="text/javascript" src="/js/handlebars1.3.0.js"></script>
</head>
<body>
<div class="page-wrapper">
    <!--面包屑-->
    <ol class="breadcrumb">
        <li>
            <a href="">首页</a>
        </li>
        <li>
            <a href="">工作面板</a>
        </li>
        <li>操作记录</li>
    </ol>
    <!--检索条件-->
    <div class="section">
        <form id="searchForm">
            <div class="form-search">
                <ul class="form-row">
                    <li class="item">
                        <div class="input-group">
                            <span class="input-group-addon">编号</span>
                            <input type="text" name="operatorId" id="operatorId" class="form-control" placeholder="人员编号">
                        </div>
                    </li>
                    <li class="item">
                        <div class="input-group">
                            <span class="input-group-addon">功能</span>
                            <input type="text" name="actionName" id="actionName" class="form-control" placeholder="功能名称">
                        </div>
                    </li>
                    <li class="item">
                        <div class="input-group">
                            <span class="input-group-addon">访问时间段</span>
                            <input type="text" name="startTime" id="startTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})" class="form-control" placeholder="开始时间">
                            <span class="input-group-addon" style="background-color: #f7f7f9; border: none;padding: 5px 1px;">-</span>
                            <input type="text" name="endTime" id="endTime"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})" class="form-control" placeholder="结束时间">
                        </div>
                    </li>
                    <li class="item">
                        <a href="javascript:void(0);" id="search" class="btn btn-primary">检索</a>
                        <input type="reset" class="btn btn-default">
                    </li>
                </ul>
            </div>
        </form>
        <div class="dataTables_wrapper pusht">
            <table id="logList"
                   class="table table-bordered table-striped table-hovered table-centered pusht table-insert" style="font-size: 12px;">
                <thead>
                <tr style="font-size:1.2em;">
                    <th>人员编号</th>
                    <th>功能名称</th>
                    <th>操作者</th>
                    <th>访问URL</th>
                    <th>访问参数</th>
                    <th>访问时间</th>
                    <th>访问IP</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var _currentDate =new Date();
        $('#startTime').val((_currentDate.getYear() +1900)+"-"+(_currentDate.getMonth()+1)+"-"+ _currentDate.getDate()+" 00:00:00");
        $('#endTime').val((_currentDate.getYear() +1900)+"-"+(_currentDate.getMonth()+1)+"-"+ _currentDate.getDate()+" 23:59:59");
        $("#logList").dataTables({
            "sAjaxSource": "/operatelog/query",
            "bSort": false,
            "fnServerData": function (sSource, aoData, fnCallback) {
                $("#logList_paginate").hide();
                var postData = aoData.concat($('#searchForm').serializeArray());
                $.post(sSource, postData, function (json) {
                    if (json.result === "success") {
                        $.each(json.data.aaData, function (_, item) {
                            item['DT_RowId'] = item.id;
                        });
                        fnCallback(json.data);
                    } else {
                        $("#logList tbody").html('<tr style="text-align: center;"><td colspan="5">获取数据失败！</td></tr>');
                        dialog_simple_ok("获取拼列表失败!", false, "145px");
                    }
                });
            },
            "fnDrawCallback": function () {
                $("#logList_paginate").show();
            },
            "aoColumns": [
                {
                    "sName": "operatorId",
                    mDataProp: "operatorId",
                    "bSortable": false
                },
                {
                    "bSortable": false,
                    mDataProp: function (aData, type, val) {
                        var _length =12;
                        var _text =(typeof(aData.params) == 'undefined') ? "..." : aData.actionName.toString();
                        var _shortTxt =_text.length > _length ? _text.substr(0,_length)+"..." : _text ;
                        var _a ="<a style='text-decoration:none;color:#000' title='"+_text +"' >"+_shortTxt+"</a>";
                        return _a;
                    }
                },
                {
                    "sName": "operatorName",
                    mDataProp: "operatorName",
                    "bSortable": false
                },
                {
                    "bSortable": false,
                    mDataProp: function (aData, type, val) {
                        var _length =23;
                        var _text =(typeof(aData.params) == 'undefined') ? "..." : aData.actionUrl.toString();
                        var _shortTxt =_text.length > _length ? _text.substr(0,_length)+"..." : _text ;
                        var _a ="<a style='text-decoration:none;color:#000' title='"+_text +"' >"+_shortTxt+"</a>";
                        return _a;
                    }
                },
                {
                    "bSortable": false,
                    mDataProp: function (aData, type, val) {
                        var _length =50;
                        var _text =(typeof(aData.params) == 'undefined') ? "..." : aData.params.toString()
                        var _extFlat =false ;
                        var _shortTxt ;
                        if(_text.length > _length)
                            _extFlat=true;
                        if(_extFlat)
                            _shortTxt =_text.substr(0,_length)
                        else
                            _shortTxt=_text;
                        var _a ="<a style='text-decoration:none;color:#000' title='"+_text +"' >"+_shortTxt+"</a>";
                        var _more ="<a href='javascript:' onclick='popParams(this)'>&nbsp;更多...</a>";
                        return  _extFlat ? _a+_more:_a;
                    }
                },
                {
                    "sName": "operateTime",
                    mDataProp: "operateTime",
                    "bSortable": false
                },
                {
                    "sName": "operateIp",
                    mDataProp: "operateIp",
                    "bSortable": false
                }
            ]
        });

        $('#logList thead th:first').removeClass('details-open-control');

        //搜索&刷新
        $("#search").click(function () {
           var _startTime= $('#startTime').val();
           var _endTime= $('#endTime').val();
            if(_startTime!= "" &&  _endTime!=""){//时间必选
                var _begDate =new   Date(Date.parse(_startTime.replace(/-/g,   "/")));
                var _endDate =new   Date(Date.parse(_endTime.replace(/-/g,   "/")));
                if(_begDate > _endDate){
                    dialog_simple_fail("开始时间不能大于结束时间！",false ,"145px");
                    return false ;
                }
                //时间不能跨天 1： 天应该一样， 2： 大减小， 在24小时内
                if(_begDate.toLocaleDateString() != _endDate.toLocaleDateString()){
                    dialog_simple_fail("开始时间与结束时间应在一天之内！",false ,"145px");
                    return false ;
                }
                $("#logList").refreshData();
            } else{
                dialog_simple_fail("请选择日志时间段（且在一天之内）！",false ,"145px");
                return false ;
            }
        });
    });

    /**
    * 弹出全部参数信息在
    * @param obj
     */
    function popParams(obj){
        dialog_prompt({title:'更多信息',content:$(obj).prev().attr('title'),isParent:true,height:350,width:550});
    }
    </script>
</body>
</html>
