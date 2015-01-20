/**
 * view代表了dom
 * @constructor
 */
module.exports = View;


////////////////////////////抽象视图的对象/////////////////////////
function View() {
}


/**
 * 初始化tab的title
 */
View.prototype.initTabTitle = function(obj) {
    Handlebars.registerHelper('count', function() {
        return obj['statics'][this.id];
    });

    $(".idTabs").html(this.tpl('#tabs-template', {list: obj.specPropList})).find("a").first().click();
    $("#second-page").slideDown();
};


/**
 * 初始化规格值列表
 */
View.prototype.initSpecValList = function(specValList, selectedSpecList) {
    Handlebars.registerHelper('selected', function() {
        return (selectedSpecList.indexOf(this.specValId) == -1) ? "label-success" : "label-danger";
    });

    Handlebars.registerHelper('specValKey', function() {
        var specValKey = this.specValKey;
        return (specValKey) ? '(' + specValKey + ')' : '';
    });

    //显示规格值列表
    $("#specValList").html(this.tpl('#specValList-template', {list: specValList}));
};

/**
 * 如果已经关联的规格不为空，则渲染该规格
 */
View.prototype.initExistsSpecValList = function(specValList) {
    $.each(specValList, function(_, specValId) {
        $('#' + specValId)
            .removeClass("label-danger")
            .removeClass('label-success')
            .addClass('label-info');
    });
};

/**
 * 更新已经选择的规格值得数量
 */
View.prototype.updateCount = function(obj) {
    var specPropId = obj.specPropId;
    var $count = $("#" + specPropId + ' .spec_count');
    //really cool?
    var text = $.trim($count.text()).slice(1, -1).split('/');
    text[0] = obj.count;
    $count.text("(" + text.join("/") +")");

};

/**
 * 保存生成的货品
 */
View.prototype.save = function(count, specList) {
    if(count < 1){
        dialog_prompt({
            content:"请您务必给所有的规格选择规格值，否则无法生成货品",
            width: 340,
            height: 180
        });
        return false;
    }else if(count > 100){
        dialog_prompt({
            content:"货品不能超过100个,请调整已选规格",
            width: 340,
            height: 180
        });
        return false;
    }

    parent.data = specList;
    parent.generateGoods();
    parent.$("#dialog").remove();
};


/**
 * 关联viewmodel
 */
View.prototype.viewModel = function(viewModel) {
    this.viewModel = viewModel;
    //绑定dom事件和ViewModel通信的自定义事件
    this.bindEvent();
};


/**
 * 获取模板编译后的html
 */
View.prototype.tpl = function (templateId, data) {
    var template = Handlebars.compile($(templateId).html());
    return template(data);
};

/**
 * 绑定视图事件
 */
View.prototype.bindEvent = function() {
    var $viewModel = $(this.viewModel);

    //搜索
    $("#search").on('click', search);

    //点击标题中的a链接
    $('.idTabs')
        .on('click', 'a', clickTabTitle)
        .on('click', '.sp_remove', removeTabTitle);

    //点击规格值
    $("#specValList").on('click', 'span', clickSpecVal);


    //生成sku
    $("#createGoods").on("click",function(){
        $viewModel.trigger("generate:sku");
        return false;
    });

    //取消
    $("#cancel").on("click",function(){
        parent.$("#dialog").remove();
    });

    //点击规格项
    $("#specPropList").on('click', 'span', function() {
        var $this = $(this);
        //添加
        if ($this.hasClass('label-success')) {
            $this.removeClass('label-success').addClass('label-danger');
        }
        //取消
        else if ($this.hasClass('label-danger')) {
            $this.removeClass("label-danger").addClass('label-success');
        }
    });

    ///////////////////callback/////////////////////////
    /**
     * 搜索
     */
    function search() {
        var $span = $("#specValList span");

        $span.removeClass('search');

        var key = $.trim($("#name").val());
        if (key != '') {
            $span.each(function() {
                if ($(this).text().indexOf(key) != -1) {
                    $(this).addClass('search');
                }
            })
        }
    }

    /**
     * 点击tab上得标题
     */
    function clickTabTitle() {
        var $this = $(this);
        var id = $this.prop('id');

        //解决选中的效果
        $('.idTabs .selected').removeClass('selected');
        $this.addClass('selected');

        $('#add_all_val').attr('specpropid', id);

        //通过viewmodel获取规格值列表，在刷新页面
        $viewModel.trigger('update:specValList', id);
    }

    /**
     * 点击tab上面的x
     */
    function removeTabTitle() {
        var $parent = $(this).parent();
        var specPropId = $parent.prop('id');

        //如果删除的是当前正在显示的，则删除后，显示第一个
        if ($parent.hasClass('selected')) {
            //删除li
            $parent.parent().remove();
            $(".idTabs a").first().click();
        } else {
            //删除li
            $parent.parent().remove();
        }

        //删除规格 同步model数据
        $viewModel.triggerHandler("remove:spec", specPropId);


        //禁止冒泡
        return false;
    }


    /**
     * toggle规格值得样式
     * 如果当前的样式是label-success改为label-danger,反之亦然
     */
    function clickSpecVal() {
        var $this = $(this);
        var obj = {
            specPropId: $('#add_all_val').attr('specpropid'),
            specValId: $this.prop('id')
        };

        //添加
        if ($this.hasClass('label-success')) {
            $this.removeClass('label-success').addClass('label-danger');
            //触发添加规格值得事件
            $viewModel.trigger('add:specVal', obj);
        }
        //取消
        else if ($this.hasClass('label-danger')) {
            $this.removeClass("label-danger").addClass('label-success');
            //触发删除规格值事件
            $viewModel.trigger('remove:specVal', obj);
        }
    }
};
