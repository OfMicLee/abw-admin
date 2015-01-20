<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>账号信息</title>
    <link rel="stylesheet" type="text/css" href="/css/core.css" />    
</head>
<body>
<form action="" id="accountInfo">
<div id="wrapper">
    <table class="form label-colored-form">
        <tbody>
        <tr class="sprite">
            <td class="colored" colspan="2">个人信息：</td>
        </tr>
        <tr class="sprite">
            <td class="label-td"><label>帐号：</label></td>
            <td>${empNo}</td>
        </tr>
        <tr class="sprite">
            <td class="label-td"><label>姓名：</label></td>
            <td>${empName}</td>
        </tr>
        <tr class="sprite">
            <td class="label-td"><label>工号：</label></td>
            <td>${empNo}</td>
        </tr>
        <tr class="sprite">
            <td class="label-td"><label>手机：</label></td>
            <td colspan="2">${mobile}</td>
        </tr>
        <tr class="sprite">
            <td class="label-td"><label>邮箱：</label></td>
            <td>${email}</td>
        </tr>
        <tr class="sprite">
            <td class="label-td"><label>描述：</label></td>
            <td>${description}</td>
        </tr>
        <tr class="sprite" height="35px">
            <td class="colored" colspan="2" >帐号信息：</td>
        </tr>
        <tr class="sprite">
            <td class="label-td"><label>最后登录ip：</label></td>
            <td>${lastIp}</td>
        </tr>
        <tr class="sprite">
            <td class="label-td"><label>最后登录时间：</label></td>
            <td>
                ${lastTime}
            </td>
        </tr>
        </tbody>
    </table>
</div>
</form>
</body>
</html>