<%--
  User: hufeng
  Date: 14-6-30
  Time: 上午10:39
  Desciption: 用户权限树形展示
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
    <title>用户权限</title>
    <link rel="stylesheet" href="http://pic.ofcard.com/ofss/css/page.css">
    <!-- 线上CSS -->
    <link rel="stylesheet" type="text/css" href="/css/crm.css"/>
    <link rel="stylesheet" type="text/css" href="/css/crm-msg.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui-1.10.3.custom.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/ztree/zTreeStyle.css"/>

    <style type="text/css">
        label {
            font-size: 12px;
            font-weight: bold;
        }
        span {
            font-size: 12px;
        }
        div#rMenu {position:absolute; visibility:hidden; top:0;text-align: left;}
    </style>

    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="/js/jquery.ztree.core-3.5.js"></script>
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
            <li>权限管理</li>
        </ol>
    </div>
<div class="section">
    <table class="layout-table user-management">
        <tbody>
            <tr>
                <td class="col fix-col-02">
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <td style="font-size: 12px; line-height: 18px;">
                                    <span style="font-weight: bold;">权限菜单</span>
                                    <div>空白处右键可以添加一级菜单，节点上右键可添加下级或删除</div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <ul id="tree" class="ztree" style="border:0; background-color: #ffffff;"></ul>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
                <td class="col">
                    <div class="box rights-manage" id="detail" style="display: none;">
                        <div class="mtitle">
                            <h3>权限详情</h3>
                        </div>
                        <form class="form" id="form">
                            <input name="parentId" type="hidden" id="parentId"/>
                            <input name="id" type="hidden" id="id"/>
                            <table>
                                <tr>
                                    <td class="label-td">
                                        <label for="name">链接名称<span class="text-danger">*</span>:</label>
                                    </td>
                                    <td>
                                        <input name="name" id="name" value ="" class="input-xxlarge"/>
                                        <span id="name-info" class="text-danger"></span>
                                    </td>
                                </tr>
                                <tr id="tr-url">
                                    <td class="label-td">
                                        <label>链接URL<span class="text-danger">*</span>:</label>
                                    </td>
                                    <td>
                                        <input type="text" name="url" id="url" class="input-xxlarge"/>
                                        <span id="url-info" class="text-danger"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label-td"><label>是否在菜单中显示:</label></td>
                                    <td>
                                        <input type="radio" name="isShow" value="0" checked="checked"/>不显示
                                        <input type="radio" name="isShow" value="1"/>显示
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label-td"><label>描述信息:</label></td>
                                    <td>
                                        <textarea name="description" id="description" style="height: 50px; width: 562px;"></textarea>
                                        <span id="desc-info" class="text-danger"></span>
                                    </td>
                                </tr>
                                <tr style="margin-top: 50px;">
                                    <td class="label-td"></td>
                                    <td>
                                        <abw:securityUrl url="/rights/save">
                                            <a href="javascript:void(0);" id="action" class="btn btn-primary">确定</a>
                                        </abw:securityUrl>
                                        <%--<a href="javascript:void(0);" id="cancel" onclick="window.history.go(-1)" class="btn btn-danger">取消</a>--%>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
    <div id="rMenu">
        <abw:securityUrl url="/rights/save">
            <a href="javascript:void(0);" id="m_add">
                <img src="/images/add.jpg" alt="" width="25px" height="25px"/>
            </a>
        </abw:securityUrl>
        <abw:securityUrl url="/rights/delete">
            <a href="javascript:void(0);" id="m_del">
                <img src="/images/remove.jpg" alt="" width="25px" height="25px"/>
            </a>
        </abw:securityUrl>
    </div>
</div>


<script type="text/javascript">
    $(function () {
        initTree();

        $("#name").on('blur', function() {
            var id = $.trim($("#id").val());
            var name = $.trim($('#name').val());

            if (name == '') {
                $("#name-info").text('权限名称必填');
                $("#name").focus();
                return false;
            } else {
                $("#name-info").text();
            }

            $.post('/rights/validate', {id: id, name: name}).done(function(res) {
                if (res) {
                    $("#name-info").text('权限名称重复');
                    $("#name").focus();
                } else {
                    $("#name-info").text('');
                }
            });
        });

        $("#m_add").on('click', function() {
            var selectNodes = zTree.getSelectedNodes();
            var parentId = 0;
            if (selectNodes.length > 0) {
                parentId = selectNodes[0].id;
            }

            if (parentId == 0) {
                $("#tr-url").hide();
            } else {
                $("#tr-url").show();
            }

            $("#id").val("");
            $("#parentId").val(parentId);
            $("#name").val("");
            $("#url").val("");
            $("#description").val("");
            $("input[type='radio'][value='1']").prop('checked', true);



            if (!$("#detail").is(":visible")) {
                $("#detail").slideDown();
            }
        });

        $("#m_del").on('click', function() {
            var selectNodes = zTree.getSelectedNodes();
            if (selectNodes.length == 0) {
                return false;
            }
            var id = selectNodes[0].id;
            var name = selectNodes[0].name;
            var tip = "确定删除 <span class='error'>" + name + "?</span>";
            dialog_confirm({content: tip,
                yes:function() {
                    $.post('/rights/delete', {id: id})
                        .done(function(res) {
                            if (res.result == 'ok') {
                                zTree.removeNode(selectNodes[0]);
                                $("#detail").slideUp();
                            } else {
                                dialog_simple_fail(res.msg, false, '145px');
                            }
                        })
                        .fail(function() {
                            dialog_simple_fail('网络错误', false, '145px');
                        });
                }
            });
        });

        $("#action").click(function() {
            var id = $.trim($("#id").val());
            var parentId = $.trim($("#parentId").val());
            var name = $.trim($('#name').val());
            var url = $.trim($('#url').val());
            var desc = $.trim($("#description").val());
            var isShow = $(":checked").val();

            if (name == '') {
                $("#name-info").text('权限名称必填');
                $("#name").focus();
                return false;
            } else if(name.length > 64 ){
                $("#name-info").text('权限名过长');
                $("#name").focus();
                return false;
            } else {
                $("#name-info").text('');
            }
            if (desc.length > 0 && desc.length > 128) {
                    $("#desc-info").text('权限描述信息过长');
                    $("#desc").focus();
                    return false;
            }else{
                $("#desc-info").text('');
            }
            if (parentId != 0) {
                if (url == '') {
                    $("#url-info").text('权限url必填');
                    $("#url").focus();
                    return false;
                } else if(url.length > 64 ){
                    $("#url-info").text('权限url过长');
                    $("#url").focus();
                    return false;
                } else {
                    $("#url-info").text('');
                }

            }

            var params = {
              id: id,
              parentId: parentId,
              name: name,
              url: url,
              description: desc,
              isShow: isShow
            };

            var path = (id == '') ? "/rights/save" : "/rights/update";
            $.post(path, params).done(function(res) {
                if(res.result =='ok') {
                    if (id != '') {
                        dialog_simple_ok("更新成功", false, "58px");
                        var node = zTree.getNodeByParam("id", id, null);
                        node.url = url;
                        node.description = desc;
                        node.name = name;
                        node.isShow = isShow
                        zTree.updateNode(node, false);
                    } else {
                        dialog_simple_ok("添加成功", false, "58px");
                        var parentNode = null;
                        if (parentId != 0) {
                            parentNode = zTree.getNodeByParam("id", parentId, null);
                        }
                        zTree.addNodes(parentNode, res.data, false);
                    }
                } else {
                    dialog_simple_fail(res.msg, false, '58px');
                }
            }).fail(function() {
                dialog_simple_fail('网络错误', false, '58px');
            });

            return false;
        });


    });

    /**
     * 初始化页面时, 加载权限树信息
     * @author 1081_yxd
     */
    var zTree, rMenu;
    function initTree() {
        <c:if test="${rightsList ne null and rightsList.size()>0}" >
        var _rightsList=$.parseJSON('${rightsList}');
        </c:if>

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
            callback: {
                onRightClick: OnRightClick,
                onClick: onClick
            }
        };

        $.fn.zTree.init($("#tree"), setting, _rightsList);
        zTree = $.fn.zTree.getZTreeObj("tree");
        rMenu = $("#rMenu");

        if (!_rightsList) {
            $("#detail").show();
        }
    }

    /**
     * 鼠标右键
     */
    function OnRightClick(event, treeId, treeNode) {
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
            zTree.cancelSelectedNode();
            showRMenu("root", event.clientX, event.clientY);
            $("#detail").slideUp();
        } else if (treeNode && !treeNode.noR) {
            var tId = treeNode.tId;
            var $a = $("#" + tId + "_span");
            $a.css('position', 'relative');
            var top = $a.position().top - 5;
            var left = $a.position().left + $a.outerWidth() + 8;
            zTree.selectNode(treeNode);
            showDetails(treeNode);
            showRMenu(treeNode.isParent ? "root" : "node", left, top);
        }
    }

    var canDelete = false;
    <abw:securityUrl url="/rights/delete">
    canDelete = true;
    </abw:securityUrl>

    /**
     * showMenu
     */
    function showRMenu(type, left, top) {
        if (type === 'root' || !canDelete) {
            $("#m_del").hide();
        } else if (type === 'node') {
            $("#m_del").show();
        }
        $("#rMenu ul").show();
        rMenu.css({"top":top+"px", "left":left+"px", "visibility":"visible"});

        $("body").bind("mousedown", function(event) {
            if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
                rMenu.css({"visibility" : "hidden"});
            }
        });
    }

    /**
     * 点击鼠标左键,时获取节点信息并在相应位置显示链接信息
     * @param clickFlag
     */
    function onClick(event, treeId, treeNode,clickFlag) {
        showDetails(treeNode);
    }

    /**
     * 显示权限详情
     */
    function showDetails(treeNode) {
        $("#id").val(treeNode.id);
        $("#parentId").val(treeNode.parentId);
        if (treeNode.parentId == 0) {
            $("#tr-url").hide();
        } else {
            $("#tr-url").show();
        }
        $("#name").val(treeNode.name);
        $("#url").val(treeNode.url);
        $("input[type='radio'][value='"+treeNode.isShow+"']").prop('checked', true);
        $("#description").val(treeNode.description);
        if (!$("#detail").is(':visible')) {
            $("#detail").slideDown();
        }
    }

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
</script>

</body>
</html>
