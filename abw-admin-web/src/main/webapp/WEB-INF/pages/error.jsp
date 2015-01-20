<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" language="java" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>500</title>
</head>
<body>
500，系统错误
<% if (exception != null) { %>
<%
    exception.printStackTrace(new java.io.PrintWriter(out));

%>
<% } else if ((Exception)request.getAttribute("javax.servlet.error.exception") != null) { %>
<% ((Exception)request.getAttribute("javax.servlet.error.exception")).printStackTrace(new java.io.PrintWriter(out)); %>
<% } %>
</body>
</html>