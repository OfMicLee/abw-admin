<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title> 用户管理 </title>
    <link rel="stylesheet" href="http://pic.ofcard.com/ofss/css/page.css"/>
    <link rel="stylesheet" type="text/css" href="/css/crm.css"/>
    <link rel="stylesheet" type="text/css" href="/css/crm-msg.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui-1.10.3.custom.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/ztree/zTreeStyle.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery.ui.menu.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery.ui.autocomplete.css"/>
    <style type="text/css">
        .highColor {
            color: #08C;
            font-weight: bold;
        }
    </style>

    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="/js/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="/js/jquery.ui.menu.js"></script>
    <script type="text/javascript" src="/js/jquery.ui.autocomplete.js"></script>
    <script type="text/javascript" src="/js/ofcs.js" charset="utf-8"></script>
</head>
<body>
<div id="wrapper">
    <!--面包屑-->
    <ol class="breadcrumb">
        <li>
            <a href="">首页</a>
        </li>
        <li>
            <a href="">工作面板</a>
        </li>
        <li>用户管理</li>
    </ol>

    <div class="section">
        <form id="searchForm">
            <div class="form-search">
                <ul class="form-row">
                    <li class="item">
                        <div class="input-group">
                            <input type="text" name="inp_emp" id ="inp_emp" class="form-control" placeholder="工号 姓名">
                        </div>
                    </li>
                    <li class="item">
                        <a href="javascript:void(0);" id="search" class="btn btn-primary">搜索用户</a>
                        <abw:securityUrl url="/employee/lock">
                            <a href="javascript:void(0);" id="lock_emp" class="btn btn-primary">修改用户锁定状态</a>
                        </abw:securityUrl>
                        <abw:securityUrl url="/employee/delete">
                            <a href="javascript:void(0);" id="remove_emp" class="btn btn-primary">删除用户</a>
                        </abw:securityUrl>
                    </li>
                </ul>
            </div>
        </form>
    </div>

    <table style="margin-top:20px; width: 100%">
        <tbody>
        <tr height="15px"> <td colspan="5"></td></tr>
        <tr>
            <td width="15%" style="vertical-align: top;">
                <form id="form_emp" style="border: 1px solid #ddd;">
                    <div style="height: 400px; overflow-y: auto; margin-top: 5px;" id="emps">
                        <ul>
                        <c:forEach items="${employeeList}" var="emp" varStatus="status">
                            <li style="margin-left: 5px;">
                                <input name="emps" value="${emp.empNo}" data-name="${emp.empName}" data-lock="${emp.locked}" class="input-c" style="margin: 5px 2px 5px 10px;display: none;" type="checkbox">
                                <label for="" class="inline-label" style="cursor: pointer;<c:if test="${emp.locked eq 1}">background-color:red;</c:if>" >${emp.empNo}&nbsp;&nbsp;${emp.empName}</label><br>
                            </li>
                        </c:forEach>
                        </ul>
                    </div>
                </form>
            </td>
            <td width="20%" style="vertical-align: top">
                <form id="form_dept">
                    <table class="table table-bordered table-striped" style="margin: 0; height: 400px;" id="dept_table">
                        <thead>
                        <tr style="height: 20px;">
                            <th id="userToDept">用户对应的部门：<img src="/images/ajax-loader-small-snake.gif" id="loading_dept" style="display: none; "  /></th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${deptList}" var="dept" varStatus="status">
                            <tr>
                                <td>
                                    <input name="deptId" id="deptId_${dept.id}" value="${dept.id}" class="input-c" type="radio" style="display: none;">&nbsp;
                                    <label for="" class="inline-label" style="cursor: pointer">${dept.name}</label>
                                </td>
                            </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3" class="centered">
                                    <abw:securityUrl url="/employee/saveDeptRel">
                                        <a href="javascript:void(0);" id="saveDeptRel" class="btn btn-primary" style="display: none">保存</a>
                                    </abw:securityUrl>
                                    <a href="javascript:void(0);" id="resetDeptRel" class="btn btn-warning" style="display: none">重置</a>
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </form>
            </td>
            <td style="vertical-align: top">
                <form id="form_rights" style=" border: 1px solid #ddd;">
                <table class="table form" style="margin: 0; height: 400px;" >
                    <thead>
                    <tr style="height: 20px;">
                        <th style="text-align: left;" id="userOrDeptRights">用户对应的权限：<img src="/images/ajax-loader-small-snake.gif" id="loading_rights" style="display: none;"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="vertical-align: top;">
                            <ul id="rightsTree" class="ztree" >
                            </ul>
                            <input type="hidden" name="rights" />
                        </td>
                    </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3" class="centered">
                                <abw:securityUrl url="/dept/saveRightsIds">
                                    <a href="javascript:void(0);" id="saveRights" class="btn btn-primary" style="display: none">保存</a>
                                </abw:securityUrl>
                                <a href="javascript:void(0);" id="resetRights" class="btn btn-warning" style="display: none">重置</a>
                            </td>
                        </tr>
                    </tfoot>
                </table>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script type="text/javascript">
$(function() {

    initTree();

    var zTree;
    function initTree() {

        var _rightsList = ${rightsList};
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey:"id",
                    pIdKey:"parentId",
                    rootPId:0
                },
                key:{
                    url:""
                }
            },
            check: {
                enable: true,
                chkboxType: { "Y": "ps", "N": "s" }
            }
        };

        $.fn.zTree.init($("#rightsTree"), setting, _rightsList);
        zTree = $.fn.zTree.getZTreeObj("rightsTree");
        // zTree.expandAll(true);
    }

    $(document).ajaxStart(function(){
        $('#loading_dept').show();
        $('#loading_rights').show();
    }).ajaxStop(function(){
        $('#loading_dept').hide();
        $('#loading_rights').hide();
    });


    // 企业QQ提示效果
    var values = new Array();
    // 获取人员信息数组
    $("input[name=emps]").each(function() {
        var value = $(this).next().html();
        values.push(value.replace("&nbsp;"," ").replace("&nbsp;"," "));
    });
    $("#inp_emp").autocomplete({
        source: values,
        delay: 0,
        minLength: 0,
        maxHeight: 20,
        // 选中某项时执行的操作
        select: function( event, ui ) {
            // ui.item.label表示选中项的显示文本
            // ui.item.value表示选中项的值
            $("#inp_emp").val(ui.item.label.split("  ")[0]);
            var num = 0;
            $("input[name=emps]").each(function() {
                if($(this).val() == $("#inp_emp").val() ) {
                    $(this).next().click();
                    emps.scrollTop =  10 * num;
                } else {
                    // 没有查到
                    $(this).prop("checked",false);
                    num += 1;
                }
            });
        }
    });

    // 搜索(点击搜索按钮触发)(支持输入工号。姓名)
    $('#search').click(function() {
        var value = $("#inp_emp").val();
        if (value.length === 0) {
            return false;
        }
        var searchFlag = false;
        var num = 0;
        $("input[name=emps]").each(function() {
            if ($(this).next().html().replace('&nbsp;', '').replace('&nbsp;', '').indexOf(value.replace(' ', '').replace(' ', '')) >= 0) {
                $(this).next().click();
                searchFlag = true;
                emps.scrollTop =  10 * num;
            } else {
                // 没有查到
                $(this).prop("checked",false);
                num += 1;
            }
        });

        if (!searchFlag) {
            dialog_simple_fail("用户不存在", false, "105px");
        }
    });

    // 修改用户锁定状态
    $('#lock_emp').on('click', function() {
        var empNo = $("input[name=emps]:checked").val();
        if (empNo === undefined) {
            dialog_simple_fail('没有选择用户！', false, "105px");
            return false;
        }
        var empName = $("input[name=emps]:checked").attr("data-name");
        var locked = $("input[name=emps]:checked").attr("data-lock");
        var lockStr = 0 == locked ? "锁定" : "未锁定";

        var params = {
            empNo: empNo,
            locked: locked == 0 ? 1 : 0
        }
        dialog_confirm({content: "确定修改用户 " + empNo +" " + empName + " 的状态为 " + lockStr + " 吗？", yes:function(){
            $.post("/employee/lock", params, function(json) {
                if (json.result == 'ok') {
                    dialog_prompt({
                        content:"修改成功!",
                        width: 340,
                        height: 180,
                        yes: function(){
                            window.location.href = "/employee/index";
                        }
                    });
                } else {
                    dialog_prompt({
                        content: json.msg,
                        width: 340,
                        height: 180,
                        yes: function(){
                        }
                    });
                }
            });
        }});
    });

    // 删除用户
    $('#remove_emp').click(function() {
        var empNo = $("input[name=emps]:checked").val();
        if (empNo === undefined) {
            dialog_simple_fail('没有选择用户！', false, "105px");
            return false;
        }
        var empName = $("input[name=emps]:checked").attr("data-name");

        dialog_confirm({content: "确定要删除用户 " + empNo +" " + empName + " 吗？", yes:function(){
            $.post("/employee/delete", {empNo: empNo}, function(json) {
                if (json.result == 'ok') {
                    dialog_prompt({
                        content:"删除成功!",
                        width: 340,
                        height: 180,
                        yes: function(){
                            window.location.href = "/employee/index";
                        }
                    });
                } else {
                    dialog_prompt({
                        content: json.msg,
                        width: 340,
                        height: 180,
                        yes: function(){
                        }
                    });
                }
            });
        }});
    });

    // 点击用户
    // 自己不可以修改自己的部门，账户锁定状态，自己不能删除自己
    $("#form_emp").on('click', '.inline-label', function() {
        $("input[name=emps]").prop("checked",false).css("display","none").next().removeClass("highColor");

        $(this).addClass("highColor").prev().prop("checked",true);

        $(".input-append input").val($(this).prev().val());
        var empNo = $(this).prev().val();
        var name = $(this).prev().data('name');
        if (empNo === "${sessionScope.empNo}") {
            $("#lock_emp").hide();
            $("#remove_emp").hide();
            $("input:radio[name='deptId']").prop("disabled", true);
            $("#saveDeptRel").hide();
            $("#resetDeptRel").hide();
        } else {
            $("#lock_emp").show();
            $("#remove_emp").show();
            $("input:radio[name='deptId']").prop("disabled", false);
            $("#saveDeptRel").show();
            $("#resetDeptRel").show();
        }
        $("#saveRights").hide();
        $("#resetRights").hide();

        showDept(empNo, name);
    });

    // 点击部门的时候，展现该部门对应的权限以及部门下的用户
    $("#dept_table").on('click', '.inline-label', function() {
        $("#lock_emp").hide();
        $("#remove_emp").hide();

        $("input:radio[name=deptId]").css("display","none").prop("checked",false).next().removeClass("highColor");
        $(this).addClass("highColor").prev().prop("checked",true);

        var deptId = $(this).prev().val();
        var deptName = $(this).html();

        $("#saveRights").show();
        $("#resetRights").show();
        $("#saveDeptRel").hide();
        $("#resetDeptRel").hide();

        showEmpsAndPermissions(deptId, deptName)
    });

    // 初始化用户对应部门和权限的方法
    function showDept(empNo, empName) {
        var $box_dept = $("input:radio[name='deptId']").show().prop("checked", false);
        $("#userOrDeptRights").html("用户：" + empName + " —对应的权限：<img src='/images/ajax-loader-small-snake.gif' id='loading_rights' style='display: none;'/>");
        $box_dept.each(function() {
            $(this).next().removeClass("highColor");
        });
        $.post("/employee/getDept", {empNo: empNo}, function(json) {
            if (json.result == 'ok') {
                if (json.data) {
                    var id = "deptId_" + json.data.id;
                    $("input:radio[id=" + id + "]").prop("checked", true).next().addClass("highColor");
                }
                checkRights($("input:radio[name='deptId']:checked").val());
            } else {
                dialog_prompt({
                    content: json.msg,
                    width: 340,
                    height: 180,
                    yes: function(){
                    }
                });
            }
        });
    }

    function showEmpsAndPermissions(deptId, deptName) {
        $("#userOrDeptRights").html("部门：" + deptName + " —对应的权限：<img src='/images/ajax-loader-small-snake.gif' id='loading_rights' style='display: none;'/>");

        $("input[name=emps]").show().prop("checked",false).prop("disabled",true).next().removeClass("highColor");

        // 初始化部门对应的用户
        $.post("/dept/listEmployee",{id: deptId},  function(json) {
            $.each(json.data, function (i, empNo) {
                $("input[name=emps]").each(function() {
                    if ($(this).val() == empNo) {
                        $(this).prop("checked",true).prop("disabled",false).next().addClass("highColor");
                    }
                });
            });
        });

        // 初始化部门对应的权限
        checkRights(deptId);
    }

    // 保存用户与部门的关系
    $("#saveDeptRel").click(function () {
        var empNo = $("input[name=emps]:checked").val();
        var deptId = $("input:radio[name='deptId']:checked").val();
        if (empNo && deptId) {
            $.post("/employee/saveDeptRel", {empNo: empNo, deptId: deptId}, function(json) {
                if (json.result == 'ok') {
                    dialog_prompt({
                        content: "保存成功",
                        width: 340,
                        height: 180,
                        yes: function(){
                            showDept(empNo, $("input[name=emps]:checked").attr("data-name"));
                        }
                    });
                } else {
                    dialog_prompt({
                        content: json.msg,
                        width: 340,
                        height: 180
                    });
                }
            });
        } else {
            dialog_simple_fail("请选择部门和用户", false, "105px");
        }
    });

    // 给部门下的重置按钮绑定事件
    $("#resetDeptRel").click(function() {
        var empNo = $("input[name=emps]:checked").val();
        var empName = $("input[name=emps]:checked").data('name');
        if (empNo !== undefined) {
            showDept(empNo, empName);
        }
    });

    // 选中部门关联的权限
    function checkRights(id){
        // 清空已选中权限
        zTree.checkAllNodes(false);

        // 查询选中的部门关联的权限信息
        var deptId = $("input:radio[name='deptId']:checked").val();
        if (deptId) {
            $.post("/dept/listRightsIds", {id: deptId}, function(json) {
                if (json.result == 'ok') {
                    $.each(json.data, function (_, rightsId){
                        var node = zTree.getNodeByParam("id", rightsId, null);
                        if (node) {
                            zTree.checkNode(node, true, false, false);
                        }
                    })
                } else {
                    dialog_prompt({
                        content: json.msg,
                        width: 340,
                        height: 180
                    });
                }
            });
        }
    }

    // 保存部门与权限的关系
    $("#saveRights").click(function (){
        // 选中的部门
        var deptId = $("input:radio[name='deptId']:checked").val();
        if (deptId) {
            // 选中的权限
            var checkedNodes = zTree.getCheckedNodes(true);
            var rightsIds = [];
            $.each(checkedNodes, function (_, node){
                rightsIds.push(node.id);
            });

            var params = {
                id: deptId,
                rightsIds: rightsIds
            };
            $.post("/dept/saveRightsIds", params, function(json) {
                if (json.result == 'ok') {
                    dialog_prompt({
                        content: "保存成功",
                        width: 340,
                        height: 180
                    });
                } else {
                    dialog_prompt({
                        content: json.msg,
                        width: 340,
                        height: 180
                    });
                }
            });
        }
    });

    // 给权限下的重置按钮绑定事件
    $("#resetRights").click(function() {
        var deptId = $("input[name=deptId]:checked").val();
        var deptName = $("input[name=deptId]:checked").next().html();
        showEmpsAndPermissions(deptId, deptName);
    });

});
</script>
</body>
</html>