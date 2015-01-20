/**
 * hufeng on 14-7-2.
 * 使用Commonjs对复杂的页面交互进行模块化
 * 1. view代表着整个页面DOM的展示和交互
 * 2. 所有的数据访问转换都在model中进行处理
 * 3. viewmodel负责协调view和model
 */
var View = require('./view');
var Model = require('./model');
var ViewModel = require("./viewmodel");

/**
 * 见证奇迹的时刻
 */
$(function() {
    var view = new View();
    var model = new Model();
    var viewModel = new ViewModel(view, model);

    //不要心慌，Let's go!
    viewModel.main();
});
