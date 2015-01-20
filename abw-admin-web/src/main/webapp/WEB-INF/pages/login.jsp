<%--
  Created by IntelliJ IDEA.
  User: MicLee
  Date: 12/15/14
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>欧邦红酒管理平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="/css/login.css">
</head>

<body>

<div class="page-container">
    <h1>ABW® 后台登录</h1>

    <form id="loginForm">
        <input type="text" id="username" name="username" placeholder="请输入您的用户名">
        <input type="password" id="password" name="password" placeholder="请输入您的用户密码">
        <!--                <input type="Captcha" class="Captcha" name="Captcha" class="Captcha" placeholder="请输入验证码！">-->
        <button type="button" class="submit_button">登录</button>
        <div class="error">x<span></span></div>
    </form>
</div>

<div class="copyright">
    Copyright© Allbuywine官网商城 2014-2020, 版权所有 苏ICP备06069127号
</div>

<!-- Javascript -->
<script src="/js/jquery-1.9.1.min.js"></script>
<script src="/js/jquery.form.min.js"></script>
<script src="/js/login-supersized.3.2.7.min.js"></script>
<script src="/js/login-supersized-init.js"></script>
<script src="/js/login-script.js"></script>
</body>

</html>

