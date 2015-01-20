<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>success</title>
    <link rel="stylesheet" type="text/css" href="/css/core.css">

    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/js/ofcs.js" charset="utf-8"></script>

    <script type="text/javascript">
        // 页面加载完毕时执行
        $(document).ready(function() {
            parent.$("#mainFrame")[0].contentWindow.fnInit();
        });
    </script>
</head>
<body>
<div id="wrapper">
    <div>
        <h1>操作成功</h1>
    </div>
    <div class="centered fill pusht">
        <a class="btn" onclick="dialog_close();">关闭</a>
    </div>
</div>
</body>
</html>