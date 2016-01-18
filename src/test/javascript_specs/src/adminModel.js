var AdminModel = function() {
	var self = this;
	this.defaults = {
		_sessionData: JSON.parse(sessionStorage.getItem('data')),
		_config: {},
		win_height: '',
		win_width: '',
		_baseUrl: '',
		_globalUrlStore: '',
		_rowView: 10,
		_dataIdToshow: 0,
		_globalId: 0,
		_globalThisContext: '',
		_previousCount: 0,
		_landingPagePrevCount: 0,
		_previousCountArray: [0],
		_previousLandingCountArray: [0],
		_dropdownCount: 10,
		_optionselector: '',
		_schoolRowDisableCheck: 0,
		_nextdisbleChcek: '',
		_popupWindow: null,
		_districtContext: '',
		_districtList: '',
		_editbox: $('.editpopupBox'),
		_editbutton: '',
		_globalCourseCatalog: [],
		_stateId: [],
		_stateReportId: [],
		_ditrictReportId: [],
		_stateName: [],
		_globalCourseSelection: [],
		_postId: 0,
		_domainId: 0,
		_result: '',
		_totalCourseArry: [],
		_globalLicensecNum: 0,
		_globalSccessToken: ""
	}
	this.get = function(parameter) {
		return this.defaults[parameter];
	};

	this.set = function(parameter1, parameter2) {
		var parameter;
		if (typeof parameter1 === 'object') {
			for (parameter in parameter1) {
				this.defaults[parameter] = parameter1[parameter];
			}
		} else if (typeof parameter1 === 'string' && parameter2) {
			this.defaults[parameter1] = parameter2;
		}
	};

	this.ajaxCalConfig = function(url) {
		var view = self.get('view');
		$.ajax({
			url: "config.json",
			success: function(result) {
				self.set('configResult', result);
				view.loadPage()
			},
			error: function() {
				console.log('error loading page');
			},
			beforeSend: function(xhr, settings) {
				// console.log(xhr);
				view.beforeSendEventHandler(xhr, settings);
			}
		});
	};
	this.ajaxObjCall = function(ajaxParam, successHandler, errorHandler, completeHandler, functionObject) {

		var view = self.get('view'),
			defaultAjaxParam = {
				url: ajaxParam.url,
				success: function(response) {
					if (successHandler) {
						successHandler(response, functionObject.successVal);
					}
				},
				error: function(response) {
					if (response.responseJSON.message.indexOf('JWT') > -1 || response.responseJSON.message.indexOf('Missing') > -1 || response.responseJSON.message.indexOf('invalid') > -1) {
						view.logout();
					}
					// console.log(response);
					if (errorHandler) {
						errorHandler(response, functionObject.errorVal);
					}
				},
				complete: function(response) {
					if (completeHandler) {
						completeHandler(response, functionObject.completeVal);
					}
				},
				beforeSend: function(xhr, settings) {
					// console.log(xhr);
					view.beforeSendEventHandler(xhr, settings);
				}
			}
		defaultAjaxParam = $.extend(true, defaultAjaxParam, ajaxParam);
		$.ajax(defaultAjaxParam);
	};
}