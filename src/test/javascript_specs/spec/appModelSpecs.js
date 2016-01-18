describe('App Model', function() {
	it('AppLoginModel is defined', function() {
		expect(AppLoginModel).toBeDefined();
	});
	var appModel = new AppLoginModel();
	var appView = new AppLoginView(appModel);
	describe('Dynamic properties', function() {
		it('Default properties are defined', function() {
			expect(appModel.defaults).toBeDefined();
		});
		it('_baseUrl property is defined', function() {
			expect(appModel.defaults._baseUrl).toBeDefined();
		});
		it('_userName property is defined', function() {
			expect(appModel.defaults._userName).toBeDefined();
		});
		it('_password property is defined', function() {
			expect(appModel.defaults._password).toBeDefined();
		});
		it('_splitArray property is defined', function() {
			expect(appModel.defaults._splitArray).toBeDefined();
		});
		it('_setItemJson property is defined', function() {
			expect(appModel.defaults._setItemJson).toBeDefined();
		});
		it('_loginPostData property is defined', function() {
			expect(appModel.defaults._loginPostData).toBeDefined();
		});
		it('_useRName property is defined', function() {
			expect(appModel.defaults._useRName).toBeDefined();
		});
		it('_passWord property is defined', function() {
			expect(appModel.defaults._passWord).toBeDefined();
		});
		it('_addAnd property is defined', function() {
			expect(appModel.defaults._addAnd).toBeDefined();
		});
	});
describe('describe get function', function() {
		it('get function is defined', function() {
			expect(appModel.get).toBeDefined();
		});
		it('get function returns correct value for property _rowView', function() {
			var _rowView = appModel.defaults._rowView,
				_rowViewValue;
			_rowViewValue = appModel.get('_rowView');
			expect(_rowView).toEqual(_rowViewValue);
		});
	});
	describe('describe set function', function() {
		it('set function is defined', function() {
			expect(appModel.set).toBeDefined();
		});
		it('set value for single attributes', function() {
			var _rowView = '_rowView';
			_rowViewValue = appModel.set('_rowView', _rowView);
			expect(appModel.defaults._rowView).toEqual(_rowView);
		});
		it('set value for multiple attributes', function() {
			var _rowView = '_rowView',
				_config = '_config';
			_rowViewValue = appModel.set({
				_rowView: _rowView,
				_config: _config
			});
			expect(appModel.defaults._rowView).toEqual(_rowView);
			expect(appModel.defaults._config).toEqual(_config);
		});
	});

	describe('describe ajaxCalConfig function', function() {
		it('ajaxCalConfig function is defined', function() {
			expect(appModel.ajaxCalConfig).toBeDefined();
		});
		it('ajax method called with parameters', function() {

			spyOn(jQuery, 'ajax');
			appModel.ajaxCalConfig();
			expect(jQuery.ajax).toHaveBeenCalled();
		});
	});
	describe('describe ajaxCallPost function', function() {
		it('ajaxCallPost function is defined', function() {
			expect(appModel.ajaxCallPost).toBeDefined();
		});
		it('ajax method called with parameters', function() {
			spyOn(jQuery, 'extend');
			spyOn(jQuery, 'ajax');
			appModel.ajaxCallPost('url','post','crossDomain','json');
			expect(jQuery.ajax).toHaveBeenCalled();
		});
	});
});