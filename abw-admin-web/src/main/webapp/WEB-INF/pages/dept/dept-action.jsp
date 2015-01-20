<%--
  User: hufeng
  Date: 14-7-1
  Time: 下午3:56
  Desciption:添加修改部门信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
    <title>部门</title>
    <link rel="stylesheet" href="http://pic.ofcard.com/ofss/css/page.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui-1.10.3.custom.min.css"/>
    <style>
        label {
            font-size: 12px;
            font-weight: bold;
        }
    </style>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="http://pic.ofcard.com/ofss/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/ofcs.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.min.js"></script>
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
        <li>部门</li>
        <li><c:choose><c:when test="${empty dept.id}">添加部门</c:when><c:otherwise>修改部门</c:otherwise></c:choose></li>
    </ol>

    <div class="section">
        <fieldset>
            <legend>部门管理</legend>
            <input name="id" type="hidden" id="id" value="${dept.id}"/>
            <div id="msg" class="alert alert-danger" style="display: none;"></div>
            <table class="form">
                <tr>
                    <td class="label-td"><label for="name">部门名称<span class="text-danger">*</span>:</label></td>
                    <td>
                        <input type="text" name="name" id="name" value="${dept.name}" maxlength="32"/>
                        <span id="name-info" class="text-danger"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label-td"><label for="description">部门描述:</label></td>
                    <td>
                        <textarea name="description" id="description" style="width: 60%; height: 50px;">${dept.description}</textarea>
                        <span id="desc-info" class="text-danger"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label-td"></td>
                    <td>
                        <a href="javascript:void(0);" id="action" class="btn btn-primary">确定</a>
                        <a href="javascript:void(0);" id="cancel" onclick="window.history.go(-1)" class="btn btn-danger">取消</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
</div>
<script type="text/javascript">
    $(function() {
        $("#name").on('blur', function() {
            var id = $.trim($('#id').val());
            var name = $.trim($("#name").val());
            var $nameInfo = $("#name-info");

            $.post('/dept/validate', {id: id, name: name}).done(function(res) {
                if (res) {
                    $nameInfo.text('部门名称重复');
                    $nameInfo.focus();
                } else {
                    $nameInfo.text('');
                }
            });
        });


        //save
        $("#action").on('click', function() {
            var id = $.trim($('#id').val());
            var name = $.trim($("#name").val());
            var description = $.trim($("#description").val());
            var $nameInfo = $("#name-info");
            var $descInfo = $("#desc-info");

            if (name == '') {
                $nameInfo.text('部门名称必填');
                return false;
            } else {
                if (name.length > 32) {
                    $nameInfo.text('部门名称不能超过32个字符');
                    return false;
                }
                $nameInfo.text("");
            }

            if (description.length > 64) {
                $descInfo.text('描述信息超长');
                return false;
            } else {
                $descInfo.text('');
            }


            var url = (id == '') ? "/dept/save" : "/dept/update";
            $.post(url, {
                id: id,
                name: name,
                description: description
            }).done(function(res) {
                if (res.result == 'ok') {
                    dialog_prompt({
                        content:"操作成功!",
                        yes: function(){
                            window.location.href = "/dept/index";
                        }
                    });
                } else {
                    $("#msg").text(res.msg).show();
                }
            }).fail(function() {
                $("#msg").text('网络异常，请重试').show();
            });
        });
    });
</script>
</body>
</html>
