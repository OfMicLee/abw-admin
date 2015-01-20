/**
 * hufeng on 14-7-2.
 * 页面DOM视图的抽象对象，完成用户的交互，数据的展示
 */
module.exports = View;


/**
 * 基于对象的封装
 *
 * @constructor
 */
function View() {
    this.chosenOpt = {
        disable_search_threshold: 10,
        no_results_text: '):，暂无匹配'
    };

    this.lastExtPopId = "";
}


/**
 * 初始化view依赖的viewModel，当然我们有能力做到view和viewmodel的完全解耦
 *
 * @param viewModel
 */

View.prototype.viewModel = function (viewModel) {
    this.viewModel = viewModel;
    this.bindEvent();
};


/**
 * 绑定页面的dom事件
 */

View.prototype.bindEvent = function () {
    var self = this;
    var $viewModel = $(this.viewModel);
    //商品select
    $("#productInfo").on('change', 'select', function () {
        //得到当前选中的商品的id
        var productId = $(":selected", this).val();
        $viewModel.trigger('show:specList', productId);
    });


    //规格select
    $("#specInfo").on('change', 'select', function () {
        var goodArr = [];
        var specArr = [];
        var productId = $("#productInfo select :selected").val();

        //当规格全部选择完成才去调用查询/生成货品列表的接口
        var specInfoLength = $("#specInfo select").length;

        var map = new HashMap();

        $("#specInfo select").each(function () {
            var $this = $(this);
            var specPropId = $this.attr('specpropid');
            var specValOpts = $(':selected', $(this));

            //封装后端需要的规格查询对象
            if (specValOpts.length > 0) {

                specValOpts.each(function () {

                    if (map.containsKey(specPropId)) {
                        map.get(specPropId).specValList.push({
                            specPropId: specPropId,
                            specValId: $(this).val()
                        });
                    }else{
                        var spec = {
                            specProp:
                            {
                                id: specPropId
                            },
                            specValList: [
                                {
                                    specPropId: specPropId,
                                    specValId: $(this).val()
                                }
                            ]
                        };
                        map.put(specPropId, spec);
                    }
                });

            }
        });

        if(map.size() > 0){
            specArr = map.values();
        }

        if (specArr.length === specInfoLength) {

            var good = {specRels: specArr};
            goodArr.push(good);

            //触发viewmodel自定义事件
            $viewModel.trigger("show:goodsList", {
                productId: productId,
                goodsArr: goodArr
            });
        }
    });

    //扩展属性change
    $("#ext").on("change", "select", function () {

        var $selectOpt = $(':selected', this);

        self.lastExtPopId = $selectOpt.attr('pid');
        //封装后端查询子扩展属性需要的对象
        var curObj = {
            type: $(this).attr("type") === 'brand' ? "brand" : 'extprop',
            pid: $selectOpt.attr('pid'),
            vid: $.trim($selectOpt.val())
        };

        $viewModel.trigger("refresh:extProp", curObj)
    });
};


/**
 * 显示错误信息
 *
 * @param error
 */

View.prototype.error = function (error) {
    if (err != null) {
        $("#msg").text(error.msg).show();
    }
};

/**
 * 初始化品牌
 *
 * @param brandList
 */

View.prototype.initBrand = function (brandList) {
    var $brandList = $("#brandBody");

    if (brandList == null || brandList.length == 0) {
        $brandList.empty().hide();
    } else {
        $brandList.html(tpl("#brand-tpl", {
            propName: "品牌",
            brandList: brandList
        })).show().find("select").chosen(this.chosenOpt);
    }
};


/**
 * 初始化商品列表
 *
 * @param productList
 */

View.prototype.initProductList = function (productList) {
    //隐藏规格，货品列表
    $("#specInfo").empty().parent().hide();
    $("#goodInfo body").empty();
    $("#goodInfo").parent().hide();
    var $productInfo = $("#productInfo");

    if (productList == null || productList.length == 0) {
        $productInfo.empty().parent().hide();
    } else {
        var chosenOpt = this.chosenOpt;
        chosenOpt.width = "296";

        $productInfo.empty().append(tpl("#product-tpl", {
            propName: "商品名称",
            productList: productList
        })).find("select").chosen(chosenOpt);
        $productInfo.parent().show();
    }
};


/**
 * 初始规格
 * @param specPropList
 */
View.prototype.initSpecPropList = function (specPropList) {
    //隐藏货品
    $("#goodInfo").parent().hide();
    $("#goodInfo body").empty();
    $("#specInfo").empty().parent().hide();

    if (specPropList != null && specPropList.length > 0) {
        $("#specInfo").parent().show();

        $.each(specPropList, function (_, item) {
            $("#specInfo").append(tpl("#spec-tpl", {
                specPropId: item.specProp.id,
                propName: item.specProp.name,
                specValList: item.specValList
            })).find("select").chosen(self.chosenOpt);
        });
    } else {
        $("#specInfo").empty().parent().hide();
    }
};


/**
 * 初始化货品列表
 *
 * @param goods
 */

View.prototype.initGoodsList = function (goods) {
    if (goods != null && goods.length > 0) {
        $("#goodInfo tbody").html(tpl("#good-tpl", { goods: goods}));
        $("#goodInfo").parent().show();
    } else {
        $("#goodInfo").parent().hide();
    }
};


/**
 * 初始化品牌游戏
 *
 * @param extPropList
 */

View.prototype.initBrandGameList = function (extPropList) {

    var self = this;
    var $brandGame = $("#branGameBody");
    $brandGame.empty();
    paramArr = [];

    if (extPropList != null && extPropList.length > 0) {
        $.each(extPropList, function (_, item) {
            //属性名称
            var name = item.name;
            var valList = item.valList;

            if (valList != null) {
                var subHtml = tpl("#brand-game-tpl", {
                    propName: name,
                    extPropList: extPropList
                });

                $brandGame.append(subHtml).show();
            }
        });

        $brandGame.find("select").chosen(self.chosenOpt);
    } else {
        $brandGame.hide();
    }
    //请求商品
    //获取当前的扩展属性的组合
    $("#ext select").each(function () {
        console.log("进来了");
        var $selectOpt = $(':selected', this);
        console.log($selectOpt.html());
        var pid = $selectOpt.attr('pid');
        var vid = $.trim($selectOpt.val());
        var ppid = $(this).attr("ppid") || "";

        if (vid != "") {
            var obj = {
                cateId: catId,
                ppid: ppid,
                propId: pid,
                valId: vid
            };
            paramArr.push(obj);
        }
    });

    console.log("所有的选择框", paramArr);
    //刷新商品
    $(this.viewModel).trigger("refresh:product");
};


/**
 * 初始化子扩展属性
 *
 *
 * @param extPropList
 */

View.prototype.initSubExtPropLIst = function (extPropList) {

    var self = this;
    var extPropId = self.lastExtPopId;
    paramArr = [];

    var subExtProp = [];
    var getSubExtPropIdList = function (extPropId) {
        var $sub = $("select[ppid='" + extPropId + "']");

        if ($sub.size() != 0) {
            $sub.each(function () {
                var $this = $(this);
                var pid = $this.attr('pid');
                subExtProp.push(pid);
                getSubExtPropIdList(pid);
            });
        }
    };
    getSubExtPropIdList(extPropId);

    //删除所有的子扩展属性
    $.each(subExtProp, function (_, extPropId) {
        $("select[pid='" + extPropId + "']").parent().parent().remove();
    });

    if (extPropList != null && extPropList.length > 0) {
        //创建子属性
        var allHtml = "";

        function creatSubExtProp(extPropList) {
            //注册一个选择的tag
            Handlebars.registerHelper('selected', function () {
                return (selectVid == this.id) ? "selected" : "";
            });

            $.each(extPropList, function (_, item) {
                selectVid = item.currentVid;
                if (item.valList != null) {
                    allHtml += tpl("#extProp-tpl", {
                        propName: item.name,            //属性名称
                        extPropList: item.valList,         //属性编号
                        ppid: item.ppid,           //父属性编号
                        propId: item.id                 //选中的属性值编号
                    });

                    //判断是否有子属性，如果有需要继续往下展示
                    var extPropList = item.currentSubSearchExtProps;
                    if (extPropList != null && extPropList.length > 0) {
                        creatSubExtProp(extPropList);
                    }
                }
            });
        }

        creatSubExtProp(extPropList);

        //新属性添加到父后面
        $("select[pid='" + extPropId + "']").parent().parent().after(allHtml);
        $("#extProp select:visible").chosen(this.chosenOpt);

    }
    //请求商品
    //获取当前的扩展属性的组合
    $("#ext select").each(function () {

        var $selectOpt = $(':selected', this);
        var pid = $selectOpt.attr('pid');
        var vid = $.trim($selectOpt.val());
        var ppid = $(this).attr("ppid") || "";

        if (vid != "") {
            var obj = {
                cateId: catId,
                ppid: ppid,
                propId: pid,
                valId: vid
            };
            paramArr.push(obj);
        }
    });

    //刷新商品
    $(this.viewModel).trigger("refresh:product");
};


/**
 * 显示商品扩展属性
 *
 * @param extPropList
 */

View.prototype.initExtProp = function (extPropList) {
    if (extPropList != null && extPropList.length > 0) {
        creatSubExtProp(extPropList);
        $("#extProp").show();
    } else {
        $("#extProp").hide();
    }
};


//创建子属性
var selectVid;
function creatSubExtProp(extPropList) {
    //注册一个选择的tag
    Handlebars.registerHelper('selected', function () {
        return (selectVid == this.id) ? "selected" : "";
    });

    $.each(extPropList, function (_, item) {
        selectVid = item.currentVid;
        if (item.valList != null) {
            var subHtml = tpl("#extProp-tpl", {
                propName: item.name,            //属性名称
                extPropList: item.valList,         //属性编号
                ppid: item.ppid,           //父属性编号
                propId: item.id                 //选中的属性值编号
            });

            $("#extProp").append(subHtml).find("select").chosen(this.chosenOpt);

            //判断是否有子属性，如果有需要继续往下展示
            var extPropList = item.currentSubSearchExtProps;
            if (extPropList != null && extPropList.length > 0) {
                creatSubExtProp(extPropList);
            }
        }
    });
}


/**
 * 得到handlerbars模板的html内容
 * @param templateId 模板的id，要带上#
 * @param data 模板的json数据
 * @returns {*}
 */
function tpl(templateId, data) {
    var template = Handlebars.compile($(templateId).html());
    return template(data);
}