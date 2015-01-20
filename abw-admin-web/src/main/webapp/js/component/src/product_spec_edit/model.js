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
