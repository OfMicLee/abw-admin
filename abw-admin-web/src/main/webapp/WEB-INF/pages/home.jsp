<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>欧邦®管理平台 — 首页</title>
    <meta name="description" content="欧邦网管理平台"/>
    <link rel="stylesheet" type="text/css" href="http://pic.ofcard.com/ofss/css/frame.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui-1.10.3.custom.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/crm.css"/>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="http://pic.ofcard.com/ofss/js/frame.js"></script>
    <script type="text/javascript" src="/js/handlebars1.3.0.js"></script>
</head>
<body>
<div id="wrapper" class="frame-wrapper">
    <div class="container">
        <div class="main full-height">
            <div class="content full-height">
            <iframe src="/account/info" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" id="mainFrame" name="mainFrame" class="full-height"></iframe>
            </div>
        </div>
        <div class="mainNav full-height">
            <a href="/home" class="mainLogo"></a>
            <ul><!--main menus--></ul>
            <a href="javascript:void(0);" class="sidebar-extend">
                <i class="icon icon-angle-right"></i>
                展开边栏
            </a>

            <p class="user-info">
                <a href="/logout" title="退出登录">
                    退出系统
                    <i class="icon icon-power-off"></i>
                </a>
            </p>
        </div>
        <div class="sideNav full-height">
            <h1 id="logo">
                <a href="">
                    ABW ADMIN
                </a>
            </h1>
            <ul id="sideList"></ul>
            <a href="javascript:void(0);" class="sidebar-shrink">
                <i class="icon icon-angle-left"></i>
                收起边栏
            </a>
        </div>
    </div>
</div>

<!--子菜单ui模板-->
<script id="sidenav-template" type="text/x-handlebars-template">
    {{#each menus}}
    <li><a href="{{url}}" target="mainFrame">{{name}}</a></li>
    {{/each}}
</script>

<!--主菜单-->
<script id="mainnav-template" type="text/x-handlebars-template">
    {{#each mainMenus}}
    <li>
        <a id="{{id}}">
            <i class="icon icon-dashboard"></i>
            {{name}}
        </a>
        <i class="triangle-border"></i>
        <i class="triangle"></i>
    </li>
    {{/each}}
</script>

<script type='text/javascript'>
    (function() {
        /////////////////////////缓存全局数据///////////////////////
        var data = (function() {
            var data = [];
            try {
                data = $.parseJSON('${menus}');
            } catch (err) {}
            return data;
        })();


        //jquery rocks
        $(function () {
            //初始化菜单
            new Menu({
                mainNavEl: ".mainNav ul", //一级菜单的dom元素
                subNavEl: "#sideList", //二级菜单的dom元素
                data: data
            });
        });

        /**
         * 构建菜单对象
         */
        var Menu = function (options) {
            //一级导航的id
            this.mainNavEl = options.mainNavEl;
            //二级导航的id
            this.subNavEl = options.subNavEl;
            //导航的数据
            this.data = options.data;

            /**
             * 得到handlerbars模板的html内容
             * @param templateId 模板的id，要带上#
             * @param data 模板的json数据
             * @returns {*}
             */
            this._html = function (templateId, data) {
                var template = Handlebars.compile($(templateId).html());
                return template(data);
            };

            //初始化菜单
            this.initMainNav();
        };

        /**
         * 初始化一级导航
         */
        Menu.prototype.initMainNav = function () {
            var self = this;
            var html = this._html("#mainnav-template", {mainMenus: this.data});
            $(this.mainNavEl)
                .empty()
                .append(html)
                .find("a")
                .click(function () {
                    //去出current样式
                    $(self.mainNavEl + " .current").removeClass("current");
                    var id = $(this).attr("id");
                    $("#" + id).parent().addClass("current");
                    //初始化二级菜单
                    self.initSubNav(id);
                })
                .first()
                .click();
        };

        /**
         * 初始化二级导航
         */
        Menu.prototype.initSubNav = function (id) {
            var self = this;
            $.each(self.data, function (_, menu) {
                if (menu.id == id) {
                    var html = self._html("#sidenav-template", {menus: menu.child});
                    $(self.subNavEl)
                        .empty()
                        .append(html);
                }
            });
        };
    })();
</script>
</body>
</html>
