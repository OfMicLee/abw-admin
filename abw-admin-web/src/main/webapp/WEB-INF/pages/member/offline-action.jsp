<%--
  User: MicLee
  Date: 15-1-4
  Time: 下午3:56
  Desciption:添加修改会员信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
    <title>会员</title>
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
        <li>会员</li>
        <li><c:choose><c:when test="${empty member.id}">添加会员</c:when><c:otherwise>修改会员</c:otherwise></c:choose></li>
    </ol>

    <div class="section">
        <fieldset>
            <legend>会员管理</legend>
            <input name="id" type="hidden" id="id" value="${member.id}"/>
            <div id="msg" class="alert alert-danger" style="display: none;"></div>
            <table class="form">
                <tr>
                    <td class="label-td"><label for="name">会员姓名<span class="text-danger">*</span>：</label></td>
                    <td>
                        <input type="text" name="name" id="name" value="${member.name}" maxlength="16"/>
                        <span id="name-info" class="text-danger"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label-td"><label>性别<span class="text-danger">*</span>：</label></td>
                    <td>
                        <input type="radio" name="sex" value="男" /> 男
                        <input type="radio" name="sex" value="女" /> 女
                        <span id="sex-info" class="text-danger"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label-td"><label for="mobile">手机：</label></td>
                    <td>
                        <input type="text" name="mobile" id="mobile" value="${member.mobile}" maxlength="11"/>
                        <span id="mobile-info" class="text-danger"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label-td"><label for="qq">QQ：</label></td>
                    <td>
                        <input type="text" name="qq" id="qq" value="${member.qq}" maxlength="32"/>
                        <span id="qq-info" class="text-danger"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label-td"><label for="weixin">微信号：</label></td>
                    <td>
                        <input type="text" name="weixin" id="weixin" value="${member.weixin}" maxlength="32"/>
                        <span id="weixin-info" class="text-danger"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label-td"><label for="email">邮箱：</label></td>
                    <td>
                        <input type="text" name="email" id="email" value="${member.email}" maxlength="32"/>
                        <span id="email-info" class="text-danger"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label-td"><label for="point">积分：</label></td>
                    <td>
                        <input type="text" name="point" id="point" value="${member.point}" maxlength="11"/>
                        <span id="point-info" class="text-danger"></span>
                    </td>
                </tr>
                <tr>
                    <td class="label-td"><label for="address">地址：</label></td>
                    <td>
                        <textarea name="address" id="address" style="width: 60%; height: 50px;">${member.address}</textarea>
                        <span id="address-info" class="text-danger"></span>
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

        //SEX
        $("input[name='sex'][value=${member.sex}]").attr("checked", "checked");

        //save
        $("#action").on('click', function() {
            var id = $.trim($('#id').val());
            var name = $.trim($("#name").val());
            var sex = $("input[name='sex']:checked").val();
            var mobile = $.trim($("#mobile").val());
            var qq = $.trim($("#qq").val());
            var weixin = $.trim($("#weixin").val());
            var email = $.trim($("#email").val());
            var point = $.trim($("#point").val());
            var address = $.trim($("#address").val());

            var $nameInfo = $("#name-info");
            var $sexInfo = $("#sex-info");
            var $addressInfo = $("#address-info");

            if (name == '') {
                $nameInfo.text('会员姓名必填');
                return false;
            } else {
                if (name.length > 16) {
                    $nameInfo.text('会员姓名不能超过16个字符');
                    return false;
                }
                $nameInfo.text("");
            }

            if (sex == '' || sex == undefined)  {
                $sexInfo.text('请选择会员性别');
                return false;
            } else {
                $sexInfo.text("");
            }

            var url = (id == '') ? "/member/offline/add" : "/member/offline/update";
            $.post(url, {
                id: id,
                name: name,
                sex: sex,
                mobile: mobile,
                qq: qq,
                weixin: weixin,
                email: email,
                point: point,
                address: address
            }).done(function(res) {
                if (res.result == 'ok') {
                    dialog_prompt({
                        content:"操作成功!",
                        yes: function(){
                            window.location.href = "/member/offline/index";
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
