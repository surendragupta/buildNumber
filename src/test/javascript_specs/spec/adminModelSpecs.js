describe('Admin Model', function() {
	// body...
	beforeEach(function() {
		spyOn(window, 'alert');
		spyOn($('document'), 'ready');
	});

	it('AdminModel is defined', function() {
		expect(AdminModel).toBeDefined();
	});
	var adminModel = new AdminModel();
	var adminView = new AdminView(adminModel);

	describe('Dynamic properties', function() {
		it('Default properties are defined', function() {
			expect(adminModel.defaults).toBeDefined();
		});
		it('_sessionData property is defined', function() {
			expect(adminModel.defaults._sessionData).toBeDefined();
		});
		it('_config property is defined', function() {
			expect(adminModel.defaults._config).toBeDefined();
		});
		it('win_height property is defined', function() {
			expect(adminModel.defaults.win_height).toBeDefined();
		});
		it('win_width property is defined', function() {
			expect(adminModel.defaults.win_width).toBeDefined();
		});
		it('_baseUrl property is defined', function() {
			expect(adminModel.defaults._baseUrl).toBeDefined();
		});
		it('_globalUrlStore property is defined', function() {
			expect(adminModel.defaults._globalUrlStore).toBeDefined();
		});
		it('_rowView property is defined', function() {
			expect(adminModel.defaults._rowView).toBeDefined();
		});
		it('_dataIdToshow property is defined', function() {
			expect(adminModel.defaults._dataIdToshow).toBeDefined();
		});
		it('_globalId property is defined', function() {
			expect(adminModel.defaults._globalId).toBeDefined();
		});
		it('_globalThisContext property is defined', function() {
			expect(adminModel.defaults._globalThisContext).toBeDefined();
		});
		it('_previousCount property is defined', function() {
			expect(adminModel.defaults._previousCount).toBeDefined();
		});
		it('_landingPagePrevCount property is defined', function() {
			expect(adminModel.defaults._landingPagePrevCount).toBeDefined();
		});
		it('_previousCountArray property is defined', function() {
			expect(adminModel.defaults._previousCountArray).toBeDefined();
		});
		it('_previousLandingCountArray property is defined', function() {
			expect(adminModel.defaults._previousLandingCountArray).toBeDefined();
		});
		it('_dropdownCount property is defined', function() {
			expect(adminModel.defaults._dropdownCount).toBeDefined();
		});
		it('_optionselector property is defined', function() {
			expect(adminModel.defaults._optionselector).toBeDefined();
		});
		it('_schoolRowDisableCheck property is defined', function() {
			expect(adminModel.defaults._schoolRowDisableCheck).toBeDefined();
		});
		it('_nextdisbleChcek property is defined', function() {
			expect(adminModel.defaults._nextdisbleChcek).toBeDefined();
		});
		it('_districtContext property is defined', function() {
			expect(adminModel.defaults._districtContext).toBeDefined();
		});
		it('_districtList property is defined', function() {
			expect(adminModel.defaults._districtList).toBeDefined();
		});
		it('_editbox property is defined', function() {
			expect(adminModel.defaults._editbox).toBeDefined();
		});
		it('_editbutton property is defined', function() {
			expect(adminModel.defaults._editbutton).toBeDefined();
		});
		it('_globalCourseCatalog property is defined', function() {
			expect(adminModel.defaults._globalCourseCatalog).toBeDefined();
		});
		it('_globalCourseSelection property is defined', function() {
			expect(adminModel.defaults._globalCourseSelection).toBeDefined();
		});
		it('_postId property is defined', function() {
			expect(adminModel.defaults._postId).toBeDefined();
		});
		it('_domainId property is defined', function() {
			expect(adminModel.defaults._domainId).toBeDefined();
		});
		it('_globalLicensecNum property is defined', function() {
			expect(adminModel.defaults._globalLicensecNum).toBeDefined();
		});
		it('_popupWindow property is defined', function() {
			expect(adminModel.defaults._popupWindow).toBeDefined();
		});
		it('_stateId property is defined', function() {
			expect(adminModel.defaults._stateId).toBeDefined();
		});
		it('_stateReportId property is defined', function() {
			expect(adminModel.defaults._stateReportId).toBeDefined();
		});
		it('_ditrictReportId property is defined', function() {
			expect(adminModel.defaults._ditrictReportId).toBeDefined();
		});
		it('_stateName property is defined', function() {
			expect(adminModel.defaults._stateName).toBeDefined();
		});
		it('_result property is defined', function() {
			expect(adminModel.defaults._result).toBeDefined();
		});
		it('_totalCourseArry property is defined', function() {
			expect(adminModel.defaults._totalCourseArry).toBeDefined();
		});
		it('_globalSccessToken property is defined', function() {
			expect(adminModel.defaults._globalSccessToken).toBeDefined();
		});
	});

	describe('describe get function', function() {
		it('get function is defined', function() {
			expect(adminModel.get).toBeDefined();
		});
		it('get function returns correct value for property _rowView', function() {
			var _rowView = adminModel.defaults._rowView,
				_rowViewValue;
			_rowViewValue = adminModel.get('_rowView');
			expect(_rowView).toEqual(_rowViewValue);
		});
	});
	describe('describe set function', function() {
		it('set function is defined', function() {
			expect(adminModel.set).toBeDefined();
		});
		it('set value for single attributes', function() {
			var _rowView = '_rowView';
			_rowViewValue = adminModel.set('_rowView', _rowView);
			expect(adminModel.defaults._rowView).toEqual(_rowView);
		});
		it('set value for multiple attributes', function() {
			var _rowView = '_rowView',
				_config = '_config';
			_rowViewValue = adminModel.set({
				_rowView: _rowView,
				_config: _config
			});
			expect(adminModel.defaults._rowView).toEqual(_rowView);
			expect(adminModel.defaults._config).toEqual(_config);
		});
	});
	describe('describe ajaxCalConfig function', function() {
		it('ajaxCalConfig function is defined', function() {
			expect(adminModel.ajaxCalConfig).toBeDefined();
		});
		it('ajax method called with parameters', function() {

			spyOn(jQuery, 'ajax');
			adminModel.ajaxCalConfig();
			expect(jQuery.ajax).toHaveBeenCalled();
		});
	});
	describe('describe ajaxObjCall function', function() {
		it('ajaxObjCall function is defined', function() {
			expect(adminModel.ajaxObjCall).toBeDefined();
		});
		it('ajax method called with parameters', function() {
			spyOn(jQuery, 'extend');
			spyOn(jQuery, 'ajax');
			adminModel.ajaxObjCall({url:'url'});
			expect(jQuery.ajax).toHaveBeenCalled();
		});
	});
});