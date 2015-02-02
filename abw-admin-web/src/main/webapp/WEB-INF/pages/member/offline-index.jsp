<%@ taglib prefix="abw" uri="http://www.allbuywine.com/tags" %>
<%--
  User: MicLee
  Date: 14-12-28
  Time: 下午3:55
  Desciption:会员首页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>线下会员管理</title>
    <link rel="stylesheet" href="http://pic.ofcard.com/ofss/css/page.css">
    <!-- 线上CSS -->
    <link rel="stylesheet" type="text/css" href="/css/crm-msg.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui-1.10.3.custom.min.css"/>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="http://pic.ofcard.com/ofss/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/js/jquery.dataTables.init.js"></script>
    <script type="text/javascript" src="/js/handlebars1.3.0.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/ofcs.js"></script>
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
        <li>线下会员列表</li>
    </ol>
    <!--检索条件-->
    <div class="section">
        <form id="searchForm">
            <div class="form-search">
                <ul class="form-row">
                    <li class="item">
                        <div class="input-group">
                            <span class="input-group-addon">姓名</span>
                            <input type="text" name="name" id="name" class="form-control" placeholder="会员姓名">
                        </div>
                    </li>
                    <li class="item">
                        <div class="input-group">
                            <span class="input-group-addon">注册时间段</span>
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
        <div class="toolbar">
            <div class="btn-group left">
                <abw:securityUrl url="/member/offline/form">
                    <a href="/member/offline/form" id="add" class="btn btn-default">
                        <i class="icon icon-plus-square"></i>
                        添加会员
                    </a>
                </abw:securityUrl>
            </div>
        </div>
        <div class="dataTables_wrapper pusht">
            <table id="memberList"
                   class="table table-bordered table-striped table-hovered table-centered pusht table-insert" style="font-size: 12px;">
                <thead>
                <tr style="font-size:1.2em;">
                    <th>编号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>手机</th>
                    <th>QQ</th>
                    <th>微信号</th>
                    <th>积分</th>
                    <th>注册时间</th>
                    <th>地址</th>
                    <th>操作</th>
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
        //默认时间段
//        $('#startTime').val((_currentDate.getYear() +1900)+"-"+(_currentDate.getMonth()+1)+"-"+ _currentDate.getDate()+" 00:00:00");
//        $('#endTime').val((_currentDate.getYear() +1900)+"-"+(_currentDate.getMonth()+1)+"-"+ _currentDate.getDate()+" 23:59:59");

        $("#memberList").dataTables({
            "sAjaxSource": "/member/offline/query",
            "bSort": false,
            "fnServerData": function (sSource, aoData, fnCallback) {
                $("#memberList_paginate").hide();
                var postData = aoData.concat($('#searchForm').serializeArray());
                $.post(sSource, postData, function (json) {
                    if (json.result === "success") {
                        $.each(json.data.aaData, function (_, item) {
                            item['DT_RowId'] = item.id;
                        });
                        fnCallback(json.data);
                    } else {
                        $("#memberList tbody").html('<tr style="text-align: center;"><td colspan="5">获取数据失败！</td></tr>');
                        dialog_simple_ok("获取拼列表失败!", false, "145px");
                    }
                });
            },
            "fnDrawCallback": function () {
                $("#memberList_paginate").show();
            },
            "aoColumns": [
                {
                    "sName": "id",
                    mDataProp: "id",
                    "bSortable": false
                },
                {
                    "sName": "name",
                    mDataProp: "name",
                    "bSortable": false
                },
                {
                    "sName": "sex",
                    mDataProp: "sex",
                    "bSortable": false
                },
                {
                    "sName": "mobile",
                    mDataProp: "mobile",
                    "bSortable": false
                },
                {
                    "sName": "qq",
                    mDataProp: "qq",
                    "bSortable": false
                },
                {
                    "sName": "weixin",
                    mDataProp: "weixin",
                    "bSortable": false
                },
                {
                    "sName": "point",
                    mDataProp: "point",
                    "bSortable": false
                },
                {
                    "sName": "registerDate",
                    mDataProp: "registerDate",
                    "bSortable": false
                },
                {
                    "sName": "address",
                    "bSortable": false,
                    mDataProp: function (aData, type, val) {
                        var _length =20;
                        var _text =(typeof(aData.address) == 'undefined') ? "..." : aData.address.toString()
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
                    "bSortable": false,
                    mDataProp: function (aData) {
                        if (1 === aData.id) {
                            return "";
                        } else {
                            return html("#html-template", {id: aData.id});
                        }
                    }
                }
            ]
        });

        $('#memberList thead th:first').removeClass('details-open-control');

        //搜索&刷新
        $("#search").click(function () {
            var _startTime= $('#startTime').val();
            var _endTime= $('#endTime').val();
            if(_startTime!= "" &&  _endTime!=""){//选择了时间
                var _begDate =new Date(Date.parse(_startTime.replace(/-/g,   "/")));
                var _endDate =new Date(Date.parse(_endTime.replace(/-/g,   "/")));
                if(_begDate > _endDate){
                    dialog_simple_fail("开始时间不能大于结束时间！",false ,"145px");
                    return false ;
                }
            }
            $("#memberList").refreshData();
        });


        /**
         * 得到handlerbars模板的html内容
         * @param templateId 模板的id，要带上#
         * @param data 模板的json数据
         * @returns {*}
         */
        function html(templateId, data) {
            var template = Handlebars.compile($(templateId).html());
            return template(data);
        }
    });

    function edit(id) {
        window.location.href = "/member/offline/form?id=" + id;
    }

    /**
     * 删除单个会员
     * @param id
     */
    function del(id){
        dialog_confirm({content: "确定要删除该会员吗？", yes:function(){
            $.post('/member/offline/delete', {id: id})
                .done(function(res) {
                    if (res.result == 'ok') {
                        $("#memberList").refreshData();///删除成功后更新表单中的数据
                    } else {
                        dialog_simple_fail(res.msg, false, '130px');
                    }
                })
                .fail(function() {
                    dialog_simple_fail('网络错误', false, '130px');
                });
        }});
    }


    /**
     * 弹出全部参数信息在
     * @param obj
     */
    function popParams(obj) {
        dialog_prompt({title:'更多信息',content:$(obj).prev().attr('title'),isParent:true,height:350,width:550});
    }
</script>

<!--列表的最后一列， 操作-->
<script id="html-template" type="text/x-handlebars-template">
    <div>
        <abw:securityUrl url="/member/offline/form">
            <a href="javascript:edit('{{id}}');" class="btn btn-default toggle-detail">
                <i class="icon icon-edit"></i>修改
            </a>
        </abw:securityUrl>
        <abw:securityUrl url="/member/offline/delete">
            <a href="javascript:del('{{id}}');" class="btn btn-default toggle-detail">
                <i class="icon icon-trash-o"></i>
                删除
            </a>
        </abw:securityUrl>
    </div>
</script>

</body>
</html>
