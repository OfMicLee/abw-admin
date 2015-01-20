var View = require('./view');
var Model = require('./model');
var ViewModel = require('./viewmodel');

//init
var view = new View();
var model = new Model();
var viewModel = new ViewModel(view, model);

//let's go
viewModel.main();
