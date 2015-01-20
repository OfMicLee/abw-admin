/**
 * hufeng on 14-7-2.
 * 繁重的ajax以及变态的数据转化model来扛。
 */
module.exports = Model;


/**
 * based oop
 * @constructor
 */
function Model() {
}


/**
 * 得到商品属性数据
 */
Model.prototype.fetchProductPropData = function(callback) {
    var url = "/supProduct/getAllSearchProp";

    $.get(url, {propValJson: JSON.stringify(paramArr), catId: catId})
        .done(function (json) {
            if (json.result != "ok" || json.data == null) {
                callback(new Error("获取商品属性数据错误"));
            } else {
                //初始化品牌
                var brandList = json.data.brandList;
                //扩展属性
                var extPropList = json.data.searchExtProps;
                //初始化商品
                var productList = json.data.productList;
                callback(null, brandList, extPropList, productList);
            }
        }).fail(function() {
            callback(new Error("获取商品属性数据错误"));
        });
};


/**
 * 获得商品的规格
 *
 * @param productId
 * @param callback
 */
Model.prototype.fetchProductSpec = function(productId, callback) {
    var url = "/supProduct/getPublicById?productId=" + productId;
    $.get(url).done(function (json) {
        if (json.result != "ok" || json.data == null) {
            callback(new Error("网络错误，获取商品的规格失败！"));
        }

        var specPropList = json.data;
        callback(null, specPropList);
    }).fail(function() {
        callback(new Error("网络错误，获取商品的规格失败！"));
    });
};


/**
 * 获取货品列表
 *
 * @param productId
 * @param goodsArr
 * @param callback
 */
Model.prototype.fetchGoodsList = function(productId, goodsArr, callback) {

    var url = "/supProduct/getGoodsById?productId=" + productId + "&goodSpecValues=" + JSON.stringify(goodsArr);
    $.get(url).done(function (json) {
        if (json.result != "ok" || json.data == null) {
            return callback(new Error("网络错误，获取货品列表失败"));
        }

        var goods = json.data;
        callback(null, goods);

    }).fail(function() {
        return callback(new Error("网络错误，获取货品列表失败"));
    });
};


/**
 * 品牌变动，获取品牌的字属性
 *
 * @param propId
 * @param valId
 * @param callback
 */
Model.prototype.fetchBrandGames = function(propId, valId, callback) {
    var url = "/supProduct/getSubSearchProp?catId=" + catId + "&propId=" + propId + "&valId=" + valId;

    $.get(url).done(function (json) {
        if (json.result != "ok" || json.data == null) {
            callback(new Error("获取品牌子属性数据错误"));
            return false;
        }

        var extPropList = json.data.searchExtProps;
        callback(null, extPropList);

    }).fail(function() {
        callback(new Error("网络错误，获取品牌子属性失败"));
    });
};

/**
 * 获取子扩展属性
 *
 * @param propId
 * @param valId
 * @param callback
 */
Model.prototype.fetchSubExtProp = function (propId, valId, callback) {
    //扩展属性
    var url = "/supProduct/getSubSearchProp?catId=" + catId + "&propId=" + propId + "&valId=" + valId;
    $.get(url).done(function (json) {
        if (json.result != "ok" || json.data == null) {
            callback(new Error("获取子扩展属性错误"));
        } else {
            var extPropList = json.data.searchExtProps;
            callback(null, extPropList)
        }
    }).fail(function() {
        callback(new Error("网络错误，获取子扩展属性错误"));
    });
};