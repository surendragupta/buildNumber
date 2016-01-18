describe('Admin View', function() {
	beforeEach(function() {
		spyOn(window, 'alert');
		spyOn($('document'), 'ready');
	});
	it('AdminView is defined', function() {
		expect(AdminView).toBeDefined();
	});
	var adminModel = new AdminModel(),
		adminView = new AdminView(adminModel),
		htmlFixtures = new HTMLFixtures();

	describe('describe init function', function() {
		it('init function is defined', function() {
			expect(adminView.init).toBeDefined();
		});
		it('config object set from init function', function() {
			var ajaxCalConfigSpy;
			spyOn(adminView.model, 'ajaxCalConfig');
			// adminView.model.ajaxCalConfig=jasmine.createSpy("ajaxCalConfig spy");
			adminView.init();
			expect(adminView.model.ajaxCalConfig).toHaveBeenCalled();
		});
		it('view object added to model', function() {
			var ajaxCalConfigSpy;
			spyOn(adminView.model, 'ajaxCalConfig');
			adminView.init();
			expect(adminModel.get('view')).toEqual(adminView);
		});

	});
	describe('describe loadPage function', function() {
		var getItemSpy;
		beforeEach(function() {
			htmlFixtures.loadPageHTML();
			spyOn(adminView, 'getConfig');
			spyOn(adminView, 'loadingdistrict');
			spyOn(adminView, 'screenload');
			spyOn(adminView, 'htmlloading');
			spyOn(adminView, 'bindevent');
			spyOn(adminView, 'logout');
			spyOn($(".popupInputDate"), 'each');
			spyOn($(".popupInputDate"), 'datepicker');
			getItemSpy = spyOn(sessionStorage, 'getItem')
			getItemSpy.and.returnValue(true);
			spyOn(JSON, 'parse').and.returnValue({
				loginToken: 'token'
			});
		});
		it('loadPage function is defined', function() {
			expect(adminView.loadPage).toBeDefined();
		});
		it('getConfig functions called', function() {
			adminView.loadPage();
			expect(adminView.getConfig).toHaveBeenCalled();
		});
		it('loadingdistrict functions called', function() {
			adminView.loadPage();
			expect(adminView.loadingdistrict).toHaveBeenCalled();
		});
		it('screenload functions called', function() {
			adminView.loadPage();
			expect(adminView.screenload).toHaveBeenCalled();
		});
		it('htmlloading functions called', function() {
			adminView.loadPage();
			expect(adminView.htmlloading).toHaveBeenCalled();
		});
		it('bindevent functions called', function() {
			adminView.loadPage();
			expect(adminView.bindevent).toHaveBeenCalled();
		});
		it('logout functions called', function() {
			getItemSpy.and.returnValue(false);
			adminView.loadPage();
			expect(adminView.logout).toHaveBeenCalled();
		});
	});
	describe('describe htmlloading function', function() {
		beforeEach(function() {
			spyOn(JSON, 'parse').and.returnValue({
				domainname: 'domainName',
				userspace: 'userspace',
				username: 'username',
				firstname: 'firstname',
				lastname: 'lastname',
				loginToken: 'loginToken'

			});
			spyOn($('.domainHead h1'), 'html');
			spyOn($('.breadCrumb a:eq(0)'), 'html');
			spyOn($('.breadCrumb a:eq(1)'), 'html');
			spyOn($('.loggerName'), 'html');
			spyOn($('#snwadminSearch h1'), 'html');

		});
		it('htmlloading function is defined', function() {
			expect(adminView.htmlloading).toBeDefined();
		});
		it('global success token value added to medel', function() {
			adminView.htmlloading();
			expect(adminModel.get('_globalSccessToken')).toEqual('loginToken');
		});

	});

	describe('describe screenload function', function() {
		beforeEach(function() {
			// spyOn($('.addNew,.upArrow'), 'hide');
			spyOn($('.newDomTab li'), 'each');
			spyOn($('.listBox'), 'each');
			spyOn($('.newDomTab li'), 'attr');
			spyOn($('.listBox'), 'attr');

		});
		it('screenload function is defined', function() {
			expect(adminView.screenload).toBeDefined();
		});
		it('addNew and upArrow buttons visiblity changed', function() {
			adminView.screenload();
			// $('.listBox').each=jasmine.createSpy("each spy");
			expect($('.addNew,.upArrow').is(':visible')).toEqual(false);
		});
	});

	describe('describe bindevent function', function() {
		beforeEach(function() {
			htmlFixtures.bindeventHTML();
			spyOn(adminView, 'circlePlusHandler');
			spyOn(adminView, 'loginKeyUpPrefix');
			spyOn(adminView, 'numberValidation');
			spyOn(adminView, 'domainNameKeyUp');
			spyOn(adminView, 'popupBoxHandler');
			spyOn(adminView, 'submitDistrictHandler');
			spyOn(adminView, 'editSubmitHandler');
			spyOn(adminView, 'schlEditSubmitHandler');
			spyOn(adminView, 'domainWelcomeLetter');
			spyOn(adminView, 'editAddSchool');
			spyOn(adminView, 'schlsubmitHandler');
			spyOn(adminView, 'createPopupRadio');
			spyOn(adminView, 'disTrictListHandler');
			spyOn(adminView, 'showEntryHandler');
			spyOn(adminView, 'NextCirHandler');
			spyOn(adminView, 'PreviousCirHandler');
			spyOn(adminView, 'DomainNextHandler');
			spyOn(adminView, 'DomainPreviousHandler');
			spyOn(adminView, 'domainShowEntryHandler');
			spyOn(adminView, 'newDomTabHandler');
			spyOn(adminView, 'adminNav');
			spyOn(adminView, 'adminNavFirst');
			spyOn(adminView, 'courseCatalogHandler');
			spyOn(adminView, 'courseSelectHandler');
			spyOn(adminView, 'SearchClickHandler');
			spyOn(adminView, 'documentClickHandler');
			spyOn(adminView, 'distrctAddHandler');
			spyOn(adminView, 'catalogListInputHandler');
			spyOn(adminView, 'editHandler');
			spyOn(adminView, 'addSchoolHandler');
			spyOn(adminView, 'addSchlPopupBox');
			spyOn(adminView, 'editSchoolDistrictList');
			spyOn(adminView, 'courseCatalogListInput');
			spyOn(adminView, 'radioButton');
			spyOn(adminView, 'editSchoolPopHandler');
			spyOn(adminView, 'snwReportsLienceLiFirst');
			spyOn(adminView, 'snwreportsLienceLiSec');
			spyOn(adminView, 'reportfisrteditHandler');
			spyOn(adminView, 'reportPostSec');
			spyOn(adminView, 'adminNavZero');
			spyOn(adminView, 'stateListingHandler');
			spyOn(adminView, 'domainListingHandler');
			spyOn(adminView, 'loginoutevent');
			adminView.bindevent();

		});
		it('bindevent function is defined', function() {
			expect(adminView.bindevent).toBeDefined();
		});
		it('circlePlus click event triggered', function() {
			$('.circlePlus').trigger('click');
			expect(adminView.circlePlusHandler).toHaveBeenCalled();
		});
		it('loginPrefix keyup event triggered', function() {
			$('.loginPrefix').trigger('keyup');
			expect(adminView.loginKeyUpPrefix).toHaveBeenCalled();
		});
		it('snw_License keyup event triggered', function() {
			$('#snw_License').trigger('keyup');
			expect(adminView.numberValidation).toHaveBeenCalled();
		});
		it('snw_License1 keyup event triggered', function() {
			$('#snw_License1').trigger('keyup');
			expect(adminView.numberValidation).toHaveBeenCalled();
		});
		it('license-number keyup event triggered', function() {
			$('#licence-number').trigger('keyup');
			expect(adminView.numberValidation).toHaveBeenCalled();
		});
		it('snwlicense-number keyup event triggered', function() {
			$('#snwlicense-number').trigger('keyup');
			expect(adminView.numberValidation).toHaveBeenCalled();
		});
		it('domainName keyup event triggered', function() {
			$('.domainName').trigger('keyup');
			expect(adminView.domainNameKeyUp).toHaveBeenCalled();
		});
		it('subscription-end keyup event triggered', function() {
			$('.popupBox .subscription-end').trigger('keyup');
			expect(adminView.popupBoxHandler).toHaveBeenCalled();
		});
		it('submitdistrict click event triggered', function() {
			$('.submitdistrict').trigger('click');
			expect(adminView.submitDistrictHandler).toHaveBeenCalled();
		});
		it('editsubmit click event triggered', function() {
			$('.editsubmit').trigger('click');
			expect(adminView.editSubmitHandler).toHaveBeenCalled();
		});
		it('schleditsubmit click event triggered', function() {
			$('.schleditsubmit').trigger('click');
			expect(adminView.schlEditSubmitHandler).toHaveBeenCalled();
		});
		it('editwelcome click event triggered', function() {
			$('.editwelcome').trigger('click');
			expect(adminView.domainWelcomeLetter).toHaveBeenCalled();
		});
		it('editAddSchool click event triggered', function() {
			$('.editAddSchool').trigger('click');
			expect(adminView.editAddSchool).toHaveBeenCalled();
		});
		it('schlsubmit click event triggered', function() {
			$('.schlsubmit').trigger('click');
			expect(adminView.schlsubmitHandler).toHaveBeenCalled();
		});
		it('createpopupradio input change event triggered', function() {
			$('.createpopupradio input').trigger('change');
			expect(adminView.createPopupRadio).toHaveBeenCalled();
		});
		it('disTrictList click event triggered', function() {
			$('.disTrictList').trigger('click');
			expect(adminView.disTrictListHandler).toHaveBeenCalled();
		});
		it('showEntry click event triggered', function() {
			$('.showEntry').trigger('change');
			expect(adminView.showEntryHandler).toHaveBeenCalled();
		});
		it('NextCir click event triggered', function() {
			$('.NextCir').trigger('click');
			expect(adminView.NextCirHandler).toHaveBeenCalled();
		});
		it('PreviousCir click event triggered', function() {
			$('.PreviousCir').trigger('click');
			expect(adminView.PreviousCirHandler).toHaveBeenCalled();
		});
		it('DomainNext click event triggered', function() {
			$('.DomainNext').trigger('click');
			expect(adminView.DomainNextHandler).toHaveBeenCalled();
		});
		it('DomainPrevious click event triggered', function() {
			$('.DomainPrevious').trigger('click');
			expect(adminView.DomainPreviousHandler).toHaveBeenCalled();
		});
		it('domain-showEntry change event triggered', function() {
			$('.domain-showEntry').trigger('change');
			expect(adminView.domainShowEntryHandler).toHaveBeenCalled();
		});
		it('newDomTab li click event triggered', function() {
			$('.newDomTab li').trigger('click');
			expect(adminView.newDomTabHandler).toHaveBeenCalled();
		});
		it('adminNav li click event triggered', function() {
			$('.adminNav li').trigger('click');
			expect(adminView.adminNav).toHaveBeenCalled();
		});
		it('adminNav li:eq(1) click event triggered', function() {
			$('.adminNav li:eq(1)').trigger('click');
			expect(adminView.adminNavFirst).toHaveBeenCalled();
		});
		it('corsecatalog click event triggered', function() {
			$('.corsecatalog').trigger('click');
			expect(adminView.courseCatalogHandler).toHaveBeenCalled();
		});
		it('coursSelect click event triggered', function() {
			$('.coursSelect').trigger('click');
			expect(adminView.courseSelectHandler).toHaveBeenCalled();
		});
		it('SearchClick click event triggered', function() {
			$('.SearchClick').trigger('click');
			expect(adminView.SearchClickHandler).toHaveBeenCalled();
		});
		it('document click/tap event triggered', function() {
			$(document.body).trigger('click');
			expect(adminView.documentClickHandler).toHaveBeenCalled();
		});


		it('distrctadd click event triggered', function() {
			$('.distrctadd').trigger('click');
			expect(adminView.distrctAddHandler).toHaveBeenCalled();
		});
		it('catalog-list input change event triggered', function() {
			$('.catalog-list input').trigger('change');
			expect(adminView.catalogListInputHandler).toHaveBeenCalled();
		});
		it('edit click event triggered', function() {
			$('.edit').trigger('click');
			expect(adminView.editHandler).toHaveBeenCalled();
		});
		it('addschool click event triggered', function() {
			$('.addschool').trigger('click');
			expect(adminView.addSchoolHandler).toHaveBeenCalled();
		});
		it('addschoollist change event triggered', function() {
			$('.addschlpopupBox .addschoollist').trigger('change');
			expect(adminView.addSchlPopupBox).toHaveBeenCalled();
		});
		it('edit addschoollist change event triggered', function() {
			$('.editschlpopupBox .addschoollist').trigger('change');
			expect(adminView.addSchlPopupBox).toHaveBeenCalled();
		});
		it('schooldistrictlist change event triggered', function() {
			$('.addschlpopupBox .schooldistrictlist').trigger('change');
			expect(adminView.editSchoolDistrictList).toHaveBeenCalled();
		});
		it('edit schooldistrictlist change event triggered', function() {
			$('.editschlpopupBox .schooldistrictlist').trigger('change');
			expect(adminView.editSchoolDistrictList).toHaveBeenCalled();
		});
		it('corsecatalogList input click event triggered', function() {
			$('.corsecatalogList input[type="checkbox"]').trigger('click');
			expect(adminView.courseCatalogListInput).toHaveBeenCalled();
		});
		it('snwselectCourse input change event triggered', function() {
			$('#snwselectCourse input').trigger('change');
			expect(adminView.radioButton).toHaveBeenCalled();
		});
		it('edit snwselectCourse input change event triggered', function() {
			$('#snwselectCourse input').trigger('change');
			expect(adminView.radioButton).toHaveBeenCalled();
		});
		it('editSchoolpop click event triggered', function() {
			$('.editSchoolpop').trigger('click');
			expect(adminView.editSchoolPopHandler).toHaveBeenCalled();
		});
		it('snwreportsLienceLi1 click event triggered', function() {
			$('#snwreportsLienceLi1').trigger('click');
			expect(adminView.snwReportsLienceLiFirst).toHaveBeenCalled();
		});
		it('snwreportsLienceLi2 click event triggered', function() {
			$('#snwreportsLienceLi2').trigger('click');
			expect(adminView.snwreportsLienceLiSec).toHaveBeenCalled();
		});
		it('reportfisrtedit click event triggered', function() {
			$('.reportfisrtedit').trigger('click');
			expect(adminView.reportfisrteditHandler).toHaveBeenCalled();
		});
		it('reportpost2 click event triggered', function() {
			$('.reportpost2').trigger('click');
			expect(adminView.reportPostSec).toHaveBeenCalled();
		});
		it('adminNav li click event triggered', function() {
			$('.adminNav li:eq(0)').trigger('click');
			expect(adminView.adminNavZero).toHaveBeenCalled();
		});
		it('stateListing click event triggered', function() {
			$('.stateListing').trigger('click');
			expect(adminView.stateListingHandler).toHaveBeenCalled();
		});
		it('domainListing click event triggered', function() {
			$('.domainListing').trigger('click');
			expect(adminView.domainListingHandler).toHaveBeenCalled();
		});
	});
	describe('describe function editSchool', function() {
		beforeEach(function() {
			adminModel.set('_config', {
				getDomainData: 'getDomainData'
			});
			spyOn(adminModel, 'ajaxObjCall');
		});
		it('editSchool function is defined', function() {
			expect(adminView.editSchool).toBeDefined();
		});
		it('ajax function called for edit school data', function() {
			adminView.editSchool('fromselector', 'data_id', 'stateSelctedVal');
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});

	describe('describe function saveSchool', function() {
		beforeEach(function() {
			htmlFixtures.saveSchoolHTML();
			adminModel.set('subscriptionstartdate', '9-15-2015')
			spyOn(adminModel, 'ajaxObjCall');

		});
		it('saveSchool function is defined', function() {
			expect(adminView.saveSchool).toBeDefined();
		});
		it('saved school with create', function() {
			var formElementSelector = $('.formElementSelector'),
				data = 'create';
			adminModel.set('_config', {
				createSchool: 'createSchool'
			});
			adminView.saveSchool(formElementSelector, data);
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
		it('saved school with edit', function() {
			var formElementSelector = $('.formElementSelector'),
				data = 'edit';
			adminModel.set('_config', {
				eidtSchool: 'eidtSchool'
			});
			adminView.saveSchool(formElementSelector, data);
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
		it('save newly created school with blank pilot date ', function() {
			var formElementSelector = $('.formElementSelector'),
				data = 'create';
			adminModel.set('_config', {
				createSchool: 'createSchool'
			});
			formElementSelector.find(".pilot-domain").attr('checked', 'checked');
			formElementSelector.find(".date-field").val('');
			adminView.saveSchool(formElementSelector, data);
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});
	describe('describe function radioButton', function() {
		it('radioButton function is defined', function() {
			expect(adminView.radioButton).toBeDefined();
		});
	});
	describe('describe function loginoutevent', function() {
		it('loginoutevent function is defined', function() {
			expect(adminView.loginoutevent).toBeDefined();
		});
		it('LogOuticon click event triggered', function() {
			adminModel.set('configResult', 'configResult');
			htmlFixtures.loginouteventHTML();
			spyOn(adminView, 'loginoutIconClick');
			adminView.loginoutevent();
			$('.LogOuticon').trigger('click');
			expect(adminView.loginoutIconClick).toHaveBeenCalled();
		});
	});
	describe('describe function loginoutIconClick', function() {
		it('loginoutIconClick function is defined', function() {
			expect(adminView.loginoutIconClick).toBeDefined();
		});
		it('LogOuticon click event triggered', function() {
			spyOn(adminModel, 'ajaxObjCall');
			adminView.loginoutIconClick({
				Logouturl: 'Logouturl'
			});
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});
	describe('describe function sortingDistrict', function() {
		it('sortingDistrict function is defined', function() {
			expect(adminView.sortingDistrict).toBeDefined();
		});
	});

	describe('describe function loadingdistrict', function() {
		it('loadingdistrict function is defined', function() {
			expect(adminView.loadingdistrict).toBeDefined();
		});
		it('loading district service call made', function() {
			adminModel.set('configResult', {
				DistrictList: 'DistrictList'
			});
			spyOn(adminModel, 'ajaxObjCall');
			adminView.loadingdistrict();
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});

	describe('describe function statelistchange', function() {
		it('statelistchange function is defined', function() {
			expect(adminView.statelistchange).toBeDefined();
		});
		it('change event triggered on statename', function() {
			adminModel.set('configResult', {
				DistrictList: 'DistrictList'
			});
			htmlFixtures.statelistchangeHTML();
			spyOn(adminView, 'cursorwait');
			spyOn(adminModel, 'ajaxObjCall');
			adminView.statelistchange();
			$('.statename').trigger('change');
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});
	describe('describe function cursorwait', function() {
		it('cursorwait function is defined', function() {
			expect(adminView.cursorwait).toBeDefined();
		});
		it('gray background height changed', function() {
			var bodyHeight;
			htmlFixtures.cursorwaitHTML();
			bodyHeight = $('body').height();
			adminView.cursorwait();
			expect(bodyHeight).toEqual($('.grayBg').height());
		});
	});

	describe('describe function cursordefault', function() {
		it('cursordefault function is defined', function() {
			expect(adminView.cursordefault).toBeDefined();
		});
		it('gray background visiblity changed', function() {
			htmlFixtures.cursordefaultHTML();
			adminView.cursordefault();
			expect($('.grayBg').is(':visible')).toEqual(false);
		});
	});
	describe('describe function getProviderList', function() {
		it('getProviderList function is defined', function() {
			expect(adminView.getProviderList).toBeDefined();
		});
		it('provider list service called', function() {
			adminModel.set('configResult', {
				New_District_Popup_Provider: 'New_District_Popup_Provider'
			});
			spyOn(adminModel, 'ajaxObjCall');
			adminView.getProviderList();
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});
	describe('describe function getCourseList', function() {
		it('getCourseList function is defined', function() {
			expect(adminView.getCourseList).toBeDefined();
		});
		it('provider list service called', function() {
			adminModel.set('_config', {
				courseList: 'New_District_Popup_Provider'
			});
			spyOn(jQuery, 'when').and.returnValue({
				then: function() {}
			});
			spyOn(jQuery, 'ajax');
			spyOn(adminView, 'beforeSendEventHandler');

			adminView.getCourseList();
			expect(jQuery.ajax).toHaveBeenCalled();
		});
	});
	describe('describe function getConfig', function() {
		it('getConfig function is defined', function() {
			expect(adminView.getConfig).toBeDefined();
		});
		it('provider list service called', function() {
			spyOn(jQuery, 'when').and.returnValue({
				then: function() {}
			});
			spyOn(jQuery, 'ajax');
			adminView.getConfig();
			expect(jQuery.ajax).toHaveBeenCalled();
		});
	});
	describe('describe function updateCourseList', function() {
		it('updateCourseList function is defined', function() {
			expect(adminView.updateCourseList).toBeDefined();
		});
		it('provider list service called', function() {
			adminModel.set('_config', {
				courseList: 'New_District_Popup_Provider'
			});
			htmlFixtures.updateCourseListHTML();
			spyOn(adminModel, 'ajaxObjCall');
			adminView.updateCourseList();
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
		it('provider list service could not called', function() {
			var htmlString = '<li>Please select a Course Catalog first.</li>';
			adminModel.set('_config', {
				courseList: 'New_District_Popup_Provider'
			});
			htmlFixtures.updateCourseListHTML();
			spyOn(adminModel, 'ajaxObjCall');
			$('.catalog-list input[type="checkbox"]').removeAttr('checked');
			adminView.updateCourseList();
			expect($(".corseselectList ul").html()).toEqual(htmlString);
		});
	});
	describe('describe function domainWelcomeLetter', function() {
		it('domainWelcomeLetter function is defined', function() {
			expect(adminView.domainWelcomeLetter).toBeDefined();
		});
	});
	describe('describe function domainFormValidation', function() {
		it('domainFormValidation function is defined', function() {
			expect(adminView.domainFormValidation).toBeDefined();
		});
		it('domainFormSubmit function called', function() {
			var htmlString = '<div></div>';
			spyOn(adminView, 'domainFormSubmit');
			adminView.domainFormValidation(htmlString, 'get');
			expect(adminView.domainFormSubmit).toHaveBeenCalled()
		});
	});
	describe('describe function domainFormSubmit', function() {
		var formElementSelector;
		beforeEach(function() {
			adminModel.set({
				_config: {
					createDomain: 'createDomain',
					editDomain: 'editDomain'
				},
				_sessionData: {
					token: 'token'
				}
			});
			htmlFixtures.domainFormSubmitHTML();
			spyOn(adminModel, 'ajaxObjCall');
			formElementSelector = $('.formElementSelector');
		})
		it('domainFormSubmit function is defined', function() {
			expect(adminView.domainFormSubmit).toBeDefined();
		});
		it('create domain object submited to server', function() {
			adminView.domainFormSubmit(formElementSelector, 'create');
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
		it('edit domain object submited to server', function() {
			adminView.domainFormSubmit(formElementSelector, 'edit');
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
		it('create domain object submited to server with calculated date', function() {
			formElementSelector.find(".pilot-domain").attr('checked', 'checked');
			formElementSelector.find(".pilot-end").eq(0).val('');
			adminView.domainFormSubmit(formElementSelector, 'edit');
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});
	describe('describe function domainEditForm', function() {
		it('domainEditForm function is defined', function() {
			expect(adminView.domainEditForm).toBeDefined();
		});
		it('domain edit data called', function() {
			var formElementSelector;
			spyOn(adminModel, 'ajaxObjCall');
			htmlFixtures.domainFormSubmitHTML();
			formElementSelector = $('.formElementSelector');
			adminModel.set('_config', {
				getDomainData: 'getDomainData'
			});
			adminView.domainEditForm();
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});
	describe('describe function editnewfunction', function() {
		it('editnewfunction function is defined', function() {
			expect(adminView.editnewfunction).toBeDefined();
		});
		it('new course list added', function() {
			adminModel.set('_totalCourseArry', {
				1: {
					id: 1254,
					title: 'data-course-name1',
					domainid: '632589'
				},
				2: {
					id: 1864,
					title: 'data-course-name2',
					domainid: '632589'
				},
				3: {
					id: 1594,
					title: 'data-course-name3',
					domainid: '632589'
				},
				4: {
					id: 1954,
					title: 'data-course-name4',
					domainid: '632589'
				},
				5: {
					id: 1954,
					title: 'data-course-name5',
					domainid: '632589'
				},
			});
			htmlFixtures.editnewfunctionHTML();
			spyOn(adminView, 'courseListEach');
			adminView.editnewfunction();
			expect(adminView.courseListEach).toHaveBeenCalled();
		});
	});
	describe('describe function courseListEach', function() {
		beforeEach(function() {
			adminModel.set('_result', {
				data: {
					subscribedcourselist: {
						1: {
							id: 1254,
							title: 'data-course-name',
							domainid: '632589'
						},
						2: {
							id: 1254,
							title: 'data-course-name',
							domainid: '632589'
						}
					}
				}
			});
		});
		it('courseListEach function is defined', function() {
			expect(adminView.courseListEach).toBeDefined();
		});
		it('course found and element property checked', function() {
			var $element = $('<div class="dummy">' + '<input type="checkbox" data-course-id="236589">' + '</div>');
			spyOn(adminView, 'eachsubscribedcourse').and.returnValue(true);
			adminView.courseListEach($element);
			expect($element.find('input').is(':checked')).toEqual(true);
		});
		it('course not found and element property unchecked', function() {
			var $element = $('<div class="dummy">' + '<input type="checkbox" data-course-id="236589">' + '</div>');
			spyOn(adminView, 'eachsubscribedcourse').and.returnValue(false);
			adminView.courseListEach($element);
			expect($element.find('input').is(':checked')).toEqual(false);
		});
	});

	describe('describe function eachsubscribedcourse', function() {
		it('eachsubscribedcourse function is defined', function() {
			expect(adminView.eachsubscribedcourse).toBeDefined();
		});
		it('returns true value', function() {
			var valueResult;
			valueResult = adminView.eachsubscribedcourse(void 0, 121, 121);
			expect(valueResult).toEqual(true);
		});
		it('returns false value', function() {
			var valueResult;
			valueResult = adminView.eachsubscribedcourse(void 0, 121, 125);
			expect(valueResult).toEqual(false);
		});
	});
	describe('describe function addSchool', function() {
		it('addSchool function is defined', function() {
			expect(adminView.addSchool).toBeDefined();
		});
		it('ajax object called for add school', function() {
			spyOn(adminModel, 'ajaxObjCall');
			adminModel.set('_config', {
				DistrictList: 'DistrictList'
			});
			adminView.addSchool('data_param', 539);
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});
	describe('describe function schoolPopupData', function() {
		it('schoolPopupData function is defined', function() {
			expect(adminView.schoolPopupData).toBeDefined();
		});
		it('ajax object called for school popup data', function() {
			spyOn(adminModel, 'ajaxObjCall');
			adminModel.set('_config', {
				getDomainData: 'getDomainData'
			});
			adminView.schoolPopupData('data_param', 539);
			expect(adminModel.ajaxObjCall).toHaveBeenCalled();
		});
	});
	
});