(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
var View = require('./view');
var Model = require('./model');
var ViewModel = require('./viewmodel');

//init
var view = new View();
var model = new Model();
var viewModel = new ViewModel(view, model);

//let's go
viewModel.main();

},{"./model":2,"./view":3,"./viewmodel":4}],2:[function(require,module,exports){
/**
 * 暴露业务对象
 * @constructor
 */
module.exports = Model;

////////////////////////////业务对象///////////////////////
function Model() {
    //所有的规格和规格值
    this.data = loadData();

    /**
     * 记录选中的规格和规格值，数据结构为map，键为规格id，值为规格值的列表
     * obj => {specPropId: [specValList..]}
     */
    this.selectedSpecObj = (function() {
        var obj = {};

        //将alreadyChooseSpec进行数据结构的转化
        var alreadyChooseSpec = parent.alreadyChooseSpec;
        for (var i = 0, len = alreadyChooseSpec.length; i < len; i++) {
            var spec = alreadyChooseSpec[i];
            obj[spec.specProp.id] = $.map(spec.specValList, function(obj) {
                return obj.specValId;
            });
        }

        return obj;
    })();

    //已经选中的规格对象简单格式,规格大字段
    var self = this;
    this.existsSpecObj = (function(data) {
        var tmpObj = {};
        $.each(data, function(_, obj) {
            var specPropId = obj.specProp.id;

            var selectedPropValList = self.selectedSpecObj[specPropId];
            if (selectedPropValList) {
                $.each(obj.specValList, function(_, obj) {
                    var index = selectedPropValList.indexOf(obj.specValId);
                    if (index != -1 ) {
                        selectedPropValList.splice(index, 1);
                    }
                });
            }

            tmpObj[specPropId] = $.map(obj.specValList, function (valObj) {
                return valObj.specValId;
            });
        });
        return tmpObj;
    })(parent.existsSpecObj);
}

/**
 * 设置用户选择的规格项
 */
Model.prototype.setSelectedSpecPropList = function(selectedSpecPropIdList) {
    var self = this;
    var originSelectedPropIdList = this.getSelectedSpecPropIdList();

    $.each(originSelectedPropIdList, function(_, id) {
        //新的选择规格项中没有旧的规格项 删除
        if (selectedSpecPropIdList.indexOf(id) == -1) {
            delete self.selectedSpecObj[id];
        }
    });

    $.each(selectedSpecPropIdList, function(_, id) {
        if (originSelectedPropIdList.indexOf(id) == -1) {
            self.selectedSpecObj[id] = [];
        }
    });
};

/**
 * 得到规格列表
 */
Model.prototype.getSpecPropList = function() {
    return $.map(this.data, function(obj) { return obj.specProp; });
};

/**
 * 得到已经选择的规格项列表
 */
Model.prototype.getSelectedSpecPropIdList = function() {
    var selectedSpecPropIdList = [];

    $.each(this.selectedSpecObj, function(key, _) {
        selectedSpecPropIdList.push(key);
    });

    return selectedSpecPropIdList;
};

/**
 * 得到已经选择的规格项
 */
Model.prototype.getSelectedSpecPropList = function() {
    var ids = this.getSelectedSpecPropIdList();

    var allSpecPropList = $.map(this.data, function(obj) {
        return obj.specProp;
    });

    return $.grep(allSpecPropList, function(obj) {
        return ids.indexOf(obj.id) != -1;
    });
};



/**
 * 根据规格的id，查询id下关联的规格值列表
 */
Model.prototype.getSpecValListBySpecPropId = function(id) {
    var data = this.data;
    for (var i = 0, len = data.length; i < len; i++) {
        if (data[i].specProp.id == id) {
            return data[i].specValList;
        }
    }
};

/**
 * 添加规格值
 * obj: {specPropId:?, specValId: ?}
 */
Model.prototype.addSpecVal = function(obj) {

    var specValList = this.selectedSpecObj[obj.specPropId];
    if (specValList) {
        specValList.push(obj.specValId);
    } else {
        this.selectedSpecObj[obj.specPropId] = [obj.specValId];
    }
};

/**
 * 统计共有多少规格值以及已经选择了多少
 */
Model.prototype.statics = function() {
    var countObj = {};

    $.each(this.selectedSpecObj, function (key, value) {
        countObj[key] = value.length;
    });

    $.each(this.existsSpecObj, function(key, value) {
       if (countObj[key]) {
           countObj[key] += value.length;
       } else {
           countObj[key] = value.length;
       }
    });

    $.each(this.data, function(_, obj) {
        var specPropId = obj.specProp.id;

        if (countObj[specPropId]) {
            countObj[specPropId] += '/' + obj.specValList.length;
        } else {
            countObj[specPropId] = '0/' + obj.specValList.length;
        }
    });

    return countObj;
};

/**
 * 得到某一个规格下已经选中的规格值得数量
 */
Model.prototype.getCount = function(specPropId) {
    return this.selectedSpecObj[specPropId].length + this.existsSpecObj[specPropId].length;
};


/**
 * 添加规格值
 * obj: {specPropId:?, specValId: ?}
 */
Model.prototype.removeSpecVal = function(obj) {
    var specValList = this.selectedSpecObj[obj.specPropId];
    var index = specValList.indexOf(obj.specValId);
    if (index != -1) {
        specValList.splice(index, 1);
    }
};

/**
 * 得到当前的关联的规格值列表
 */
Model.prototype.getSelectedSpecObj = function() {
    return this.selectedSpecObj;
};


/**
 * 最终的规格值情况
 */
Model.prototype.last = function() {
    var len = [];

    var self = this;
    $.each(this.existsSpecObj, function(key, value) {
        if (self.selectedSpecObj[key]) {
            len.push(value.length + self.selectedSpecObj[key].length);
        } else {
            len.push(value.length);
        }
    });

    var count = 0;
    if (len != 0) {
        len.push(1);
        count = eval(len.join('*'));
    }

    var specList = $.map(this.data, function(obj) {
        var specPropId = obj.specProp.id;
        var specValIdList = self.selectedSpecObj[specPropId] || [];
        specValIdList = specValIdList.concat(self.existsSpecObj[specPropId] || []);

        if (specValIdList.length > 0) {

            var specValList = $.grep(obj.specValList, function(obj, _) {
                if (specValIdList.indexOf(obj.specValId) != -1) {
                    return obj;
                }
            });
            return {
                specProp: obj.specProp,
                specValList: specValList
            }
        }
    });


    return {
        count: count,
        specList: specList
    }
};

/**
 * 根据规格id删除已经选中的规格和规格值
 */
Model.prototype.removeSpecProp = function(specPropId) {
    delete this.selectedSpecObj[specPropId];
};

},{}],3:[function(require,module,exports){
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

},{}],4:[function(require,module,exports){
/**
 * 暴露viewmodel对象
 *
 * @param view
 * @param model
 * @constructor
 */
module.exports = ViewModel;


/////////////////////////ViewModel你懂得//////////////////
function ViewModel(view, model) {
    this.view = view;
    this.view.viewModel(this);
    this.model = model;

    //new的时候初始化初始化自定义事件
    this.bindEvent();
}

ViewModel.prototype.main = function() {
    var obj = {
        specPropList: this.model.getSpecPropList(),
        statics: this.model.statics()
    };

    this.view.initTabTitle(obj);
};

/**
 * 自定义ViewMode的事件，主要为了和view进行沟通
 */
ViewModel.prototype.bindEvent = function() {
    var self = this;

    //更新规格值列表视图
    $(self).on('update:specValList', function(event, id) {
        self.updateSpecValListView(id);
    });

    //添加规格值
    $(self).on('add:specVal', function(event, obj) {
        self.model.addSpecVal(obj);
        var count = self.model.getCount(obj.specPropId);
        self.view.updateCount({specPropId: obj.specPropId, count: count});
    });

    //删除规格值
    $(self).on('remove:specVal', function(event, obj) {
        var specPropId = obj.specPropId;
        var specValId = obj.specValId;

        self.model.removeSpecVal(obj);
        var count = self.model.getCount(specPropId);
        self.view.updateCount({specPropId: specPropId, count: count});
    });

    //保存生成sku
    $(self).on('generate:sku', function() {
        var last = self.model.last();
        var count = last.count;
        var specList = last.specList;

        self.view.save(count, specList);
    });

    //删除规格
    $(self).on('remove:spec', function(event, specPropId) {
        self.model.removeSpecProp(specPropId);

        var specPropList = self.model.getSelectedSpecPropIdList();
        //如果规格都删除完，返回上级重现选规格
        if (specPropList.length == 0) {
            self.main();
        }
    });
};

/**
 * 规格值model发生变化，刷新页面
 * @param id
 */
ViewModel.prototype.updateSpecValListView = function(id) {
    //根据规格id查询所有的规格值列表
    var specValList = this.model.getSpecValListBySpecPropId(id);

    //查询当前该规格id下面已经关联的规格值列表
    var selectedObj = this.model.getSelectedSpecObj();

    this.view.initSpecValList(specValList, selectedObj[id] || []);

    //高亮已经关联商品的规格
    this.view.initExistsSpecValList(this.model.existsSpecObj[id]);
};

},{}]},{},[1])