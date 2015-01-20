<%@ taglib prefix="abw" uri="http://www.allbuywine.com/tags" %>
<%--
  User: hufeng
  Date: 14-7-1
  Time: 下午3:55
  Desciption:部门首页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门管理</title>
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
        <li>部门列表</li>
    </ol>
    <!--检索条件-->
    <div class="section">
        <form id="searchForm">
            <div class="form-search">
                <ul class="form-row">
                    <li class="item">
                        <div class="input-group">
                            <span class="input-group-addon">部门名称</span>
                            <input type="text" name="name" id="name" class="form-control" placeholder="部门名称">
                        </div>
                    </li>
                    <li class="item">
                        <a href="javascript:void(0);" id="search" class="btn btn-primary">检索</a>
                    </li>
                </ul>
            </div>
        </form>
        <div class="toolbar">
            <div class="btn-group left">
                <abw:securityUrl url="/dept/form">
                    <a href="/dept/form" id="add" class="btn btn-default">
                        <i class="icon icon-plus-square"></i>
                        添加部门
                    </a>
                </abw:securityUrl>
            </div>
        </div>
        <div class="dataTables_wrapper pusht">
            <table id="deptList"
                   class="table table-bordered table-striped table-hovered table-centered pusht table-insert" style="font-size: 12px;">
                <thead>
                <tr style="font-size:1.2em;">
                    <th><input type="checkbox" id="chk"></th>
                    <th>部门编号</th>
                    <th>部门名称</th>
                    <th>部门描述</th>
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
    /**
     *
     * TODO 考虑抽取出来
     * 封装checkbox选中的数据的操作
     */
    var selector = {
        data: [],
        add: function (id) {
            if (this.data.indexOf(id) === -1) {
                this.data.push(id);
            }
        },
        remove: function (id) {
            var index = this.data.indexOf(id);
            if (index != -1) {
                this.data.splice(index, 1);
            }
        }
    };
    $(function () {

        $("#deptList").dataTables({
            "sAjaxSource": "/dept/query",
            "bSort": false,
            "fnServerData": function (sSource, aoData, fnCallback) {
                $("#deptList_paginate").hide();
                var postData = aoData.concat($('#searchForm').serializeArray());
                $.post(sSource, postData, function (json) {
                    if (json.result === "success") {
                        $.each(json.data.aaData, function (_, item) {
                            item['DT_RowId'] = item.id;
                        });
                        fnCallback(json.data);
                    } else {
                        $("#deptList tbody").html('<tr style="text-align: center;"><td colspan="5">获取数据失败！</td></tr>');
                        dialog_simple_ok("获取拼列表失败!", false, "145px");
                    }
                });
            },
            "fnDrawCallback": function () {
                $("#deptList_paginate").show();
                // 注册checkbox的监听,放在这个地方监听是因为dom是异步生成的。
                $(":checkbox", $("#deptList tbody")).click(function () {
                    var status = $(this).prop("checked");
                    var value = $(this).val();
                    if (status) {
                        selector.add(value);
                    } else {
                        selector.remove(value);
                    }
                });

                //每页显示的数量pageSize
                var pageSize = this.fnGetData().length;
                //页面选中的checkbox的数量
                var checkedNum = 0;
                $.each(selector.data, function (index, item) {
                    $(":checkbox", $("#deptList tbody")).each(function () {
                        if ($(this).prop("value") === item) {
                            $(this).prop('checked', true);
                            checkedNum++;
                        }
                    });
                });
                //如果当前checkbox选中的数量和页面显示的数量相同，说明是全选，将全选的框勾上
                if (checkedNum === pageSize) {
                    $("#chk").prop("checked", true);
                } else {
                    $("#chk").prop("checked", false);
                }
            },
            "aoColumns": [
                {
                    "bSortable": false,
                    mDataProp: function (aData, type, val) {
                        return html("#checkbox-template", {id: aData.id});
                    }
                },
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
                    "sName": "description",
                    mDataProp: "description",
                    "bSortable": false
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

        $('#deptList thead th:first').removeClass('details-open-control');

        //check全选
        $("#chk").click(function () {
            var checked = $(this).prop("checked");
            $.each($("#deptList input[name='id']"), function () {
                var val = $(this).val();
                if (checked) {
                    selector.add(val);
                } else {
                    selector.remove(val);
                }
                $(this).prop("checked", checked);
            });
        });

        //搜索&刷新
        $("#search").click(function () {
            $("#deptList").refreshData();
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
        window.location.href = "/dept/form?id=" + id;
    }

    /**
     * 删除单个部门
     * @param id
     */
    function del(id){
        dialog_confirm({content: "确定要删除该部门吗？", yes:function(){
            $.post('/dept/delete', {id: id})
                .done(function(res) {
                    if (res.result == 'ok') {
                        $("#deptList").refreshData();///删除成功后更新表单中的数据
                    } else {
                        dialog_simple_fail(res.msg, false, '130px');
                    }
                })
                .fail(function() {
                    dialog_simple_fail('网络错误', false, '130px');
                });
        }});
    }

</script>

<!--列表的最后一列， 操作-->
<script id="html-template" type="text/x-handlebars-template">
    <div>
        <abw:securityUrl url="/dept/form">
            <a href="javascript:edit('{{id}}');" class="btn btn-default toggle-detail">
                <i class="icon icon-edit"></i>修改
            </a>
        </abw:securityUrl>
        <abw:securityUrl url="/dept/delete">
            <a href="javascript:del('{{id}}');" class="btn btn-default toggle-detail">
                <i class="icon icon-trash-o"></i>
                删除
            </a>
        </abw:securityUrl>
    </div>
</script>
<!-- 列表中选择框-->
<script id="checkbox-template" type="text/x-handlebars-template">
    <input type="checkbox" name="id" value="{{id}}"/>
</script>
</body>
</html>
