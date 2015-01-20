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
