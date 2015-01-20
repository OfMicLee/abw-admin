/**
 * hufeng on 14-7-2.
 * 协调view和model
 */
module.exports = ViewModel;


/**
 * 基于对象封装
 *
 * @param view
 * @param model
 * @constructor
 */
function ViewModel(view, model) {
    this.view = view;
    this.view.viewModel(this);
    this.model = model;

    this.bindEvent();
}


/**
 * 处理view传递的自定义事件，不处理任何dom事件
 */

ViewModel.prototype.bindEvent = function() {
    var self = this;

    /**
     * 显示规格列表
     */
    $(self).on("show:specList", function(event, productId) {
        self.model.fetchProductSpec(productId, function(err, specPropList) {
            self.view.initSpecPropList(specPropList);
        });
    });

    /**
     * 显示货品列表
     */
    $(self).on('show:goodsList', function(event, obj) {
        self.model.fetchGoodsList(obj.productId, obj.goodsArr, function(err, goodsList) {
            self.view.initGoodsList(goodsList);
        });
    });

    /**
     * 刷新商品
     */
    $(self).on("refresh:product", function(event, obj) {

        self.model.fetchProductPropData(function(err, brandList, extPropList, productList) {
            if (err != null) {
                this.view.error(err);
                return false;
            }

            self.view.initProductList(productList);
        });
    });


    /**
     * 刷新扩展属性
     */
    $(self).on('refresh:extProp', function(event, obj) {

        if (obj.type === 'brand') {
            self.model.fetchBrandGames(obj.pid, obj.vid, function(err, extPropList) {
                if (err != null) {
                    self.view.error(err);
                } else {
                    self.view.initBrandGameList(extPropList);
                }
            })
        } else {
            self.model.fetchSubExtProp(obj.pid, obj.vid, function(err, extPropList) {
                if (err != null) {
                    self.view.error(err);
                } else {
                    self.view.initSubExtPropLIst(extPropList);
                }
            });
        }
    });
};


/**
 * 页面初始化入口
 *
 */
ViewModel.prototype.main = function() {
    var self = this;
    this.model.fetchProductPropData(function(err, brandList, extPropList, productList) {
        if (err != null) {
            this.view.error(err);
        } else {
            self.view.initBrand(brandList);
            self.view.initExtProp(extPropList);
            self.view.initProductList(productList);
        }
    });
};


